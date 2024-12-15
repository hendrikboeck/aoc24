package day01

import scala.io.{ Codec, Source }

object Part2 {

    def solve(inputPath: String): Int = {

        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().toList
            finally src.close()

        val (ll, rl) = lines.map(_.trim).filter(_.nonEmpty).map(Part1.parseNums).toList.unzip
        val lut      = ll.distinct.map(i => (i, rl.count(_ == i))).toMap
        val result   = ll.map(i => i * lut.getOrElse(i, 0)).sum

        result
    }

}
