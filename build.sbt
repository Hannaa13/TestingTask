name := "Testing"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.1.1" % Test,
    "org.scalamock" %% "scalamock" % "4.4.0" % Test,
    "org.mockito" %% "mockito-scala-scalatest" % "1.13.0" % Test
  )


