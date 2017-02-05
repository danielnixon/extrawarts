package org.danielnixon.extrawarts

object DateFormatPartial extends ClassMethodWart(
  "java.text.DateFormat",
  "parse",
  "DateFormat#parse is disabled"
)
