coverageEnabled := true
coverageExcludedPackages := "de.htwg.se.stratego.view.*"

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
//libraryDependencies += "com.google.inject" % "guice" % "3.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.2"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.10"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1"