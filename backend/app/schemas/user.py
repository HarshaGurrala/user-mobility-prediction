# from typing import Optional

# from pydantic import BaseModel, EmailStr

# from typing import Optional

# from typing import Optional
from typing import Optional
from pydantic import BaseModel, EmailStr

class UserCreate(BaseModel):

    full_name: str
    email: EmailStr
    phone_number: str
    password: str
    role: str = "USER"
    guardian_code: Optional[str] = None


class UserLogin(BaseModel):
    email: EmailStr
    password: str

class Token(BaseModel):
    access_token: str
    token_type: str


class TokenData(BaseModel):
    email: Optional[str] = None