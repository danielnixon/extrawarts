package org.danielnixon.extrawarts

object UnsafeContains extends ClassMethodWart(
  "scala.collection.SeqLike",
  "contains",
  "SeqLike#contains is disabled")
