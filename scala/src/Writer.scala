import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

object Writer:
  // Builds the text report, prints it, saves it, and returns it (for the notification).
  def saveTxt(days: Seq[Day], path: String): String =
    val rows = days.map(d => s"${d.date}: high ${d.high}°C, low ${d.low}°C")
    val report = ("Tehran forecast" +: "-----------------" +: rows).mkString("\n")
    println(report)
    write(path, report)
    report

  def saveCsv(days: Seq[Day], path: String): Unit =
    val lines = "date,high_c,low_c" +: days.map(d => s"${d.date},${d.high},${d.low}")
    write(path, lines.mkString("\n") + "\n")

  private def write(path: String, content: String): Unit =
    Files.writeString(Paths.get(path), content, StandardCharsets.UTF_8)
