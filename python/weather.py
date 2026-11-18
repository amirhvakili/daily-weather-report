import logging
import time

import requests

URL = "https://api.open-meteo.com/v1/forecast?latitude=60.17&longitude=24.94&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=1"


def get_forecast(attempts=3, wait=5):
    for attempt in range(1, attempts + 1):
        try:
            response = requests.get(URL, timeout=10)
            response.raise_for_status()
            return response.json()["daily"]
        except requests.exceptions.RequestException as error:
            logging.warning(f"Attempt {attempt} of {attempts} failed: {error}")
            if attempt == attempts:
                raise
            time.sleep(wait)