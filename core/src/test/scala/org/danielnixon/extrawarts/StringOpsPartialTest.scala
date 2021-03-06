package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

class StringOpsPartialTest extends FunSuite {

  test("can't use StringOps#toBoolean") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toBoolean
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toBoolean is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use StringOps#toByte") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toByte
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toByte is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use StringOps#toShort") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toShort
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toShort is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use StringOps#toInt") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toInt
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toInt is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use StringOps#toLong") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toLong
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toLong is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use StringOps#toFloat") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toFloat
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toFloat is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use StringOps#toDouble") {
    val result = WartTestTraverser(StringOpsPartial) {
      val foo = "foo".toDouble
    }
    assertResult(List("[wartremover:StringOpsPartial] StringOps#toDouble is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("StringOpsPartial wart obeys SuppressWarnings") {
    val result = WartTestTraverser(StringOpsPartial) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.StringOpsPartial"))
      val foo = "foo".toInt
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
