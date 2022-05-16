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

libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
libraryDependencies += ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13)

libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.9.2").cross(CrossVersion.for3Use2_13)

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"
libraryDependencies += ("com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion).cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-stream" % AkkaVersion).cross(CrossVersion.for3Use2_13)
libraryDependencies += ("com.typesafe.akka" %% "akka-http" % AkkaHttpVersion).cross(CrossVersion.for3Use2_13)

//Slick
//libraryDependencies += ("com.typesafe.slick" %% "slick" % "3.3.3").cross(CrossVersion.for3Use2_13)
libraryDependencies +=("com.github.slick.slick" % "slick_3" % "nafg~dottyquery-SNAPSHOT")
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.36"
libraryDependencies += ("com.typesafe.slick" %% "slick-hikaricp" % "3.3.3").cross(CrossVersion.for3Use2_13)
libraryDependencies += "org.postgresql" % "postgresql" % "42.3.5"

//MongoDB
libraryDependencies += ("org.mongodb.scala" %% "mongo-scala-driver" % "4.6.0").cross(CrossVersion.for3Use2_13)