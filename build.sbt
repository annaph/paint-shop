name := "paint-shop"
organization := "com.gilt"
version := "1.0"

scalaVersion := "2.12.1"
	
scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint:missing-interpolator",
  "-Ywarn-unused-import",
  "-Ywarn-unused",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "junit" % "junit" % "4.10" % "test")
