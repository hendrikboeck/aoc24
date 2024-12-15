import day06.Part1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day06Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day06/test.txt") should be(41)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day06/input.txt") should be(4939)
    }

    // "Part2" should "solve test input" in {
    //     Part2.solve("day05/test.txt") should be(123)
    // }

    // it should "solve real puzzle input" in {
    //     Part2.solve("day05/input.txt") should be(6336)
    // }

}
