name := "scala-worker"

version := "1.0.0"

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-deprecation")

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"

org.scalastyle.sbt.ScalastylePlugin.Settings


