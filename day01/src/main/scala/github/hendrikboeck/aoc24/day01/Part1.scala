package github.hendrikboeck.aoc24.day01

import scala.io.{ Codec, Source }

object Part1 {

    def parseNums(line: String): (Int, Int) = {
        val nums = line.split(' ').filter(!_.isEmpty).map(_.toInt)

        nums match {
            case Array(n0, n1) => (n0, n1)
            case _ => throw new RuntimeException("every row must have exactly 2 numbers")
        }
    }

    def getDistance(nums: List[Int]): Int = nums match {
        case n0 :: n1 :: Nil => (n0 - n1).abs.toInt
        case _               => throw new RuntimeException("expected 2 nums, but got unexpected")
    }

    def apply(inputPath: String): Unit = {

        implicit val codec = Codec.UTF8

        val src    = Source.fromFile(inputPath)
        val nums   = src.getLines().map(_.trim).filter(!_.isEmpty).map(parseNums).toList.unzip
        val result = (nums._1 :: nums._2 :: Nil).map(_.sorted).transpose.map(getDistance).sum

        println(result)

        src.close()
    }

}
