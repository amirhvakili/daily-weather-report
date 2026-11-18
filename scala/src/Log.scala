import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Tiny logger that prints lines like: 2026-11-17 09:10:33,548 INFO Fetching forecast...
object Log:
  private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS")

  private def log(level: String, message: String): Unit =
    println(s"${LocalDateTime.now.format(format)} $level $message")

  def info(message: String): Unit = log("INFO", message)

  def warn(message: String): Unit = log("WARNING", message)

  def error(message: String, e: Throwable): Unit =
    log("ERROR", message)
    e.printStackTrace()
