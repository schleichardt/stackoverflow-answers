import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "so16110908"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    playStage <<= (playStage, baseDirectory) map {(stageCommand, baseDir) =>
      val content = """#!/usr/bin/env sh
                      |
                      |exec authbind --deep java $@ -cp "`dirname $0`/staged/*" play.core.server.NettyServer `dirname $0`/..""".stripMargin
      IO.write(baseDir / "target" / "start", content)
      stageCommand
    }
  )

}
