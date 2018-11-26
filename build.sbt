coverageEnabled := true

name          := "htwg.se.stratego"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion  := "2.12.4"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

connectInput in run := true

resolvers += Resolver.jcenterRepo

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
libraryDependencies += "junit" % "junit" % "4.8" % "test"