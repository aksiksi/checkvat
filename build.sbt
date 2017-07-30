name := "checkvat"

organization := "me.assil"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

crossScalaVersions := Seq("2.12.3", "2.11.11")
