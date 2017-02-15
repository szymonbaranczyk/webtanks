

lazy val akkaVersion = "2.4.11"
lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)
lazy val serverDependencies = Seq(
  "com.vmunier" %% "scalajs-scripts" % "1.0.0",
  "com.lihaoyi" %% "upickle" % "0.4.3"
)
lazy val `server` = (project in file("server")).settings(
  scalaVersion := "2.11.8",
  libraryDependencies ++= akkaDependencies,
  libraryDependencies ++= serverDependencies,
  unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" ),
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(rjs, digest, gzip),
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value
).enablePlugins(PlayScala, SbtWeb).
  dependsOn(sharedJvm)



lazy val `client` = (project in file ("client")).settings(
  scalaVersion := "2.11.8",
  persistLauncher := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "com.lihaoyi" %%% "upickle" % "0.4.3"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)


lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := "2.11.8").
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js


onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value