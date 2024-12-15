package day02

import scala.io.{ Codec, Source }

object Part2 {

    private def isSafeAllowFailure(report: List[Int]): Boolean = Part1.isSafe(report) match {
        case true => true

        // brute force: remove each element and check if the report is safe
        case false =>
            report.indices.map(i => report.take(i) ++ report.drop(i + 1)).exists(Part1.isSafe)
    }

    def solve(inputPath: String): Int = {

        implicit val codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().toList
            finally src.close()

        val reports     = lines.map(_.trim).filter(_.nonEmpty).map(Part1.getReport)
        val safeReports = reports.filter(isSafeAllowFailure).length

        safeReports
    }

}
