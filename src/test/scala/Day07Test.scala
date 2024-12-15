import day07.Part1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day07Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day07/test.txt") should be(3749)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day07/input.txt") should be(20281182715321L)
    }

    // "Part2" should "solve test input" in {
    //     Part2.solve("day05/test.txt") should be(123)
    // }

    // it should "solve real puzzle input" in {
    //     Part2.solve("day05/input.txt") should be(6336)
    // }

}
