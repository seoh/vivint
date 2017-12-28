
name := "hindley-milner"
version := "0.0.1"
scalaVersion := "2.11.11"

enablePlugins(ScalaNativePlugin)


libraryDependencies += "com.lihaoyi" %%% "scalaparse" % "1.0.0"

libraryDependencies += "com.lihaoyi" %%% "utest" % "0.5.4" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")

nativeLinkStubs := true

