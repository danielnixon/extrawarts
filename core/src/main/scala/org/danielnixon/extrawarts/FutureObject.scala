package org.danielnixon.extrawarts

object FutureObject extends ObjectMultiWart(
  "org.danielnixon.extrawarts.FutureObject",
  "scala.concurrent.Future",
  List("reduce" -> "Future#reduce is disabled - use Future#fold instead")
)
