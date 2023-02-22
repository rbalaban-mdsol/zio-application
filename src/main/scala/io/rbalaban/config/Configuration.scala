package io.rbalaban.config

import com.typesafe.config.ConfigFactory
import zio._
import zio.config._
import zio.config.ConfigSource._
import zio.config.ConfigDescriptor._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

object Configuration:

  final case class ServerConfig(
      port: Int,
      sqsQueueName: String,
      awsRegion: String,
      awsAccessKey: String,
      awsSecretAccessKey: String,
    )

  object ServerConfig:
    val layer: ZLayer[Any, ReadError[String], ServerConfig] = ZLayer(
      read(
        descriptor[ServerConfig].from(
          TypesafeConfigSource.fromTypesafeConfig(
            ZIO.attempt(ConfigFactory.defaultApplication())
          )
        )
      )
    )
