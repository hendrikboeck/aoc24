name         := "Advent of Code 2024"
version      := "0.1.0"
organization := "github.hendrikboeck"

scalaVersion := "2.13.12"

libraryDependencies ++=
    Seq(
      "org.scala-lang"    % "scala-library" % scalaVersion.value,
      "org.scala-lang"    % "scala-reflect" % scalaVersion.value,
      "org.scalatest"    %% "scalatest"     % "3.2.10" % Test,
      "com.github.scopt" %% "scopt"         % "4.0.1",
    )

resolvers ++= Seq("Maven Central" at "https://repo1.maven.org/maven2/")

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

scalacOptions ++=
    Seq(
      "-deprecation",     // Warn about use of deprecated APIs
      "-feature",         // Warn about features that should be explicitly enabled
      "-unchecked",       // Enable additional warnings where generated code depends on assumptions
      "-explaintypes",    // Explain type errors in more detail
      "-Xlint",           // Enable recommended additional warnings
      "-Ywarn-dead-code", // Warn when dead code is identified
      "-Ywarn-unused:imports", // Warn if imports are unused
    )

scapegoatDisabledInspections := Seq("StoreBeforeReturn")
