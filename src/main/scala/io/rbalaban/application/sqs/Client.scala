package io.rbalaban.application.sqs

import io.rbalaban.config.Configuration.ServerConfig
import software.amazon.awssdk.auth.credentials.{ AwsBasicCredentials, StaticCredentialsProvider }
import software.amazon.awssdk.regions.Region
import zio.*
import zio.aws.core.config.CommonAwsConfig
import zio.aws.core.config.AwsConfig
import zio.aws.sqs.Sqs

import scala.language.postfixOps

object Client:
  /** TODO: Grab the config from Config layer
    */
  val layer: ZLayer[ServerConfig, Nothing, CommonAwsConfig] = ZLayer.fromFunction { (config: ServerConfig) =>
    CommonAwsConfig(
      region = Some(Region.of(config.awsRegion)),
      credentialsProvider =
        StaticCredentialsProvider.create(AwsBasicCredentials.create(config.awsAccessKey, config.awsSecretAccessKey)),
      endpointOverride = None,
      commonClientConfig = None,
    )
  }
