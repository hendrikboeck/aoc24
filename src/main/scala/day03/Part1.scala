package day03

import scala.io.{ Codec, Source }

object Part1 {

    def apply(inputPath: String): Unit = {

        implicit val codec = Codec.UTF8

        val src = Source.fromFile(inputPath)
        val text =
            try src.mkString.trim
            finally src.close()

        val mulPattern = """mul\((\d{1,3}),(\d{1,3})\)""".r
        val result = mulPattern
            .findAllIn(text)
            .map {
                case mulPattern(x, y) => x.toInt * y.toInt
                case _                => 0 // neutral element in sum
            }
            .sum

        println(result)
    }

}
