from pydantic import BaseModel, EmailStr
from typing import Optional


class UserCreate(BaseModel):

    full_name: str

    email: EmailStr

    phone_number: Optional[str] = None

    password: str

    role: str = "USER"



class UserLogin(BaseModel):

    email: EmailStr

    password: str



class UserResponse(BaseModel):

    id: int

    full_name: str

    email: EmailStr

    phone_number: Optional[str]

    role: str

    safe_path_id: str


    class Config:
        from_attributes = True