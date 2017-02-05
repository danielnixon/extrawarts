package org.danielnixon.extrawarts

object TraversableOnceOps extends ClassMultiWart(
  "org.danielnixon.extrawarts.TraversableOnceOps",
  "scala.collection.TraversableOnce",
  List(
    "reduceLeft" -> "TraversableOnce#reduceLeft is disabled - use TraversableOnce#reduceLeftOption or TraversableOnce#foldLeft instead",
    "max" -> "TraversableOnce#max is disabled - use TraversableOnce#reduceLeftOption instead",
    "min" -> "TraversableOnce#min is disabled - use TraversableOnce#reduceLeftOption instead",
    "maxBy" -> "TraversableOnce#maxBy is disabled - use TraversableOnce#reduceLeftOption instead",
    "minBy" -> "TraversableOnce#minBy is disabled - use TraversableOnce#reduceLeftOption instead"
  )
)
