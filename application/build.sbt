organization := "com.company"

name := "application"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "org.specs2" % "specs2_2.10" % "2.2.2" % "test"
    , "com.company" % "objects-commons_2.10" % "0.1-SNAPSHOT"
    , "com.company" % "objects-commons_2.10" % "0.1-SNAPSHOT" % "test" classifier "tests" //classifier test in SBT 12
)