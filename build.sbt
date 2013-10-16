 
scalaVersion := "2.10.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

seq(sbt.scct.ScctPlugin.instrumentSettings : _*)