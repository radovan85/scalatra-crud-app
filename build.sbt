val ScalatraVersion = "3.1.1"

ThisBuild / scalaVersion := "2.13.15"
ThisBuild / organization := "com.radovan.scalatra"

enablePlugins(SbtTwirl, SbtWar, RevolverPlugin)

lazy val hello = (project in file("."))
  .settings(
    name := "scalatra-webapp",
    version := "0.1.0-SNAPSHOT",

    fork := true,
    mainClass := Some("com.radovan.scalatra.server.JettyLauncher"),

    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra-json" % "3.0.0-M5-jakarta",
      "org.scalatra" %% "scalatra-jakarta" % ScalatraVersion,
      "org.json4s" %% "json4s-jackson" % "4.0.7",
      "ch.qos.logback" % "logback-classic" % "1.5.6" % "runtime",
      "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.17.2",
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.17.2",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.17.2",
      "jakarta.enterprise" % "jakarta.enterprise.cdi-api" % "4.1.0",
      "jakarta.inject" % "jakarta.inject-api" % "2.0.1",
      "jakarta.annotation" % "jakarta.annotation-api" % "3.0.0",
      "org.hibernate.orm" % "hibernate-core" % "6.5.2.Final",
      "jakarta.persistence" % "jakarta.persistence-api" % "3.2.0",
      "com.zaxxer" % "HikariCP" % "5.1.0",
      "org.mariadb.jdbc" % "mariadb-java-client" % "3.5.2",
      "jakarta.validation" % "jakarta.validation-api" % "3.1.1",
      "org.hibernate.validator" % "hibernate-validator" % "8.0.2.Final",
      "org.modelmapper" % "modelmapper" % "3.2.4",
      "org.jboss.weld.servlet" % "weld-servlet-core" % "6.0.2.Final",
      "org.jboss.weld.se" % "weld-se-core" % "6.0.2.Final",
      "jakarta.el" % "jakarta.el-api" % "6.0.1",
      "org.glassfish.expressly" % "expressly" % "6.0.0",
      "net.sf.flexjson" % "flexjson" % "3.3",
      "org.apache.httpcomponents.client5" % "httpclient5" % "5.4.4"

    ),

    watchSources ++= Seq(
      baseDirectory.value / "src" / "main" / "scala",
      baseDirectory.value / "src" / "main" / "resources"
    )
  )
