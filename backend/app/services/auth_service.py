from sqlalchemy.orm import Session

from app.models.user import User
from app.schemas.user import UserCreate

from app.core.security import hash_password, verify_password
from app.utils.id_generator import generate_safe_path_id



# def create_user(db: Session, user_data: UserCreate):

#     print("=== CREATE USER CALLED ===")
#     print(user_data)

#     existing_user = db.query(User).filter(
#         User.email == user_data.email
#     ).first()

#     if existing_user:
#         print("User already exists")
#         return None

#     hashed = hash_password(user_data.password)
#     print("Hash created successfully")

#     new_user = User(
#         full_name=user_data.full_name,
#         email=user_data.email,
#         phone_number=user_data.phone_number,
#         password=hashed,
#         role=user_data.role,
#         safe_path_id=generate_safe_path_id()
#     )

#     db.add(new_user)
#     db.commit()
#     db.refresh(new_user)

#     print("User saved successfully")

#     return new_user
def create_user(db: Session, user_data: UserCreate):

    print("========== CREATE USER ==========")
    print("Email:", user_data.email)
    print("Password:", user_data.password)
    print("Password Length:", len(user_data.password))

    existing_user = db.query(User).filter(
        User.email == user_data.email
    ).first()

    if existing_user:
        print("User already exists")
        return None

    hashed_password = hash_password(user_data.password)

    print("Hash created successfully")
    print(hashed_password)

    new_user = User(
        full_name=user_data.full_name,
        email=user_data.email,
        phone_number=user_data.phone_number,
        password=hashed_password,
        role=user_data.role,
        safe_path_id=generate_safe_path_id()
    )

    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    print("User saved successfully")

    return new_user

# def authenticate_user(
#     db: Session,
#     email: str,
#     password: str
# ):
def authenticate_user(
    db: Session,
    email: str,
    password: str
):

    print("INPUT PASSWORD:", password)
    print("PASSWORD LENGTH:", len(password))

    user = db.query(User).filter(
        User.email == email
    ).first()

    if not user:
        print("User not found")
        return None

    print("HASH FROM DB:", user.password)
    print("HASH LENGTH:", len(user.password))

    if not verify_password(password, user.password):
        return None

    return user

    # user = db.query(User).filter(
    #     User.email == email
    # ).first()


    # if not user:
    #     return None


    # if not verify_password(
    #     password,
    #     user.password
    # ):
    #     return None


    # return user