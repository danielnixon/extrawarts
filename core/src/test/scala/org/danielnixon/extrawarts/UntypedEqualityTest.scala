package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

class UntypedEqualityTest extends FunSuite {
  test("can't use eq operator") {
    val result = WartTestTraverser(UntypedEquality) {
      val foo = "" eq ""
    }
    assertResult(List("[wartremover:UntypedEquality] Untyped equality is disabled - use a typesafe alternative"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use ne operator") {
    val result = WartTestTraverser(UntypedEquality) {
      val foo = "" ne ""
    }
    assertResult(List("[wartremover:UntypedEquality] Untyped equality is disabled - use a typesafe alternative"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use equals operator") {
    val result = WartTestTraverser(UntypedEquality) {
      val foo = "" equals ""
    }
    assertResult(List("[wartremover:UntypedEquality] Untyped equality is disabled - use a typesafe alternative"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
