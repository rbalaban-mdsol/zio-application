package io.rbalaban.application.sqs

import zio.aws.sqs.model._

case class SqsStreamSettings(
    attributeName: List[QueueAttributeName] = Nil,
    maxNumberOfMessages: Int = 1,
    messageAttributeNames: List[String] = Nil,
    visibilityTimeout: Option[Int] = Some(30),
    waitTimeSeconds: Option[Int] = Some(20),
    autoDelete: Boolean = true,
    stopWhenQueueEmpty: Boolean = false,
  )
