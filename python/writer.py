import csv


def save_txt(data, path):
    lines = ["Helsinki forecast", "-----------------"]
    for day, high, low in zip(data["time"], data["temperature_2m_max"], data["temperature_2m_min"]):
        lines.append(f"{day}: high {high}°C, low {low}°C")

    report = "\n".join(lines)
    print(report)

    with open(path, "w", encoding="utf-8") as f:
        f.write(report)

    return report


def save_csv(data, path):
    with open(path, "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        writer.writerow(["date", "high_c", "low_c"])
        for day, high, low in zip(data["time"], data["temperature_2m_max"], data["temperature_2m_min"]):
            writer.writerow([day, high, low])