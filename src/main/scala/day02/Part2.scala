package day02

import scala.io.{ Codec, Source }

object Part2 {

    def getReport(line: String): List[Int] = line.split(' ').filter(_.nonEmpty).map(_.toInt).toList

    def isSafe(report: List[Int], allowFailure: Boolean = true): Boolean = {
        val sorted = report.sorted == report || report.sorted == report.reverse
        val dst = report
            .sliding(2)
            .collect {
                case Seq(prev, curr) => Math.abs(prev - curr).toInt
            }
            .map(i => i > 3 || i == 0)
            .toList

        var safe = sorted && !dst.exists(x => x)

        if (!safe && allowFailure && dst.length > 1) {
            val combined = dst
                .zipWithIndex
                .collect {
                    case (true, i) => report.patch(i, Nil, 1)
                }

            // println(combined)
        }

        sorted && dst.isEmpty
    }

    def solve(inputPath: String): Int = {

        implicit val codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().toList
            finally src.close()

        val reports     = lines.map(_.trim).filter(!_.isEmpty).map(getReport).toList
        val safeReports = reports.filter(isSafe(_)).length

        safeReports
    }

}
