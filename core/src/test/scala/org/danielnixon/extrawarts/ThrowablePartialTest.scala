package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

class ThrowablePartialTest extends FunSuite {

  test("can't use Throwable#getCause") {
    val result = WartTestTraverser(ThrowablePartial) {
      val foo = new Throwable().getCause
    }
    assertResult(List("[wartremover:ThrowablePartial] Throwable#getCause is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("ThrowablePartial wart obeys SuppressWarnings") {
    val result = WartTestTraverser(ThrowablePartial) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.ThrowablePartial"))
      val foo = new Throwable().getCause
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
