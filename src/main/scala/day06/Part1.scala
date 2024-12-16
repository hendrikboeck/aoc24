package day06

import scala.annotation.unused
import scala.io.{ Codec, Source }

sealed trait Tile
case object Empty       extends Tile
case object Obstruction extends Tile
case object Marked      extends Tile

sealed trait Direction
case object North extends Direction
case object East  extends Direction
case object South extends Direction
case object West  extends Direction

class Guard(var x: Int, var y: Int, var dir: Direction) {

    def moveForward(): Unit = dir match {
        case North => y -= 1
        case East  => x += 1
        case South => y += 1
        case West  => x -= 1
    }

    def turnRight(): Unit = dir = dir match {
        case North => East
        case East  => South
        case South => West
        case West  => North
    }

}

object Guard {

    def apply(x: Int, y: Int, dir: Direction): Guard = new Guard(x, y, dir)

    def apply(x: Int, y: Int, dir: Char): Guard = dir match {
        case '^' => new Guard(x, y, North)
        case '>' => new Guard(x, y, East)
        case 'v' => new Guard(x, y, South)
        case '<' => new Guard(x, y, West)
        case _   => throw new RuntimeException("invalid direction")
    }

}

class LabMap(tiles: Array[Array[Tile]], guard: Guard) {

    def apply(x: Int, y: Int): Tile = tiles(y)(x)

    private def isInBounds(x: Int, y: Int): Boolean = x >= 0 && x < tiles(0).length && y >= 0 &&
        y < tiles.length

    private def getFront: Option[Tile] = {
        val (x, y) = guard.dir match {
            case North => (guard.x, guard.y - 1)
            case East  => (guard.x + 1, guard.y)
            case South => (guard.x, guard.y + 1)
            case West  => (guard.x - 1, guard.y)
        }

        if (!isInBounds(x, y)) None
        else Some(tiles(y)(x))
    }

    private def markVisited(): Unit = tiles(guard.y)(guard.x) = Marked

    def run: LabMap = {
        var inBounds = true

        while (inBounds) {
            markVisited()

            getFront match {
                case Some(Empty) | Some(Marked) => guard.moveForward()
                case Some(Obstruction)          => guard.turnRight()
                case None                       => inBounds = false
            }
        }

        this
    }

    def getDistinctTiles: Int = tiles.flatten.count(_ == Marked)

    @unused
    def debugPrint(): Unit = {
        for (y <- tiles.indices) {
            for (x <- tiles(y).indices) {
                print(
                  tiles(y)(x) match {
                      case Empty       => '.'
                      case Obstruction => '#'
                      case Marked      => 'X'
                  }
                )
            }
            println()
        }
    }

}

object LabMap {

    def fromString(str: String): LabMap = {
        val chars = str.split("\n").map(_.trim).filter(_.nonEmpty).map(_.toCharArray)
        require(chars.map(_.length).distinct.length == 1, "all lines must be the same length")

        var guard: Option[Guard] = None
        val map = Array.tabulate[Tile](chars.length, chars(0).length) {
            (y, x) =>
                chars(y)(x) match {
                    case '.' => Empty
                    case '#' => Obstruction
                    case 'X' => Marked
                    case c @ ('^' | '>' | 'v' | '<') =>
                        guard = Some(Guard(x, y, c))
                        Empty
                    case c => throw new RuntimeException(s"Invalid character: $c")
                }
        }

        new LabMap(
          map,
          guard.getOrElse(throw new RuntimeException("guard must be placed on the map")),
        )
    }

}

object Part1 {

    def solve(inputPath: String): Int = {

        implicit val codec: Codec = Codec.UTF8

        val src = Source.fromResource(inputPath)
        val str =
            try src.mkString.trim
            finally src.close

        val map = LabMap.fromString(str)
        map.run.getDistinctTiles

    }

}
