import random
import string


def generate_safe_path_id():

    code = ''.join(
        random.choices(
            string.ascii_uppercase + string.digits,
            k=6
        )
    )

    return f"SP-{code}"