import scalariform.formatter.preferences._

val scala210 = "2.10.6"
val scala211 = "2.11.8"
val scala212 = "2.12.2"
val coreName = "extrawarts"
val wartremoverVersion = "2.1.1"
val scalatestVersion = "3.0.3"

lazy val commonSettings = Seq(
  organization := "org.danielnixon",
  licenses := Seq("The Apache Software License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  version := "0.3.0-SNAPSHOT",
  publishMavenStyle := true,
  publishArtifact in Test := false,
  publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  homepage := Some(url("https://github.com/danielnixon/extrawarts")),
  pomExtra := {
    <scm>
      <url>git@github.com:danielnixon/extrawarts.git</url>
      <connection>scm:git:git@github.com:danielnixon/extrawarts.git</connection>
    </scm>
      <developers>
        <developer>
          <id>danielnixon</id>
          <name>Daniel Nixon</name>
          <url>https://danielnixon.org/</url>
        </developer>
      </developers>
  },
  coverageMinimum := 90,
  coverageFailOnMinimum := true,
  scalariformPreferences := scalariformPreferences.value
    .setPreference(DoubleIndentClassDeclaration, true)
    .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Ywarn-dead-code",
    "-Ywarn-inaccessible",
    "-Ywarn-value-discard",
    "-Ywarn-numeric-widen",
    "-Ywarn-nullary-override")
)

lazy val core = Project(
  id = "core",
  base = file("core")
).settings(commonSettings ++ Seq(
  name := coreName,
  scalaVersion := scala211,
  crossScalaVersions := Seq(scala211, scala212),
  libraryDependencies ++= Seq(
    "org.wartremover" %% "wartremover" % wartremoverVersion,
    "org.scalatest" %% "scalatest" % scalatestVersion % Test
  ),
  dependencyOverrides ++= Set(
    "org.scalatest" %% "scalatest" % scalatestVersion
  ),
  scalacOptions ++= Seq("-Xlint:_", "-Ywarn-unused", "-Ywarn-unused-import")
): _*)

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
  scalaVersion := scala210,
  addSbtPlugin("org.wartremover" %% "sbt-wartremover" % wartremoverVersion),
  scalacOptions += "-Xlint"
): _*)

addCommandAlias("publishLocalCoverageOff", ";clean;coverageOff;compile;test;publishLocal")
addCommandAlias("publishSignedCoverageOff", ";clean;coverageOff;compile;test;publishSigned")