import os
import shutil
import uuid

from fastapi import (
    APIRouter,
    Depends,
    HTTPException,
    UploadFile,
    File,
)

from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User

from app.schemas.guardian_team import (
    GuardianTeamCreate,
    GuardianTeamUpdate,
    GuardianTeamResponse,
)

from app.services.guardian_team_service import (
    get_guardian_team,
    get_team_person,
    create_team_person,
    update_team_person,
    delete_team_person,
)


router = APIRouter(
    prefix="/guardian/team",
    tags=["Guardian Team"],
)


# ============================================================
# HELPER — CHECK EDIT PERMISSION
# ============================================================

def can_edit_team_person(
    current_user: User,
    team_person,
) -> bool:

    # Only Guardian accounts can edit
    if current_user.role != "GUARDIAN":
        return False

    # Team person must have an email
    if not team_person.email:
        return False

    # Logged-in Guardian email must match
    # the team person's email
    if current_user.email.lower().strip() != team_person.email.lower().strip():
        return False

    return True


# ============================================================
# GET GUARDIAN TEAM
# ============================================================

@router.get(
    "",
    response_model=list[GuardianTeamResponse],
)
def get_team(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can access team information.",
        )

    return get_guardian_team(
        db=db,
        guardian_id=current_user.id,
    )

# ============================================================
# CREATE TEAM PERSON
# ============================================================

@router.post(
    "",
    response_model=GuardianTeamResponse,
)
def create_team(
    data: GuardianTeamCreate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can create team information.",
        )

    person_type = data.person_type.upper()

    allowed_types = {
        "HOD",
        "MENTOR",
        "MEMBER",
    }

    if person_type not in allowed_types:
        raise HTTPException(
            status_code=400,
            detail="person_type must be HOD, MENTOR, or MEMBER.",
        )

    # Roll number only for members
    if person_type == "MEMBER":

        if not data.roll_no:
            raise HTTPException(
                status_code=400,
                detail="Roll number is required for team members.",
            )

    else:

        if data.roll_no:
            raise HTTPException(
                status_code=400,
                detail="Roll number is only allowed for team members.",
            )

    return create_team_person(
        db=db,
        guardian_id=current_user.id,
        person_type=person_type,
        roll_no=data.roll_no,
        name=data.name,
        email=data.email,
        role=data.role,
        details=data.details,
    )


# ============================================================
# UPDATE TEAM PERSON
# ============================================================

@router.put(
    "/{team_id}",
    response_model=GuardianTeamResponse,
)
def update_team(
    team_id: int,
    data: GuardianTeamUpdate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can update team information.",
        )

    # --------------------------------------------------------
    # Find record by ID only
    # --------------------------------------------------------

    team_person = (
        db.query(
            __import__(
                "app.models.guardian_team",
                fromlist=["GuardianTeam"]
            ).GuardianTeam
        )
        .filter(
            __import__(
                "app.models.guardian_team",
                fromlist=["GuardianTeam"]
            ).GuardianTeam.id == team_id
        )
        .first()
    )

    if not team_person:
        raise HTTPException(
            status_code=404,
            detail="Team member not found.",
        )

    # --------------------------------------------------------
    # Permission check
    # --------------------------------------------------------

    if not can_edit_team_person(
        current_user,
        team_person,
    ):
        raise HTTPException(
            status_code=403,
            detail=(
                "You are not authorized to edit this "
                "team information."
            ),
        )

    # --------------------------------------------------------
    # Roll number validation
    # --------------------------------------------------------

    if team_person.person_type != "MEMBER":

        if data.roll_no is not None:
            raise HTTPException(
                status_code=400,
                detail="Roll number is only allowed for team members.",
            )

    return update_team_person(
        db=db,
        team_person=team_person,
        roll_no=data.roll_no,
        name=data.name,
        email=data.email,
        role=data.role,
        details=data.details,
    )


# ============================================================
# DELETE TEAM PERSON
# ============================================================

@router.delete(
    "/{team_id}",
)
def delete_team(
    team_id: int,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can delete team information.",
        )

    # Find record by ID
    from app.models.guardian_team import GuardianTeam

    team_person = (
        db.query(GuardianTeam)
        .filter(
            GuardianTeam.id == team_id
        )
        .first()
    )

    if not team_person:
        raise HTTPException(
            status_code=404,
            detail="Team member not found.",
        )

    # Only authorized person can delete
    if not can_edit_team_person(
        current_user,
        team_person,
    ):
        raise HTTPException(
            status_code=403,
            detail=(
                "You are not authorized to delete "
                "this team information."
            ),
        )

    delete_team_person(
        db=db,
        team_person=team_person,
    )

    return {
        "message": "Team information deleted successfully."
    }


# ============================================================
# UPLOAD TEAM PERSON PHOTO
# ============================================================

@router.post(
    "/{team_id}/photo",
    response_model=GuardianTeamResponse,
)
def upload_team_photo(
    team_id: int,
    file: UploadFile = File(...),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can upload team photos.",
        )

    # --------------------------------------------------------
    # Find team person by ID
    # --------------------------------------------------------

    from app.models.guardian_team import GuardianTeam

    team_person = (
        db.query(GuardianTeam)
        .filter(
            GuardianTeam.id == team_id
        )
        .first()
    )

    if not team_person:
        raise HTTPException(
            status_code=404,
            detail="Team person not found.",
        )

    # --------------------------------------------------------
    # Permission check
    # --------------------------------------------------------

    if not can_edit_team_person(
        current_user,
        team_person,
    ):
        raise HTTPException(
            status_code=403,
            detail=(
                "You are not authorized to upload "
                "a photo for this team person."
            ),
        )

    # --------------------------------------------------------
    # Validate image
    # --------------------------------------------------------

    allowed_types = {
        "image/jpeg",
        "image/png",
        "image/webp",
    }

    if file.content_type not in allowed_types:
        raise HTTPException(
            status_code=400,
            detail="Only JPG, PNG, and WEBP images are allowed.",
        )

    # --------------------------------------------------------
    # Create upload directory
    # --------------------------------------------------------

    upload_directory = os.path.join(
        "uploads",
        "team",
    )

    os.makedirs(
        upload_directory,
        exist_ok=True,
    )

    # --------------------------------------------------------
    # Delete old image
    # --------------------------------------------------------

    if team_person.image:

        old_image = team_person.image

        if old_image.startswith("/uploads/"):

            old_file = old_image.lstrip("/")

            if os.path.exists(old_file):

                try:
                    os.remove(old_file)

                except OSError:
                    pass

    # --------------------------------------------------------
    # Generate filename
    # --------------------------------------------------------

    extension = os.path.splitext(
        file.filename or ""
    )[1].lower()

    if not extension:
        extension = ".jpg"

    filename = (
        f"{current_user.id}_"
        f"{team_person.id}_"
        f"{uuid.uuid4().hex}"
        f"{extension}"
    )

    file_path = os.path.join(
        upload_directory,
        filename,
    )

    # --------------------------------------------------------
    # Save image
    # --------------------------------------------------------

    try:

        with open(
            file_path,
            "wb",
        ) as buffer:

            shutil.copyfileobj(
                file.file,
                buffer,
            )

    except Exception as error:

        print(
            "Team photo upload error:",
            error,
        )

        raise HTTPException(
            status_code=500,
            detail="Failed to save team photo.",
        )

    # --------------------------------------------------------
    # Save URL
    # --------------------------------------------------------

    team_person.image = (
        f"/uploads/team/{filename}"
    )

    db.commit()
    db.refresh(team_person)

    return team_person