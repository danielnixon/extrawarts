package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

class UnsafeContainsTest extends FunSuite {

  test("can't use SeqLike#contains") {
    val result = WartTestTraverser(UnsafeContains) {
      val foo = Seq().contains(1)
    }
    assertResult(List("[wartremover:UnsafeContains] SeqLike#contains is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("UnsafeContains wart obeys SuppressWarnings") {
    val result = WartTestTraverser(UnsafeContains) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.UnsafeContains"))
      val foo = Seq().contains(1)
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
