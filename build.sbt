val zioVersion            = "2.0.2"
val zioJsonVersion        = "0.3.0-RC10"
val zioConfigVersion      = "3.0.2"
val zioLoggingVersion     = "2.1.2"
val logbackClassicVersion = "1.4.4"
val postgresqlVersion     = "42.5.0"
val testContainersVersion = "0.40.11"
val zioMockVersion        = "1.0.0-RC8"
val zioHttpVersion        = "2.0.0-RC10"
val zioAWSVersion         = "5.17.280.1"
val quillVersion          = "4.6.0"
val zioConfigMagnolia     = "3.0.2"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "io.rbalaban",
        scalaVersion := "3.2.1",
      )
    ),
    name           := "sensorcloud-zio-app",
    libraryDependencies ++= Seq(
      "io.getquill"   %% "quill-jdbc-zio"      % quillVersion excludeAll (
        ExclusionRule(organization = "org.scala-lang.modules")
      ),
      "org.postgresql" % "postgresql"          % postgresqlVersion,
      "dev.zio"       %% "zio"                 % zioVersion,
      "dev.zio"       %% "zio-streams"         % zioVersion,
      "io.d11"        %% "zhttp"               % zioHttpVersion,
      "dev.zio"       %% "zio-config"          % zioConfigVersion,
      "dev.zio"       %% "zio-config-typesafe" % zioConfigVersion,
      "dev.zio"       %% "zio-config-magnolia" % zioConfigMagnolia,
      "ch.qos.logback" % "logback-classic"     % logbackClassicVersion,
      "dev.zio"       %% "zio-json"            % zioJsonVersion,
      "dev.zio"       %% "zio-aws-core"        % zioAWSVersion,
      "dev.zio"       %% "zio-aws-sqs"         % zioAWSVersion,
      "dev.zio"       %% "zio-aws-netty"       % zioAWSVersion,

      // logging
      "dev.zio"       %% "zio-logging"       % zioLoggingVersion,
      "dev.zio"       %% "zio-logging-slf4j" % zioLoggingVersion,
      "ch.qos.logback" % "logback-classic"   % logbackClassicVersion,

      // test
      "dev.zio"      %% "zio-test"                        % zioVersion            % Test,
      "dev.zio"      %% "zio-test-sbt"                    % zioVersion            % Test,
      "dev.zio"      %% "zio-test-junit"                  % zioVersion            % Test,
      "dev.zio"      %% "zio-mock"                        % zioMockVersion        % Test,
      "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainersVersion % Test,
      "dev.zio"      %% "zio-test-magnolia"               % zioVersion            % Test,
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
  )
  .enablePlugins(JavaAppPackaging)
