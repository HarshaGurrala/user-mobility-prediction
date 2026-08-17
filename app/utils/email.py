import smtplib
from email.message import EmailMessage

from app.core.config import settings


def send_reset_email(
    recipient_email: str,
    reset_link: str
):

    message = EmailMessage()

    message["Subject"] = "SafePath AI - Reset Your Password"
    message["From"] = settings.SMTP_FROM_EMAIL
    message["To"] = recipient_email

    # Plain-text fallback
    message.set_content(
        f"""
Hello,

We received a request to reset your SafePath AI password.

Reset your password using this link:

{reset_link}

This link is temporary and can only be used once.

If you did not request a password reset, you can safely ignore this email.

Regards,
SafePath AI Team
"""
    )

    # HTML version
    html_content = f"""
    <html>
        <body>
            <h2>SafePath AI - Reset Your Password</h2>

            <p>Hello,</p>

            <p>
                We received a request to reset your SafePath AI password.
            </p>

            <p>
                Click the button below to reset your password:
            </p>

            <p>
                <a href="{reset_link}"
                   style="
                       display:inline-block;
                       padding:12px 24px;
                       background-color:#2563EB;
                       color:white;
                       text-decoration:none;
                       border-radius:6px;
                       font-weight:bold;
                   ">
                    Reset Password
                </a>
            </p>

            <p>
                This link is temporary and can only be used once.
            </p>

            <p>
                If you did not request a password reset,
                you can safely ignore this email.
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