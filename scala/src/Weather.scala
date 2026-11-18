import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

case class Day(date: String, high: Double, low: Double)

object Weather:
  private val Url =
    "https://api.open-meteo.com/v1/forecast?latitude=35.75&longitude=51.37&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=1"
  // One request. Throws if the network fails or the API answers with an error status.
  private def fetch(): Seq[Day] =
    val response = requests.get(Url, readTimeout = 10000, connectTimeout = 10000)
    val daily = ujson.read(response.text())("daily")
    val dates = daily("time").arr.map(_.str)
    val highs = daily("temperature_2m_max").arr.map(_.num)
    val lows = daily("temperature_2m_min").arr.map(_.num)
    dates.lazyZip(highs).lazyZip(lows).map((d, h, l) => Day(d, h, l)).toSeq

  // Tries up to `attempts` times, waiting `waitMs` between tries.
  def getForecast(attempts: Int = 3, waitMs: Long = 5000): Seq[Day] =
    @tailrec
    def loop(attempt: Int): Seq[Day] =
      Try(fetch()) match
        case Success(days) => days
        case Failure(e) =>
          Log.warn(s"Attempt $attempt of $attempts failed: ${e.getMessage}")
          if attempt >= attempts then throw e
          Thread.sleep(waitMs)
          loop(attempt + 1)

    loop(1)
