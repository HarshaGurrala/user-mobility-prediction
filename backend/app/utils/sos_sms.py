from twilio.rest import Client
from typing import Optional

from app.core.config import settings


def send_sos_sms(
    recipient_phone: str,
    user_name: str,
    latitude: Optional[float],
    longitude: Optional[float],
    sos_message: str
):

    if not all((
        settings.TWILIO_ACCOUNT_SID,
        settings.TWILIO_AUTH_TOKEN,
        settings.TWILIO_PHONE_NUMBER
    )):
        print("SOS SMS skipped: Twilio is not configured")
        return None

    maps_link = (
        f"https://www.google.com/maps?q={latitude},{longitude}"
    )

    message_body = f"""
🚨 SAFEPATH AI SOS ALERT

Emergency detected!

User: {user_name}

Message:
{sos_message}

Current Location:
{maps_link}

Latitude: {latitude}
Longitude: {longitude}

Please contact the user immediately.

SafePath AI Team
"""

    client = Client(
        settings.TWILIO_ACCOUNT_SID,
        settings.TWILIO_AUTH_TOKEN
    )

    message = client.messages.create(
        body=message_body,
        from_=settings.TWILIO_PHONE_NUMBER,
        to=recipient_phone
    )

    return message.sid