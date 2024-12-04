import day01.{ Part1, Part2 }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day01Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day01/test.txt") should be(11)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day01/input.txt") should be(1970720)
    }

    "Part2" should "solve test input" in {
        Part2.solve("day01/test.txt") should be(31)
    }

    it should "solve real puzzle input" in {
        Part2.solve("day01/input.txt") should be(17191599)
    }

}
