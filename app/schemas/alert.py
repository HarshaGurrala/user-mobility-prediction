from pydantic import BaseModel


class AlertCreate(BaseModel):

    user_id: int
    guardian_id: int
    alert_type: str
    message: str


class AlertResponse(BaseModel):

    id: int
    user_id: int
    guardian_id: int
    alert_type: str
    message: str
    status: str

    class Config:
        from_attributes = True