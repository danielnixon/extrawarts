package org.danielnixon.extrawarts

@deprecated("Use the Java 8 Time API instead.", "0.3.0")
object DateFormatPartial extends ClassMethodWart(
  "java.text.DateFormat",
  "parse",
  "DateFormat#parse is disabled"
)
