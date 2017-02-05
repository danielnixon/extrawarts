package org.danielnixon.extrawarts

object GenMapLikePartial extends ClassMethodWart(
  "scala.collection.GenMapLike",
  "apply",
  "GenMapLike#apply is disabled - use GenMapLike#get instead"
)
