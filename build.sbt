import scalariform.formatter.preferences._
import ReleaseTransformations._

val scala210 = "2.10.6"
val scala211 = "2.11.11"
val scala212 = "2.12.3"
val scalaVersions = Seq(scala211, scala212)
val coreName = "extrawarts"
val wartremoverVersion = "2.2.1"
val scalatestVersion = "3.0.4"

lazy val commonSettings = Seq(
  organization := "org.danielnixon",
  licenses := Seq("The Apache Software License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://github.com/danielnixon/extrawarts")),
  scmInfo := Some(
    ScmInfo(url("https://github.com/danielnixon/extrawarts"), "git@github.com:danielnixon/extrawarts.git")),
  developers := List(
    Developer("danielnixon", "Daniel Nixon", "dan.nixon@gmail.com", url("https://danielnixon.org/"))
  ),
  coverageMinimum := 90,
  coverageFailOnMinimum := true,
  scalariformPreferences := scalariformPreferences.value
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
    .setPreference(DanglingCloseParenthesis, Preserve),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Ywarn-dead-code",
    "-Ywarn-inaccessible",
    "-Ywarn-value-discard",
    "-Ywarn-numeric-widen",
    "-Ywarn-nullary-override"),
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 11 => Seq("-Xlint:_")
      case _ => Seq("-Xlint")
    }
  },
  scalaVersion := scala212,
  sbtVersion := {
    scalaBinaryVersion.value match {
      case "2.10" => "0.13.16"
      case _      => "1.0.2"
    }
  }
)

lazy val root = Project(
  id = "root",
  base = file("."),
  aggregate = Seq(core, sbtPlug)
).settings(commonSettings ++ Seq(
  publishArtifact := false,
  releaseCrossBuild := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    releaseStepCommandAndRemaining("+test"),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("+publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommandAndRemaining("sonatypeReleaseAll"),
    pushChanges
  )
): _*).enablePlugins(CrossPerProjectPlugin)

lazy val core = Project(
  id = "core",
  base = file("core")
).settings(commonSettings ++ Seq(
  name := coreName,
  crossScalaVersions := scalaVersions,
  libraryDependencies ++= Seq(
    "org.wartremover" %% "wartremover" % wartremoverVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % Test
  ),
  dependencyOverrides ++= Set(
    "org.scalatest" %% "scalatest" % scalatestVersion
  ),
  scalacOptions ++= Seq("-Ywarn-unused", "-Ywarn-unused-import")
): _*).enablePlugins(CrossPerProjectPlugin)

/**
  * Workaround for https://github.com/sbt/sbt/issues/3393.
  */
def addSbtPluginHack(dependency: ModuleID): Setting[Seq[ModuleID]] =
  libraryDependencies += {
    val sbtV = (sbtBinaryVersion in pluginCrossBuild).value
    val scalaV = (scalaBinaryVersion in update).value
    Defaults.sbtPluginExtra(dependency, sbtV, scalaV)
  }

lazy val sbtPlug: Project = Project(
  id = "sbt-plugin",
  base = file("sbt-plugin")
).enablePlugins(
  BuildInfoPlugin
).disablePlugins(
  ScoverageSbtPlugin
).settings(commonSettings ++ Seq(
  buildInfoKeys := Seq[BuildInfoKey](version, organization, "artifactID" -> coreName),
  buildInfoPackage := s"${organization.value}.$coreName",
  sbtPlugin := true,
  name := s"sbt-$coreName",
  crossScalaVersions := Seq(scala210, scala212),
  addSbtPluginHack("org.wartremover" %% "sbt-wartremover" % wartremoverVersion)
): _*).enablePlugins(CrossPerProjectPlugin)
