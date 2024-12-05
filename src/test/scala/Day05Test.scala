import day05.{ Part1, Part2 }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day05Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day05/test.txt") should be(143)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day05/input.txt") should be(4689)
    }

    "Part2" should "solve test input" in {
        Part2.solve("day05/test.txt") should be(123)
    }

    it should "solve real puzzle input" in {
        Part2.solve("day05/input.txt") should be(6336)
    }

}
