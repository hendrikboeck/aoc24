package day07

import scala.io.{ Codec, Source }

object Part1 {

    private def isValidCalibration(value: Long, numbers: List[Long]): Boolean = numbers match {
        case head :: Nil => head == value

        case x0 :: x1 :: tail =>
            ((x0 * x1) <= value) && isValidCalibration(value, x0 * x1 :: tail) match {
                case true  => true
                case false => isValidCalibration(value, x0 + x1 :: tail)
            }

        case _ => throw new RuntimeException("Invalid calibration")
    }

    def parseCalibrations(str: String): List[Long] = {
        val parts = str.trim.split(":")
        require(parts.length == 2, s"Invalid calibration: $str")

        val value   = parts(0).toLong
        val numbers = parts(1).split(" ").map(_.trim).filter(_.nonEmpty).map(_.toLong).toList
        require(numbers.nonEmpty, s"Invalid calibration: $str")

        value :: numbers
    }

    def solve(input: String): Long = {

        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(input)
        val lines =
            try src.getLines().filter(_.nonEmpty).toList
            finally src.close()

        val calibrations = lines.map(parseCalibrations)
        val result = calibrations
            .filter {
                case head :: tail => isValidCalibration(head, tail)
                case _            => throw new RuntimeException("Invalid calibration")
            }
            .map(_.head)
            .sum

        result
    }

}
