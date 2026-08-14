from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

import json
import urllib.parse
import urllib.request
import urllib.error

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.models.user import User
from app.models.safe_location import SafeLocation
from app.models.user_guardian_relationship import UserGuardianRelationship

from app.schemas.safe_location import (
    SafeLocationCreate,
    SafeLocationUpdate,
)

from app.services.safe_location_service import (
    create_safe_location,
    get_safe_locations,
    get_safe_location_by_id,
    update_safe_location,
    delete_safe_location,
)


router = APIRouter(
    prefix="/safe-location",
    tags=["Safe Locations"]
)


# ==========================================================
# NOMINATIM LOCATION SEARCH HELPER
# ==========================================================

def search_nominatim(query: str, limit: int = 10):

    params = {
        "format": "json",
        "addressdetails": 1,
        "limit": limit,
        "countrycodes": "in",
        "q": query
    }

    url = (
        "https://nominatim.openstreetmap.org/search?"
        + urllib.parse.urlencode(params)
    )

    request = urllib.request.Request(
        url,
        headers={
            "User-Agent": (
                "SafePathAI/1.0 "
                "(Safe Zone location search)"
            ),
            "Accept": "application/json"
        }
    )

    try:

        with urllib.request.urlopen(
            request,
            timeout=10
        ) as response:

            data = response.read().decode("utf-8")

            return json.loads(data)

    except urllib.error.HTTPError as error:

        if error.code == 429:
            raise HTTPException(
                status_code=503,
                detail=(
                    "Location search service is temporarily "
                    "busy. Please try again in a moment."
                )
            )

        raise HTTPException(
            status_code=502,
            detail="Location search service unavailable."
        )

    except Exception as error:

        print(
            "NOMINATIM SEARCH ERROR:",
            error
        )

        raise HTTPException(
            status_code=502,
            detail="Unable to search locations."
        )


# ==========================================================
# EXTRACT GEOGRAPHIC TERMS FROM FULL ADDRESS
# ==========================================================

# ==========================================================
# BUILD MULTIPLE GEOGRAPHIC FALLBACK QUERIES
# ==========================================================

def build_geographic_queries(query: str):

    text = query.strip()

    if not text:
        return []

    # Normalize common separators
    text = text.replace(" - ", ", ")

    parts = [
        part.strip()
        for part in text.split(",")
        if part.strip()
    ]

    geographic_parts = []

    for part in parts:

        lower_part = part.lower().strip()

        # Remove house / door / flat information
        if (
            lower_part.startswith("d.no")
            or lower_part.startswith("d no")
            or lower_part.startswith("door no")
            or lower_part.startswith("door no.")
            or lower_part.startswith("house no")
            or lower_part.startswith("house no.")
            or lower_part.startswith("h.no")
            or lower_part.startswith("h.no.")
            or lower_part.startswith("h no")
            or lower_part.startswith("flat no")
            or lower_part.startswith("flat no.")
        ):
            continue

        # Remove non-geographic landmark descriptions
        if (
            lower_part.startswith("near ")
            or lower_part.startswith("opp ")
            or lower_part.startswith("opposite ")
            or lower_part.startswith("beside ")
        ):
            continue

        # Remove administrative words from the actual
        # search term while keeping the place name.
        cleaned = part

        cleaned = cleaned.replace(
            " Mandal", ""
        )

        cleaned = cleaned.replace(
            " mandal", ""
        )

        cleaned = cleaned.replace(
            " District", ""
        )

        cleaned = cleaned.replace(
            " district", ""
        )

        cleaned = cleaned.strip()

        if cleaned:
            geographic_parts.append(
                cleaned
            )

    queries = []

    # ------------------------------------------------------
    # Find PIN code
    # ------------------------------------------------------

    pin_code = None

    for part in parts:

        digits = "".join(
            character
            for character in part
            if character.isdigit()
        )

        if len(digits) == 6:
            pin_code = digits
            break

    # ------------------------------------------------------
    # Build useful geographic combinations
    # ------------------------------------------------------

    if geographic_parts:

        # Full geographic combination
        queries.append(
            ", ".join(
                geographic_parts
            )
        )

        # Remove PIN if it is already represented separately
        if pin_code:
            non_pin_parts = [
                part
                for part in geographic_parts
                if pin_code not in part
            ]

            if non_pin_parts:

                queries.append(
                    ", ".join(
                        non_pin_parts
                    )
                )

        # Locality + district/state
        if len(geographic_parts) >= 3:

            queries.append(
                ", ".join(
                    geographic_parts[-3:]
                )
            )

        # Locality + state
        if len(geographic_parts) >= 2:

            queries.append(
                ", ".join(
                    geographic_parts[-2:]
                )
            )

    # ------------------------------------------------------
    # PIN-based geographic search
    # ------------------------------------------------------

    if pin_code:

        queries.append(
            f"{pin_code}, Andhra Pradesh, India"
        )

    # ------------------------------------------------------
    # Remove duplicates
    # ------------------------------------------------------

    final_queries = []

    seen = set()

    for item in queries:

        cleaned = item.strip()

        key = cleaned.lower()

        if (
            cleaned
            and key not in seen
        ):

            seen.add(key)

            final_queries.append(
                cleaned
            )

    return final_queries


# ==========================================================
# LOCATION SEARCH FOR SAFE ZONE
# ==========================================================

# ==========================================================
# LOCATION SEARCH FOR SAFE ZONE
# ==========================================================

@router.get("/search")
def search_safe_location(
    q: str
):

    search_text = q.strip()

    if len(search_text) < 3:
        return []

    print(
        "========================================"
    )

    print(
        "SAFE LOCATION SEARCH:",
        search_text
    )

    print(
        "========================================"
    )

    # ------------------------------------------------------
    # FIRST SEARCH
    # Exact user-entered search
    # ------------------------------------------------------

    results = search_nominatim(
        search_text,
        limit=10
    )

    print(
        "FULL ADDRESS RESULTS:",
        len(results)
    )

    # ------------------------------------------------------
    # FALLBACK SEARCHES
    # ------------------------------------------------------

    fallback_queries = (
        build_geographic_queries(
            search_text
        )
    )

    print(
        "FALLBACK QUERIES:",
        fallback_queries
    )

    fallback_results = []

    # ------------------------------------------------------
    # Try geographic queries until useful results appear
    # ------------------------------------------------------

    for fallback_query in fallback_queries:

        # Don't repeat the original query
        if (
            fallback_query.lower()
            == search_text.lower()
        ):
            continue

        print(
            "TRYING FALLBACK:",
            fallback_query
        )

        try:

            current_results = search_nominatim(
                fallback_query,
                limit=10
            )

            print(
                "RESULTS:",
                len(current_results)
            )

            fallback_results.extend(
                current_results
            )

            # Stop once we have enough results
            if len(fallback_results) >= 10:
                break

        except HTTPException:

            # Don't allow one failed fallback
            # to destroy the entire search.
            continue

    # ------------------------------------------------------
    # COMBINE RESULTS
    # ------------------------------------------------------

    combined_results = []

    seen = set()

    for result in (
        results + fallback_results
    ):

        place_id = result.get(
            "place_id"
        )

        if place_id is not None:

            key = str(place_id)

        else:

            key = (
                str(result.get("lat", "")),
                str(result.get("lon", ""))
            )

        if key in seen:
            continue

        seen.add(key)

        combined_results.append(
            result
        )

    print(
        "FINAL RESULT COUNT:",
        len(combined_results)
    )

    return combined_results[:15]


# ==========================================================
# GUARDIAN ADD SAFE LOCATION FOR SELECTED USER
# ==========================================================

@router.post("/guardian/{user_id}")
def guardian_add_safe_location(
    user_id: int,
    location: SafeLocationCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can add safe location"
        )

    user = (
        db.query(User)
        .filter(User.id == user_id)
        .first()
    )

    if not user:
        raise HTTPException(
            status_code=404,
            detail="User not found"
        )

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        raise HTTPException(
            status_code=403,
            detail="User is not connected with this guardian"
        )

    return create_safe_location(
        db,
        user_id,
        location
    )


# ==========================================================
# USER / GUARDIAN VIEW SAFE LOCATIONS
# ==========================================================

@router.get("/{user_id}")
def list_safe_locations(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    # User can view their own safe locations
    if current_user.id == user_id:

        return get_safe_locations(
            db,
            user_id
        )

    # Guardian can view connected user's safe locations
    if current_user.role == "GUARDIAN":

        relationship = (
            db.query(UserGuardianRelationship)
            .filter(
                UserGuardianRelationship.guardian_id == current_user.id,
                UserGuardianRelationship.user_id == user_id,
                UserGuardianRelationship.status == "ACCEPTED"
            )
            .first()
        )

        if not relationship:
            raise HTTPException(
                status_code=403,
                detail="User is not connected with this guardian"
            )

        return get_safe_locations(
            db,
            user_id
        )

    raise HTTPException(
        status_code=403,
        detail="Unauthorized"
    )


# ==========================================================
# GET SINGLE SAFE LOCATION
# ==========================================================

@router.get("/detail/{location_id}")
def get_safe_location_detail(
    location_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    safe_location = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.id == location_id
        )
        .first()
    )

    if not safe_location:
        raise HTTPException(
            status_code=404,
            detail="Safe location not found"
        )

    # Owner can view
    if current_user.id == safe_location.user_id:

        return safe_location

    # Connected guardian can view
    if current_user.role == "GUARDIAN":

        relationship = (
            db.query(UserGuardianRelationship)
            .filter(
                UserGuardianRelationship.guardian_id == current_user.id,
                UserGuardianRelationship.user_id == safe_location.user_id,
                UserGuardianRelationship.status == "ACCEPTED"
            )
            .first()
        )

        if relationship:
            return safe_location

    raise HTTPException(
        status_code=403,
        detail="Unauthorized"
    )


# ==========================================================
# GUARDIAN UPDATE SAFE LOCATION
# ==========================================================

@router.put("/{location_id}")
def edit_safe_location(
    location_id: int,
    location: SafeLocationUpdate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can update safe location"
        )

    safe_location = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.id == location_id
        )
        .first()
    )

    if not safe_location:
        raise HTTPException(
            status_code=404,
            detail="Safe location not found"
        )

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.user_id == safe_location.user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        raise HTTPException(
            status_code=403,
            detail="User is not connected with this guardian"
        )

    return update_safe_location(
        db,
        location_id,
        location
    )


# ==========================================================
# GUARDIAN DELETE SAFE LOCATION
# ==========================================================

@router.delete("/{location_id}")
def remove_safe_location(
    location_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can delete safe location"
        )

    safe_location = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.id == location_id
        )
        .first()
    )

    if not safe_location:
        raise HTTPException(
            status_code=404,
            detail="Safe location not found"
        )

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.user_id == safe_location.user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        raise HTTPException(
            status_code=403,
            detail="User is not connected with this guardian"
        )

    result = delete_safe_location(
        db,
        location_id
    )

    return {
        "success": result
    }