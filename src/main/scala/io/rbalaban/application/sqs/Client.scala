package io.rbalaban.application.sqs

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
  val layer: ZLayer[Any, Throwable, CommonAwsConfig] = ZLayer.succeed(
    CommonAwsConfig(
      region = Some(Region.of("us-east-1")),
      credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("key", "key")),
      endpointOverride = None,
      commonClientConfig = None,
    )
  )
