package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

import scala.collection.{ GenMapLike, Map }

class GenMapLikePartialTest extends FunSuite {
  val map: GenMapLike[String, Int, Map[String, Int]] = Map[String, Int]()

  test("can't use GenMapLike#apply") {
    val result = WartTestTraverser(GenMapLikePartial) {
      val foo = map("foo")
    }
    assertResult(List("[wartremover:GenMapLikePartial] GenMapLike#apply is disabled - use GenMapLike#get instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("doesn't detect other `apply` methods") {
    val result = WartTestTraverser(GenMapLikePartial) {
      case class A(apply: Int)
      println(A(1).apply)
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("GenMapLikePartial wart obeys SuppressWarnings") {
    val result = WartTestTraverser(GenMapLikePartial) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.GenMapLikePartial"))
      val foo = map("foo")
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
