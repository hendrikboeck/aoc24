package day01

import scala.io.{ Codec, Source }

object Part2 {

    def apply(inputPath: String): Unit = {

        implicit val codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val (ll, rl) = src
            .getLines()
            .map(_.trim)
            .filter(!_.isEmpty)
            .map(Part1.parseNums)
            .toList
            .unzip

        val lut    = ll.distinct.map(i => (i, rl.count(_ == i))).toMap
        val result = ll.map(i => i * lut.getOrElse(i, 0)).sum

        println(result)

        src.close()

    }

}
