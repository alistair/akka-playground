name := "scala-worker"

version := "1.0.0"

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-deprecation")

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"

libraryDependencies ++= {
  val akkaVersion = "2.3.6"
  val sprayVersion = "1.3.1"
  Seq(
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-json" % "1.2.6",
    "io.spray" %% "spray-client" % sprayVersion,
    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "org.specs2" %% "specs2-core" % "2.4.4" % "test"
  )
}

org.scalastyle.sbt.ScalastylePlugin.Settings


