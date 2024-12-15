package day05

import scala.io.{ Codec, Source }

object Part2 {

    private def order(pageList: List[Int], beforeMap: Map[Int, List[Int]]): List[Int] = pageList
        .sortWith((a, b) => !beforeMap.getOrElse(a, Nil).contains(b))

    def solve(inputPath: String): Int = {

        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().map(_.trim).toList
            finally src.close()

        val (sec1, sec2) = lines.splitAt(lines.indexOf("")) match {
            case (a, b) if b.nonEmpty => (a, b.drop(1))
            case _ => throw new RuntimeException("failed to find section divider")
        }

        val rules      = sec1.map(Part1.getPageTuple)
        val orderedMap = rules.groupMap(_._1)(_._2)
        val beforeMap  = rules.groupMap(_._2)(_._1)
        val pages      = sec2.map(Part1.getPageList)

        pages
            .filter(!Part1.isOrderedCorrectly(_, orderedMap))
            .map(order(_, beforeMap))
            .map(part => part((part.length / 2).toInt))
            .sum
    }

}