import scala.util.control.NonFatal

object Main:
  def main(args: Array[String]): Unit =
    try
      Log.info("Fetching forecast...")
      val days = Weather.getForecast()

      Log.info("Saving reports...")
      val report = Writer.saveTxt(days, "/app/output/report.txt")
      Writer.saveCsv(days, "/app/output/report.csv")

      Log.info("Sending notification...")
      Notify.send(report)

      Log.info("Done.")
    catch
      case NonFatal(e) =>
        Log.error("Report failed", e)
        sys.exit(1)
