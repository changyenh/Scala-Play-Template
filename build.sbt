name := "BackendExample"
 
version := "1.0"

scalaVersion := "2.12.1"
      
lazy val `backendexample` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "SarXos Repository" at "http://www.sarxos.pl/repo/maven2"

//routesImport += "com.crystalapps.example.backend.controllers._"

// **Basic Dependencies
// ehcache: Store the request result in cache.
// ws: Asynchronous http client.
// guice: For Dependency Injection.
// jdbc: Replaced by postgresql-jdbc.
libraryDependencies ++= Seq(
  //  jdbc,
  ehcache,
  ws,
  guice
)

//** Scala Reflection
// scala-reflect: Supports reflections for Scala classes.
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

//** Database
// postgresql: PostgreSQL JDBC Connector
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

//** Slick
// slick-codegen: Generates model classes for the first time.
// play-slick: Supports using Scala-style codes to access database.
// play-slick-evolutions: Check and apply table changes through sql file.
libraryDependencies ++= Seq(
  // "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1"
)

//** Json
// play-json: Scala default json serializer.
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.9"

//** OAuth2
// scala-oauth2-core: Support OAuth2 for scala.
libraryDependencies ++= Seq(
  "com.nulab-inc" %% "scala-oauth2-core" % "1.3.0",
  "com.nulab-inc" %% "play2-oauth2-provider" % "1.3.0"
)

//** Logging
// grizzled-slf4j: Scala-friendly slf4j.
// logback-classic: Play Framework uses logback as default logging implementation.
libraryDependencies += "org.clapper" %% "grizzled-slf4j" % "1.3.2"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

//** Encryption
libraryDependencies += "org.bouncycastle" % "bcprov-jdk15on" % "1.59"


//"com.google.api-client" % "google-api-client" % "1.22.0"

/*
slick <<= slickCodeGenTask

sourceGenerators in Compile <+= slickCodeGenTask

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "main/slick").getPath
  val url = "jdbc:postgresql://localhost:5432/CrystalCat"
  val jdbcDriver = "org.postgresql.Driver"
  val slickDriver = "slick.jdbc.PostgresProfile"
  val pkg = "com.example.models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
  val fname = outputDir + "/" + "me/arnaudtanguy/models" + "/Tables.scala"
  Seq(file(fname))
}*/

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      