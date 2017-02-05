package org.danielnixon.extrawarts

object EnumerationPartial extends ClassMethodWart(
  "scala.Enumeration",
  "withName",
  "Enumeration#withName is disabled"
)
