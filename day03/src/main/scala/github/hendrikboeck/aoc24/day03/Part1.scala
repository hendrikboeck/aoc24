package github.hendrikboeck.aoc24.day03

import scala.io.{ Codec, Source }

object Part1 {

    def apply(inputPath: String): Unit = {

        implicit val codec = Codec.UTF8

        val src   = Source.fromFile(inputPath)
        val lines = src.getLines().map(_.trim).filter(!_.isEmpty)

        src.close()
    }

}
