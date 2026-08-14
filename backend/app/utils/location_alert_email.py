import smtplib
from email.message import EmailMessage

from app.core.config import settings


def send_unknown_location_email(
    recipient_email: str,
    guardian_name: str,
    user_name: str,
    latitude: float,
    longitude: float,
    alert_id: int
):

    maps_link = (
        f"https://www.google.com/maps"
        f"?q={latitude},{longitude}"
    )

    message = EmailMessage()

    message["Subject"] = (
        "⚠️ SafePath AI - Unknown Location Alert"
    )

    message["From"] = settings.SMTP_FROM_EMAIL
    message["To"] = recipient_email

    message.set_content(
        f"""
SafePath AI - Location Alert

Hello {guardian_name},

The user {user_name} has entered a location that is not currently registered as a Safe Zone.

Location Details:

User:
{user_name}

Latitude:
{latitude}

Longitude:
{longitude}

Google Maps:
{maps_link}

Please review this location from the Guardian Dashboard.

You can classify this location as:

1. Safe Zone
2. Unsafe / Not Safe
3. Safe / Not Safe - Informational Alert

Alert ID:
{alert_id}

Regards,
SafePath AI Team
"""
    )

    html_content = f"""
<html>
<body style="font-family:Arial,sans-serif;">

<h2 style="color:#F59E0B;">
⚠️ Unknown Location Alert
</h2>

<p>
Hello <strong>{guardian_name}</strong>,
</p>

<p>
The user
<strong>{user_name}</strong>
has entered a location that is not currently registered as a Safe Zone.
</p>

<h3>Location Details</h3>

<p>
<strong>User:</strong> {user_name}<br>
<strong>Latitude:</strong> {latitude}<br>
<strong>Longitude:</strong> {longitude}
</p>

<p>
<a href="{maps_link}"
style="
display:inline-block;
padding:12px 24px;
background-color:#2563EB;
color:white;
text-decoration:none;
border-radius:6px;
font-weight:bold;
">
📍 View Location
</a>
</p>

<h3>Guardian Action</h3>

<p>
Please review this location and classify it as:
</p>

<ul>
<li><strong>Safe Zone</strong> - add this location as a safe zone</li>
<li><strong>Unsafe / Not Safe</strong> - alert the user</li>
<li><strong>Informational</strong> - send location information without marking it safe</li>
</ul>

<p>
<strong>Alert ID:</strong> {alert_id}
</p>

<p>
Regards,<br>
SafePath AI Team
</p>

</body>
</html>
"""

    message.add_alternative(
        html_content,
        subtype="html"
    )

    with smtplib.SMTP(
        settings.SMTP_HOST,
        settings.SMTP_PORT
    ) as server:

        server.starttls()

        server.login(
            settings.SMTP_USERNAME,
            settings.SMTP_PASSWORD
        )

        server.send_message(message)