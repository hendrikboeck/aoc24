package day04

import scala.io.{ Codec, Source }

object Part1 {

    private def transposeToDiagonals(lines: List[String], direction: String): List[String] = {
        val maxLength = lines.map(_.length).max

        def getDiagonal45(k: Int): String = lines
            .indices
            .collect {
                case i if i + k >= 0 && i + k < lines(i).length => lines(i)(i + k)
            }
            .mkString

        def getDiagonalMinus45(k: Int): String = lines
            .indices
            .collect {
                case i if i - k >= 0 && i - k < lines(i).length =>
                    lines(lines.length - 1 - i)(i - k)
            }
            .mkString

        val diagonals = (-maxLength + 1 until maxLength).map {
            k =>
                direction match {
                    case "45"  => getDiagonal45(k)
                    case "-45" => getDiagonalMinus45(k)
                    case _     => throw new RuntimeException("Invalid direction")
                }
        }

        diagonals.filter(_.nonEmpty).toList
    }

    private def countXmas(lines: List[String]): Int = {
        val xmasPattern = """XMAS""".r
        val samxPattern = """SAMX""".r

        lines
            .map(text => xmasPattern.findAllIn(text).length + samxPattern.findAllIn(text).length)
            .sum
    }

    def solve(inputPath: String): Int = {

        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().toList
            finally src.close()

        val transposedMatrix = lines.map(_.toCharArray.toList).transpose.map(_.mkString)

        val horizontalXmas  = countXmas(lines)
        val verticalXMas    = countXmas(transposedMatrix)
        val diagonalXmas45  = countXmas(transposeToDiagonals(lines, "45"))
        val diagonalXmas_45 = countXmas(transposeToDiagonals(lines, "-45"))

        horizontalXmas + verticalXMas + diagonalXmas45 + diagonalXmas_45
    }

}
