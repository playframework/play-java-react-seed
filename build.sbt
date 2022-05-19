name := """java-play-react-seed"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava).settings(
  watchSources ++= (baseDirectory.value / "ui/src" ** "*").get
)

scalaVersion := "2.13.8"

libraryDependencies += guice

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.200"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.22.0" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "4.2.0" % Test

// Make verbose tests
(Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
