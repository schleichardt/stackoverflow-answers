import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "so19676060"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )

  val smallTests = (s:String) => s.startsWith("Small")

  val largeTests = (s:String) => s.startsWith("Large")

  val mySettings = Seq[Setting[_]]()

  val smallTestSettings = Defaults.testSettings ++ Seq(
    testOptions := Seq(Tests.Filter(smallTests))
  )

  val largeTestSettings = Defaults.testSettings ++ Seq(
    testOptions := Seq(Tests.Filter(largeTests)),
    testOptions in LargeTest += Tests.Argument("-Dmysetting=1")  // <--- SOLUTION HERE
  )

  lazy val SmallTest = config("smalltest") extend(Test)
  lazy val LargeTest = config("largetest") extend(Test)

  val main = play.Project(appName, appVersion, appDependencies)
    .configs(SmallTest)
    .configs(LargeTest)
    .settings(mySettings: _*)
    .settings(inConfig(SmallTest)(smallTestSettings): _*)
    .settings(inConfig(LargeTest)(largeTestSettings): _*)
}
