import logging

from notify import send_notification
from weather import get_forecast
from writer import save_txt, save_csv

logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(message)s")

try:
    logging.info("Fetching forecast...")
    data = get_forecast()

    logging.info("Saving reports...")
    report = save_txt(data, "/app/output/report.txt")
    save_csv(data, "/app/output/report.csv")

    logging.info("Sending notification...")
    send_notification(report)

    logging.info("Done.")
except Exception:
    logging.exception("Report failed")
    raise SystemExit(1)