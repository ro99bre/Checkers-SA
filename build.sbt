name := "Checkers"
version := "1.0"
scalaVersion := "3.1.1"
crossScalaVersions ++= Seq("2.13.8", "3.1.1")

lazy val root = (project in file("."))
  .dependsOn(storage)
  .settings(
      sbtassembly.AssemblyKeys.assembly / assemblyOutputPath :=
        file(baseDirectory.value + "/artifacts/Checkers-" + version.value + ".jar"),
      sbtassembly.AssemblyKeys.assembly / assemblyMergeStrategy := {
          case PathList("reference.conf") => MergeStrategy.concat
          case PathList("META-INF", xs@_*) => MergeStrategy.discard
          case x => MergeStrategy.first
      })
  .settings(inConfig(Gatling)(Defaults.testSettings): _*)

lazy val textui = (project in file("TextUI")).dependsOn(root)
lazy val storage = (project in file("StorageManager"))

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"
libraryDependencies += "org.scalatestplus" %% "junit-4-13" % "3.2.11.0" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1"
libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.9.2").cross(CrossVersion.for3Use2_13)

libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
libraryDependencies += ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13)

resolvers += Resolver.sonatypeRepo("snapshots")

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"
libraryDependencies += ("com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion).cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-stream" % AkkaVersion).cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-http" % AkkaHttpVersion).cross(CrossVersion.for3Use2_13)

//Gatling
enablePlugins(GatlingPlugin)
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.7.6" % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "3.7.6" % "test"
Gatling / scalaSource := sourceDirectory.value / "it" / "scala"
