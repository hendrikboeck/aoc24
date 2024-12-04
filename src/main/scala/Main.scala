import scopt.OParser

import scala.reflect.runtime.universe
import scala.util.Try

case class Config(day: Int = 0, input: String = "", part: Int = 1)

object ConfigParser {

    private val builder = OParser.builder[Config]

    private val parser = {
        import builder._
        OParser.sequence(
          programName("aoc24"),
          head("aoc24", "0.1.0"),
          opt[Int]('d', "day")
              .required()
              .valueName("<INT>")
              .action((x, c) => c.copy(day = x))
              .text("day is an integer property"),
          opt[String]('i', "input")
              .required()
              .valueName("<puzzle/test>")
              .action((x, c) => c.copy(input = x))
              .text("input is a string property"),
          opt[Int]('p', "part")
              .required()
              .valueName("<1/2>")
              .validate(
                x =>
                    if (x == 1 || x == 2) success
                    else failure("Value <part> must be 1 or 2")
              )
              .action((x, c) => c.copy(part = x))
              .text("part is an integer property between 1 and 2"),
          help("help").text("prints this usage text"),
        )
    }

    def parse(args: Array[String]): Config = OParser
        .parse(parser, args, Config())
        .getOrElse {
            throw new RuntimeException("Invalid arguments")
        }

}

object Main {

    def main(args: Array[String]): Unit = {
        val config             = ConfigParser.parse(args)
        val (instance, symbol) = loadModule(config.day, config.part)
        val result             = invokeSolveMethod(instance, symbol, config.day, config.input)

        println(result)
    }

    def loadModule(day: Int, part: Int): (Any, universe.ModuleSymbol) = {
        val mirror    = universe.runtimeMirror(getClass.getClassLoader)
        val dayPadded = f"$day%02d"
        val className = s"day$dayPadded.Part$part"

        val symbol = Try(mirror.staticModule(className)).getOrElse {
            throw new RuntimeException(s"Failed to load object: $className")
        }
        val moduleMirror = mirror.reflectModule(symbol)

        (moduleMirror.instance, symbol)
    }

    def invokeSolveMethod(
        instance: Any,
        symbol: universe.ModuleSymbol,
        day: Int,
        input: String,
    ): Any = {
        val mirror         = universe.runtimeMirror(getClass.getClassLoader)
        val inputPath      = f"day$day%02d/$input.txt"
        val instanceMirror = mirror.reflect(instance)
        val methodSymbol   = symbol.moduleClass.asType.toType.decl(universe.TermName("solve"))

        if (!methodSymbol.isMethod)
            throw new RuntimeException(s"Method 'solve' not found in ${ symbol.fullName }")

        val solveMethodSymbol = methodSymbol.asMethod
        val method            = instanceMirror.reflectMethod(solveMethodSymbol)

        method(inputPath)
    }

}
