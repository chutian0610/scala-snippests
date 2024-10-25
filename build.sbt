import MyResolvers._
import MyDependencies._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.github.chutian0610"
ThisBuild / scalaVersion := "3.4.1"
ThisBuild / semanticdbEnabled:= true

// ================================ multi projects structure ==============================================

lazy val root = (project in file("."))
  .settings(
    name := "scala-snippest",
    commonSettings
  )
  .aggregate(
    antlr4_demo,common
  )

lazy val antlr4_demo = (project in file("antlr4-demo"))
  .enablePlugins(Antlr4Plugin)
  .settings(
    name := "antlr4-demo",
    commonSettings,
    Antlr4 / antlr4GenVisitor := true,
    Antlr4 / antlr4GenListener := true,
    Antlr4 / antlr4Version := "4.13.1",
    Antlr4 / antlr4PackageName := Some ("io.github.chutian0610.antlr4.demo"),
    libraryDependencies ++= Seq (
      scalatest
      ,antlr4
      ,guava
      ,scalaLogging
      ,slf4jSimple
    )
  )
lazy val common = (project in file("common"))
  .settings(
    name := "common",
    commonSettings,
    libraryDependencies ++= Seq (
      scalatest
      ,guava
      ,scalaLogging
      ,slf4jSimple
      ,enumeratum
    )
  )

// ===================================== common setting ======================================================

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers := allResolver
)

/*
 * @see https://docs.scala-lang.org/overviews/compiler-options/index.html
 */
lazy val compilerOptions = Seq(
  "-encoding", "utf8",
  "-feature",
  "-language:existentials",
  "-language:implicitConversions",
  "-unchecked",
  "-explain"
)