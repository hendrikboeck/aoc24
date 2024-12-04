package day03

import scala.io.{ Codec, Source }

object Part2 {

    def solve(inputPath: String): Int = {

        implicit val codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val text =
            try src.mkString.trim
            finally src.close()

        val enabledText = text
            .split("""do\(\)""")
            .map(_.split("""don't\(\)"""))
            .map {
                case Array(enabled, _*) => enabled
                case _                  => throw new IllegalArgumentException("Invalid input")
            }
            .mkString

        val mulPattern = """mul\((\d{1,3}),(\d{1,3})\)""".r
        val result = mulPattern
            .findAllIn(enabledText)
            .map {
                case mulPattern(x, y) => x.toInt * y.toInt
                case _                => 0 // neutral element in sum
            }
            .sum

        result
    }

}
