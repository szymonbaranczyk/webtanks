import sbt.Project.projectToRef

lazy val akkaVersion = "2.4.11"
lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)
lazy val serverDependencies = Seq(
  "com.lihaoyi" %% "upickle" % "0.4.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test"
)

lazy val browser = TaskKey[Unit]("Opens default browser")

lazy val browserSettings = browser := {
  val url = new URL("http://localhost:9000")
  streams.value.log.info(s"Opening $url in browser...")
  java.awt.Desktop.getDesktop.browse(url.toURI)
}

lazy val `server` = (project in file("server")).settings(
  scalaVersion := "2.11.8",
  browserSettings,
  libraryDependencies ++= akkaDependencies,
  libraryDependencies ++= serverDependencies,
  unmanagedResourceDirectories in Test += baseDirectory(_ / "target/web/public/test").value,
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(rjs, digest, gzip),
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  run := ((run in Compile) dependsOn browser).evaluated
).enablePlugins(PlayScala, SbtWeb).aggregate(Seq(client).map(projectToRef): _*).
  dependsOn(sharedJvm)



lazy val `client` = (project in file ("client")).settings(
  scalaVersion := "2.11.8",
  persistLauncher := true,
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "upickle" % "0.4.3",
    "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test"
  ),
  jsDependencies +=
    "org.webjars" % "jquery" % "2.1.3" / "2.1.3/jquery.js"
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)


lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(
    scalaVersion := "2.11.8",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  ).
  jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js


onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value