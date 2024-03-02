import play.sbt.PlayRunHook
import sbt._

import scala.sys.process.Process

/**
  * Frontend build play run hook.
  * https://www.playframework.com/documentation/2.8.x/SBTCookbook
  */
object FrontendRunHook {
  def apply(base: File): PlayRunHook = {
    object UIBuildHook extends PlayRunHook {

      def makeProcessString(cmd: String): String = {
        // Windows requires npm commands prefixed with cmd /c
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
          s"cmd /c $cmd"
        } else {
          cmd
        }
      }

      val frontend: File = base / "ui"

      /**
       * Runs the given shell command in the frontend dir, blocks until it is done, and returns the return code.
       */
      def runAndWait(cmd: String): Int = {
        val fullCmd = makeProcessString(cmd)
        Process(fullCmd, frontend).!
      }

      /**
        * Executed before play run start.
        * Run npm install if node modules are not installed.
        */
      override def beforeStarted(): Unit = {
        if (!(base / "ui" / "node_modules").exists()) {
          println(s"Installing npm dependencies")
          runAndWait(FrontendCommands.dependencyInstall)
        }
      }

      /**
        * Executed after play run start.
        * Run npm start
        */
      override def afterStarted(): Unit = {
        println(s"Booting frontend")
        Process(makeProcessString(FrontendCommands.serve), frontend).run
      }

      /**
        * Executed after play run stop.
        * Cleanup frontend execution processes.
        */
      override def afterStopped(): Unit = {
        println(s"Shutting down frontend")
        runAndWait("npx kill-port 3000")
      }
    }

    UIBuildHook
  }
}
