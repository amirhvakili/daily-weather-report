import os

import requests


def send_notification(message, title="Daily report"):
    topic = os.environ["NTFY_TOPIC"]
    response = requests.post(
        f"https://ntfy.sh/{topic}",
        data=message.encode("utf-8"),
        headers={"Title": title},
        timeout=10,
    )
    response.raise_for_status()