import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "so12879547"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.seleniumhq.selenium" % "selenium-java" % "2.35.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
