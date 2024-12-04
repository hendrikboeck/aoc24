import day04.{ Part1, Part2 }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day04Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day04/test.txt") should be(18)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day04/input.txt") should be(2507)
    }

    "Part2" should "solve test input" in {
        Part2.solve("day04/test.txt") should be(9)
    }

    it should "solve real puzzle input" in {
        Part2.solve("day04/input.txt") should be(1969)
    }

}
