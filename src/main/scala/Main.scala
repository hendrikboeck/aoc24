import scopt.OParser
import scala.reflect.runtime.universe
import scala.util.{ Failure, Success, Try }

case class Config(day: Int = 0, input: String = "", part: Int = 1)

object Main {

    def main(args: Array[String]): Unit = {
        val config         = parseArgs(args)
        val moduleInstance = loadModule(config.day, config.part)

        moduleInstance match {
            case Some((instance, symbol)) =>
                val result = invokeSolveMethod(instance, symbol, config.day, config.input)
                println(result)
            case None =>
                println(s"Failed to load module for day ${ config.day } part ${ config.part }")
        }
    }

    def parseArgs(args: Array[String]): Config = {
        val builder = OParser.builder[Config]
        val parser = {
            import builder._
            OParser.sequence(
              programName("cli-parser"),
              head("cli-parser", "1.0"),
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

        OParser.parse(parser, args, Config()) match {
            case Some(config) => config
            case _            => throw new RuntimeException("Invalid arguments")
        }
    }

    def loadModule(day: Int, part: Int): Option[(Any, universe.ModuleSymbol)] = {
        val mirror    = universe.runtimeMirror(getClass.getClassLoader)
        val dayPadded = f"$day%02d"
        val className = s"day$dayPadded.Part$part"

        Try(mirror.staticModule(className)) match {
            case Success(symbol) =>
                val moduleMirror = mirror.reflectModule(symbol)
                Some((moduleMirror.instance, symbol))
            case Failure(exception) =>
                println(s"Failed to load object: $className. Error: ${ exception.getMessage }")
                None
        }
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
