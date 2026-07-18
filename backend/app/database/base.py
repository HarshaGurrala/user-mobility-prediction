from app.database.database import Base

# Import all models here
from app.models.user import User
from app.models.user_guardian import UserGuardian
from app.models.emergency_contact import EmergencyContact
from app.models.location import Location
from app.models.safe_location import SafeLocation
from app.models.prediction import Prediction
from app.models.alert import Alert