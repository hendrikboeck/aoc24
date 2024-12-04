package day02

import scala.io.{ Codec, Source }

object Part1 {

    def getReport(line: String): List[Int] = line.split(' ').filter(!_.isEmpty).map(_.toInt).toList

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

    def apply(inputPath: String): Unit = {

        implicit val codec = Codec.UTF8

        val src         = Source.fromFile(inputPath)
        val reports     = src.getLines().map(_.trim).filter(!_.isEmpty).map(getReport).toList
        val safeReports = reports.filter(isSafe).length

        println(safeReports)

        src.close()
    }

}
