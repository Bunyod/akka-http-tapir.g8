name := "$name$"

import sbt.Keys._
import sbt._
import scala.language.postfixOps

/* Override `Artifact.artifactName`. We do not want to have the Scala version
 * the file name. (https://www.scala-sbt.org/1.x/docs/Artifacts.html) */
artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + module.revision + "." + artifact.extension
}

version in ThisBuild := "$releaseVersion$"

lazy val commonSettings = Seq(
  organization := "$organization$",
  scalaVersion := "$scalaVersion$",
  version := "$version$",
  resolvers ++= Seq(
    DefaultMavenRepository,
    Resolver.jcenterRepo,
    Resolver.sbtPluginRepo("releases"),
    Resolver.mavenLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  scalacOptions ++= CompilerOptions.cOptions,
  test in assembly := {}
)

def baseProject(name: String): Project =
  Project(name, file(name))
    .settings(commonSettings: _*)
    .configs(IntegrationTest)
    .settings(Defaults.itSettings)

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(`$seedName;format="norm"$`, common)

lazy val `$seedName;format="norm"$` = baseProject("$seedName;format="norm"$")
  .dependsOn(common)
  .settings(
    libraryDependencies ++= Dependencies.$seedName;format="norm,word,lower"$,
    Compile/mainClass := Some("$package$.Starter"),
    mainClass in assembly := Some("$package$.Starter")
  )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)


lazy val common = baseProject("common")
  .settings(
    libraryDependencies ++= Dependencies.common
  )

val runServer = inputKey[Unit]("Runs web-server")
val runStart = inputKey[Unit]("Runs web-server")

runServer := {
  (run in Compile in `$seedName;format="norm"$`).evaluated
}

mainClass in ~reStart := Some("$package$.Starter")

