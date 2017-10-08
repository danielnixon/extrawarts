package org.danielnixon.extrawarts

object ThrowablePartial extends ClassMethodWart(
  "java.lang.Throwable",
  "getCause",
  "Throwable#getCause is disabled")
