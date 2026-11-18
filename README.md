# Daily Weather Report

A small automation project: every morning it fetches today's weather forecast, saves it as a text file and a CSV, and sends it to my phone as a push notification.

I built it twice, once in **Python** and once in **Scala**, to compare the two.

## What it does

1. Fetches the forecast from the free [Open-Meteo](https://open-meteo.com) API (no API key needed)
2. Retries up to 3 times if the network fails
3. Saves `report.txt` and `report.csv` to an `output/` folder
4. Sends the report to my phone through [ntfy.sh](https://ntfy.sh)
5. Logs every step with timestamps

It runs in Docker and is scheduled with Windows Task Scheduler.

## Project layout

    python/   Python 3.12 version (requests)
    scala/    Scala 3 version (Scala CLI, requests-scala, upickle)

Both versions are split into small modules: fetching the data, writing the files, sending the notification, and a main file that ties them together.

## How to run it

1. Install the ntfy app on your phone and subscribe to a long, random topic name.
2. Go into `python/` or `scala/`.
3. Copy `.env.example` to `.env` and put your topic name in it.
4. Run:

        docker compose up --build

The reports appear in `output/`, and your phone gets the notification.

To run it again without rebuilding:

    docker compose run --rm report

Rebuild only after you change the code.

To change the city, edit the latitude and longitude in the weather module.

## Scheduling on Windows

A small `run.bat` runs `docker compose run --rm -T report` and appends the output to `output\scheduler.log`. A Task Scheduler task runs it every day at 08:00. The task is set to catch up if the PC was off at that time.

## Tech used

- **Python:** requests, logging, csv
- **Scala:** Scala 3, Scala CLI, requests-scala, upickle, multi-stage Docker build
- Docker and Docker Compose, Windows Task Scheduler, ntfy.sh