import sbt._
import sbt.Configurations._

class Project(info: ProjectInfo) extends ParentProject(info) { rootProject =>
  lazy val lib = project("ccf", "ccf", new CcfLibraryProject(_))
  lazy val app = project("app", "app", new TextAppProject(_), lib)
  lazy val perftest = project("perftest", "perftest", new PerftestProject(_), lib)

  class CcfLibraryProject(info: ProjectInfo) extends DefaultProject(info) {
    override def testFrameworks = ScalaCheckFramework :: SpecsFramework :: Nil
    override def mainClass = Some("TestMain")

    val testScopeDependency = "test"

    val databinder_net = "databinder.net repository" at "http://databinder.net/repo"
    val dispatchHttp = "net.databinder" %% "dispatch-http" % "0.7.3"
    val httpclient = "org.apache.httpcomponents" % "httpclient" % "4.0.1"
    val specs = "org.scala-tools.testing" % "specs" % "1.6.0" % testScopeDependency
    val mockito = "org.mockito" % "mockito-core" % "1.8.0" % testScopeDependency
    val scalacheck = "org.scala-tools.testing" % "scalacheck" % "1.5" % testScopeDependency
  }

  class TextAppProject(info: ProjectInfo) extends DefaultProject(info) {
    override def testFrameworks = SpecsFramework :: Nil
    override def mainClass = Some("textapp.TextAppMain")

    val databinder_net = "databinder.net repository" at "http://databinder.net/repo"
    val dispatchHttp = "net.databinder" %% "dispatch-http" % "0.6.3"
    val dispatchJson = "net.databinder" %% "dispatch-json" % "0.6.3"
    val dispatchHttpJson = "net.databinder" %% "dispatch-http-json" % "0.6.3"
    val liftJson = "net.liftweb" % "lift-json" % "1.1-M5"
    val jGoodiesForms = "com.jgoodies" % "forms" % "1.2.0"
  }

  class PerftestProject(info: ProjectInfo) extends DefaultProject(info) {
    override def mainClass = Some("perftest.Perftest")
    val httpclient = "org.apache.httpcomponents" % "httpclient" % "4.0.1"
  }
}