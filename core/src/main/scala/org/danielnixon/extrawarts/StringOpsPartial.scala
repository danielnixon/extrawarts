package org.danielnixon.extrawarts

object StringOpsPartial extends ClassMultiWart(
  "org.danielnixon.extrawarts.StringOpsPartial",
  "scala.collection.immutable.StringOps",
  List(
    "toBoolean" -> "StringOps#toBoolean is disabled",
    "toByte" -> "StringOps#toByte is disabled",
    "toShort" -> "StringOps#toShort is disabled",
    "toInt" -> "StringOps#toInt is disabled",
    "toLong" -> "StringOps#toLong is disabled",
    "toFloat" -> "StringOps#toFloat is disabled",
    "toDouble" -> "StringOps#toDouble is disabled"
  )
)