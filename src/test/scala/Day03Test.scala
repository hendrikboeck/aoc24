import day03.{ Part1, Part2 }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day03Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day03/test.txt") should be(161)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day03/input.txt") should be(179571322)
    }

    "Part2" should "solve test input" in {
        Part2.solve("day03/test.txt") should be(48)
    }

    it should "solve real puzzle input" in {
        Part2.solve("day03/input.txt") should be(103811193)
    }

}
