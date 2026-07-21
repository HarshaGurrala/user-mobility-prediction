from pydantic import BaseModel


class GuardianRequest(BaseModel):

    safe_path_id: str



class PendingRequestResponse(BaseModel):

    request_id: int

    guardian_id: int

    guardian_name: str

    guardian_email: str

    status: str