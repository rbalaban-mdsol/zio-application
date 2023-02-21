package io.rbalaban

import io.getquill.jdbczio.Quill
import io.getquill.PluralizedTableNames
import zhttp.http.*
import zhttp.service.*
import zhttp.service.server.ServerChannelFactory
import zio.*
import zio.config.*
import zio.stream.*
import zio.logging.LogFormat
import zio.logging.backend.SLF4J
import io.rbalaban.api.*
import io.rbalaban.api.healthcheck.*
import io.rbalaban.application.sqs.SqsStreamSettings
import io.rbalaban.config.Configuration.ServerConfig
import zio.aws.core.config.AwsConfig
import zio.aws.sqs.Sqs
import io.rbalaban.application.sqs.{ Client, SqsStream, Utils }
import zio.aws.netty.NettyHttpClient

import scala.language.postfixOps

object Main extends ZIOAppDefault:

  override val bootstrap: ULayer[Unit] = Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  private val dataSourceLayer = Quill.DataSource.fromPrefix("postgres-db")

  private val postgresLayer = Quill.Postgres.fromNamingStrategy(PluralizedTableNames)

  private val healthCheckServiceLayer = HealthCheckServiceLive.layer

  private val awsConfigLayer = Client.layer >>> AwsConfig.configured()

  val routes = HealthCheckRoutes.app

  val program =
    for
      config   <- getConfig[ServerConfig]
      _        <- Server.start(config.port, routes)
      queueUrl <- Utils.getQueueUrl(config.sqsQueueName)
      _        <- SqsStream(queueUrl, SqsStreamSettings()).foreach(msg => Console.printLine(msg.body))
    yield ()

  override val run =
    program.provide(
      healthCheckServiceLayer,
      ServerConfig.layer,
      postgresLayer,
      dataSourceLayer,
      Sqs.live,
      awsConfigLayer,
      NettyHttpClient.default,
    )
