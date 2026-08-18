from twilio.rest import Client

from app.core.config import settings


def send_sos_sms(
    recipient_phone: str,
    user_name: str,
    latitude: float,
    longitude: float,
    sos_message: str
):

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