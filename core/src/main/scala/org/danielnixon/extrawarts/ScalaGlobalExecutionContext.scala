package org.danielnixon.extrawarts

object ScalaGlobalExecutionContext extends ObjectMultiWart(
  "org.danielnixon.extrawarts.ScalaGlobalExecutionContext",
  "scala.concurrent.ExecutionContext",
  List(
    "global" -> "ExecutionContext#global is disabled - declare a dependency on an ExecutionContext instead",
    "Implicits" -> "ExecutionContext#Implicits is disabled - declare a dependency on an ExecutionContext instead"
  )
)

