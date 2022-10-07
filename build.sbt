name := "sbt"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.dom4j" % "dom4j" % "2.1.3"
  , "jaxen" % "jaxen" % "1.2.0"
  , "org.specs2" %% "specs2-core" % "4.10.0" % "test"
  , "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
)

scalacOptions in Test ++= Seq("-Yrangepos")
