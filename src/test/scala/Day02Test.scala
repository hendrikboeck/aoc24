import day02.{ Part1, Part2 }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day02Test extends AnyFlatSpec with Matchers {

    "Part1" should "solve test input" in {
        Part1.solve("day02/test.txt") should be(2)
    }

    it should "solve real puzzle input" in {
        Part1.solve("day02/input.txt") should be(287)
    }

//    "Part2" should "solve test input" in {
//        Part2.solve("day02/test.txt") should be(4)
//    }
//
//    it should "solve real puzzle input" in {
//        Part2.solve("day02/input.txt") should be(17191599)
//    }

}
