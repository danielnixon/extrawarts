package org.danielnixon.extrawarts

import wartremover.Wart

object ExtraWart {
  val EnumerationPartial: Wart = wart("EnumerationPartial")
  val FutureObject: Wart = wart("FutureObject")
  val GenMapLikePartial: Wart = wart("GenMapLikePartial")
  val GenTraversableLikeOps: Wart = wart("GenTraversableLikeOps")
  val GenTraversableOnceOps: Wart = wart("GenTraversableOnceOps")
  val ScalaGlobalExecutionContext: Wart = wart("ScalaGlobalExecutionContext")
  val StringOpsPartial: Wart = wart("StringOpsPartial")
  val ThrowablePartial: Wart = wart("ThrowablePartial")
  val TraversableOnceOps: Wart = wart("TraversableOnceOps")
  val UnsafeContains: Wart = wart("UnsafeContains")

  private def wart(name: String) = {
    Wart.custom(s"org.danielnixon.extrawarts.$name")
  }
}
