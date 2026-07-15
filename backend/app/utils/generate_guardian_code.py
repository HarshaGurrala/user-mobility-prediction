import random
import string


def generate_guardian_code():

    while True:

        code = "GUARD" + "".join(
            random.choices(
                string.digits,
                k=6
            )
        )

        return code