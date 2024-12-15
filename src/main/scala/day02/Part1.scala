package day02

import scala.io.{ Codec, Source }

object Part1 {

    def getReport(line: String): List[Int] = line.split(' ').filter(_.nonEmpty).map(_.toInt).toList

    def isSafe(report: List[Int]): Boolean = {
        val sorted = report.sorted == report || report.sorted == report.reverse
        val dst = report
            .sliding(2)
            .collect {
                case Seq(prev, curr) => Math.abs(prev - curr).toInt
            }
            .filter(i => i > 3 || i == 0)
            .toList

        sorted && dst.isEmpty
    }

    def solve(inputPath: String): Int = {

        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().toList
            finally src.close()

        val reports     = lines.map(_.trim).filter(_.nonEmpty).map(getReport)
        val safeReports = reports.count(isSafe)

        safeReports
    }

}
