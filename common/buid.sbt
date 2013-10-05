organization := "com.company"

name := "objects-commons"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "org.specs2" % "specs2_2.10" % "2.2.2" % "test"
)

publishArtifact in (Test, packageBin) := true