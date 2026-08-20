import smtplib
from email.message import EmailMessage

from app.core.config import settings




def send_sos_email(
    recipient_email: str,
    user_name: str,
    latitude: float,
    longitude: float,
    sos_message: str
):

    maps_link = f"https://www.google.com/maps?q={latitude},{longitude}"

    message = EmailMessage()

    message["Subject"] = "🚨 SafePath AI - SOS EMERGENCY ALERT"
    message["From"] = settings.SMTP_FROM_EMAIL
    message["To"] = recipient_email

    # ==========================================================
    # PLAIN TEXT EMAIL
    # ==========================================================

    message.set_content(
        f"""
🚨 SOS EMERGENCY ALERT
==============================

SafePath AI has detected an emergency situation.

USER INFORMATION
------------------------------
User:
{user_name}

EMERGENCY INFORMATION
------------------------------
Emergency Message:
{sos_message}

The {user_name} has activated the SOS emergency alert
and may require immediate assistance.

CURRENT LOCATION
------------------------------
Latitude:
{latitude}

Longitude:
{longitude}

Google Maps Location:
{maps_link}

Please open the Google Maps location to view
the user's current position.

IMPORTANT
------------------------------
• This is an automated emergency alert from SafePath AI.
• Please contact the user immediately.
• If the {user_name}cannot be reached, check their current location.
• The location provided is the latest available GPS location.
• Please take appropriate action if the situation appears dangerous.

SAFETY REMINDER
------------------------------
Do not ignore this alert if the user may be in danger.

Regards,
SafePath AI Team

This is an automated message.
Please do not reply to this email.
"""
    )

    # ==========================================================
    # HTML EMAIL
    # ==========================================================

    html_content = f"""
<html>
    <body style="font-family: Arial, sans-serif;
                 background-color:#f3f4f6;
                 padding:20px;">

        <div style="
            max-width:600px;
            margin:auto;
            background:white;
            padding:25px;
            border-radius:12px;
        ">

            <h2 style="color:#DC2626;">
                🚨 SOS EMERGENCY ALERT
            </h2>

            <p>
                <strong>
                    SafePath AI has detected an emergency situation.
                </strong>
            </p>

            <hr>

            <h3>User Information</h3>

            <p>
                <strong>User:</strong> {user_name}
            </p>

            <h3 style="color:#DC2626;">
                Emergency Information
            </h3>

            <p>
                <strong>Emergency Message:</strong>
            </p>

            <p>
                {sos_message}
            </p>

            <p>
                The user has activated the SOS emergency alert
                and may require immediate assistance.
            </p>

            <h3>📍 Current Location</h3>

            <p>
                <strong>Latitude:</strong> {latitude}<br>
                <strong>Longitude:</strong> {longitude}
            </p>

            <p>
                <a href="{maps_link}"
                   style="
                       display:inline-block;
                       padding:12px 24px;
                       background-color:#DC2626;
                       color:white;
                       text-decoration:none;
                       border-radius:6px;
                       font-weight:bold;
                   ">
                    📍 View User's Current Location
                </a>
            </p>

            <h3>⚠️ Important</h3>

            <ul>
                <li>
                    This is an automated emergency alert from SafePath AI.
                </li>

                <li>
                    Please contact the user immediately.
                </li>

                <li>
                    If the user cannot be reached,
                    check their current location.
                </li>

                <li>
                    The location represents the latest available GPS position.
                </li>

                <li>
                    Please take appropriate action if the situation
                    appears dangerous.
                </li>
            </ul>

            <div style="
                background-color:#FEF2F2;
                padding:15px;
                border-radius:8px;
                margin-top:20px;
            ">

                <strong style="color:#DC2626;">
                    🚨 Safety Reminder
                </strong>

                <p>
                    Please do not ignore this alert if the user
                    may be in danger.
                </p>

            </div>

            <hr>

            <p style="color:#666;">
                Regards,<br>
                <strong>SafePath AI Team</strong>
            </p>

            <p style="font-size:12px;color:#999;">
                This is an automated emergency message.
                Please do not reply to this email.
            </p>

        </div>

    </body>
</html>
"""

    message.add_alternative(
        html_content,
        subtype="html"
    )

    # ==========================================================
    # SEND EMAIL
    # ==========================================================

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


def send_unknown_location_email(
        recipient_email: str,
        user_name: str,
        latitude: float,
        longitude: float,
        location_address: str,
        event_time: str
    ):

        maps_link = f"https://www.google.com/maps?q={latitude},{longitude}"

        message = EmailMessage()

        message["Subject"] = "⚠️ SafePath AI - UNKNOWN LOCATION ALERT"
        message["From"] = settings.SMTP_FROM_EMAIL
        message["To"] = recipient_email

        # ==========================================================
        # PLAIN TEXT EMAIL
        # ==========================================================

        message.set_content(
            f"""
    ⚠️ UNKNOWN LOCATION ALERT
    ==============================

    SafePath AI has detected that the user has
    left their registered safe zone.

    USER INFORMATION
    ------------------------------
    User:
    {user_name}

    LOCATION INFORMATION
    ------------------------------
    Location:
    {location_address}

    Latitude:
    {latitude}

    Longitude:
    {longitude}

    Time:
    {event_time}

    Google Maps Location:
    {maps_link}

    The user is currently outside their registered
    safe zone.

    Please check the user's location and contact
    them if necessary.

    IMPORTANT
    ------------------------------
    • This is an automated safety notification from SafePath AI.
    • The user has entered an unknown location.
    • Please verify the user's safety.
    • The location provided is the latest available GPS location.

    Regards,
    SafePath AI Team

    This is an automated message.
    Please do not reply to this email.
    """
        )

        # ==========================================================
        # HTML EMAIL
        # ==========================================================

        html_content = f"""
    <html>
        <body style="
            font-family: Arial, sans-serif;
            background-color:#f3f4f6;
            padding:20px;
        ">

            <div style="
                max-width:600px;
                margin:auto;
                background:white;
                padding:25px;
                border-radius:12px;
            ">

                <h2 style="color:#D97706;">
                    ⚠️ UNKNOWN LOCATION ALERT
                </h2>

                <p>
                    <strong>
                        SafePath AI has detected that the user
                        has left their registered safe zone.
                    </strong>
                </p>

                <hr>

                <h3>User Information</h3>

                <p>
                    <strong>User:</strong> {user_name}
                </p>

                <h3 style="color:#D97706;">
                    📍 Location Information
                </h3>

                <p>
                    <strong>Location:</strong>
                    {location_address}
                </p>

                <p>
                    <strong>Latitude:</strong> {latitude}<br>
                    <strong>Longitude:</strong> {longitude}<br>
                    <strong>Time:</strong> {event_time}
                </p>

                <p>
                    <a href="{maps_link}"
                    style="
                        display:inline-block;
                        padding:12px 24px;
                        background-color:#D97706;
                        color:white;
                        text-decoration:none;
                        border-radius:6px;
                        font-weight:bold;
                    ">
                        📍 View User's Location
                    </a>
                </p>

                <div style="
                    background-color:#FFFBEB;
                    padding:15px;
                    border-radius:8px;
                    margin-top:20px;
                ">

                    <strong style="color:#D97706;">
                        ⚠️ Safety Information
                    </strong>

                    <p>
                        The user is currently outside their
                        registered safe zone. Please verify
                        their safety if necessary.
                    </p>

                </div>

                <hr>

                <p style="color:#666;">
                    Regards,<br>
                    <strong>SafePath AI Team</strong>
                </p>

                <p style="font-size:12px;color:#999;">
                    This is an automated safety notification.
                    Please do not reply to this email.
                </p>

            </div>

        </body>
    </html>
    """

        message.add_alternative(
            html_content,
            subtype="html"
        )

        # ==========================================================
        # SEND EMAIL
        # ==========================================================

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


