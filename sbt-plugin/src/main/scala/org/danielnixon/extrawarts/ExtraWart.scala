package org.danielnixon.extrawarts

import wartremover.Wart

object ExtraWart {
  @deprecated("Use the Java 8 Time API instead.", "0.3.0")
  val DateFormatPartial: Wart = wart("DateFormatPartial")
  val EnumerationPartial: Wart = wart("EnumerationPartial")
  val FutureObject: Wart = wart("FutureObject")
  val GenMapLikePartial: Wart = wart("GenMapLikePartial")
  val GenTraversableLikeOps: Wart = wart("GenTraversableLikeOps")
  val GenTraversableOnceOps: Wart = wart("GenTraversableOnceOps")
  @deprecated("Use wartremover-contrib's OldTime wart instead.", "0.3.0")
  val LegacyDateTimeCode: Wart = wart("LegacyDateTimeCode")
  val ScalaGlobalExecutionContext: Wart = wart("ScalaGlobalExecutionContext")
  val StringOpsPartial: Wart = wart("StringOpsPartial")
  val TraversableOnceOps: Wart = wart("TraversableOnceOps")
  val UntypedEquality: Wart = wart("UntypedEquality")

  private def wart(name: String) = {
    Wart.custom(s"org.danielnixon.extrawarts.$name")
  }
}
