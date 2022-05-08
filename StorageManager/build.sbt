name := "StorageManager"
version := "1.0"
scalaVersion := "3.1.1"
crossScalaVersions ++= Seq("2.13.8", "3.1.1")

lazy val storage = (project in file("."))
  .settings(
    assembly / assemblyOutputPath :=
      file(baseDirectory.value + "/artifacts/StorageManager-" + version.value + ".jar"),
    assembly / assemblyMergeStrategy := {
    case PathList("reference.conf") => MergeStrategy.concat
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  })

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"
libraryDependencies += ("com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion).cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-stream" % AkkaVersion).cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-http" % AkkaHttpVersion).cross(CrossVersion.for3Use2_13)
