from typing import Optional

from pydantic import BaseModel, EmailStr


class UserCreate(BaseModel):

    full_name: str

    email: EmailStr

    phone_number: Optional[str] = None

    password: str

    role: str = "USER"


class UserLogin(BaseModel):

    email: EmailStr

    password: str


class ChangePasswordRequest(BaseModel):

    current_password: str

    new_password: str

    

class UserUpdate(BaseModel):

    full_name: str

    email: EmailStr

    phone_number: Optional[str] = None


class UserResponse(BaseModel):

    id: int

    full_name: str

    email: EmailStr

    phone_number: Optional[str]

    role: str

    safe_path_id: str

    class Config:
        from_attributes = True