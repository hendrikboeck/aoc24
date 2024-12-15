package day04

import scala.io.{ Codec, Source }

object Part2 {

    def solve(inputPath: String): Int = {
        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val grid =
            try src.getLines().map(_.toArray).toArray
            finally src.close()

        val numRows = grid.length
        val numCols =
            if (numRows > 0) grid(0).length
            else 0

        val arms = Seq("MAS", "SAM")

        def isValidArm(
            i: Int,
            j: Int,
            arm: String,
            dx1: Int,
            dy1: Int,
            dx2: Int,
            dy2: Int,
        ): Boolean = {
            val positions = Seq((i + dx1, j + dy1, arm(0)), (i + dx2, j + dy2, arm(2)))
            positions.forall {
                case (x, y, char) => x >= 0 && x < numRows && y >= 0 && y < numCols &&
                    grid(x)(y) == char
            }
        }

        val count = (
          for {
              i <- 0 until numRows
              j <- 0 until numCols
              if grid(i)(j) == 'A'
              arm1 <- arms
              arm2 <- arms
              if isValidArm(i, j, arm1, -1, -1, 1, 1)
              if isValidArm(i, j, arm2, -1, 1, 1, -1)
          } yield 1
        ).size

        count
    }

}
