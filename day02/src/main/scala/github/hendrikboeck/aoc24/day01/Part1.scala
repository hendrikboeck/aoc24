package github.hendrikboeck.aoc24.day01

import scala.io.{ Codec, Source }

object Part1 {

    def getReport(line: String): List[Int] = line.split(' ').filter(!_.isEmpty).map(_.toInt).toList

    def apply(inputPath: String): Unit = {

        implicit val codec = Codec.UTF8

        val src     = Source.fromFile(inputPath)
        val reports = src.getLines().map(_.trim).filter(!_.isEmpty).map(getReport).toList

        src.close()
    }

}
