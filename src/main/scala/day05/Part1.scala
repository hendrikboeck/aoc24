package day05

import scala.io.{ Codec, Source }

object Part1 {

    def getPageTuple(line: String): (Int, Int) =
        line.split('|').map(_.trim).filter(_.nonEmpty) match {
            case Array(l, r) => (l.toInt, r.toInt)
            case default     => throw new RuntimeException(s"Invalid input: $default")
        }

    def getPageList(line: String): List[Int] = line
        .split(",")
        .map(_.trim)
        .filter(_.nonEmpty)
        .map(_.toInt)
        .toList

    def isOrderedCorrectly(pageList: List[Int], orderedMap: Map[Int, List[Int]]): Boolean = {
        var refMap = orderedMap.filter {
            case (key, _) => pageList.contains(key)
        }

        !pageList
            .iterator
            .exists {
                i =>
                    refMap = refMap.removed(i)
                    refMap.values.flatten.toSet.contains(i)
            }
    }

    def solve(inputPath: String): Int = {

        implicit val codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val lines =
            try src.getLines().map(_.trim).toList
            finally src.close()

        val (sec1, sec2) = lines.splitAt(lines.indexOf("")) match {
            case (a, b) if b.nonEmpty => (a, b.drop(1))
            case _ => throw new RuntimeException("failed to find section divider")
        }

        val orderedMap = sec1.map(getPageTuple).groupMap(_._1)(_._2)
        val pages      = sec2.map(getPageList)

        pages
            .filter(isOrderedCorrectly(_, orderedMap))
            .map(part => part((part.length / 2).toInt))
            .sum
    }

}
