name := "scala"

version := "1.0.0"

scalaVersion := "2.13.8"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")

libraryDependencies += "com.newmotion" %% "akka-rabbitmq" % "6.0.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.32"