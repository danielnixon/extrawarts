package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

import scala.collection.GenTraversableLike

class GenTraversableLikeOpsTest extends FunSuite {

  val list: GenTraversableLike[Int, List[Int]] = Nil

  test("can't use GenTraversableLike#head") {
    val result = WartTestTraverser(GenTraversableLikeOps) {
      println(list.head)
    }
    assertResult(List("[wartremover:GenTraversableLikeOps] GenTraversableLike#head is disabled - use GenTraversableLike#headOption instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use GenTraversableLike#tail") {
    val result = WartTestTraverser(GenTraversableLikeOps) {
      println(list.tail)
    }
    assertResult(List("[wartremover:GenTraversableLikeOps] GenTraversableLike#tail is disabled - use GenTraversableLike#drop(1) instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use GenTraversableLike#init") {
    val result = WartTestTraverser(GenTraversableLikeOps) {
      println(list.init)
    }
    assertResult(List("[wartremover:GenTraversableLikeOps] GenTraversableLike#init is disabled - use GenTraversableLike#dropRight(1) instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use GenTraversableLike#last") {
    val result = WartTestTraverser(GenTraversableLikeOps) {
      println(list.last)
    }
    assertResult(List("[wartremover:GenTraversableLikeOps] GenTraversableLike#last is disabled - use GenTraversableLike#lastOption instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("GenTraversableLikeOps wart obeys SuppressWarnings") {
    val result = WartTestTraverser(GenTraversableLikeOps) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.GenTraversableLikeOps"))
      val foo = {
        println(list.head)
        println(list.tail)
        println(list.init)
        println(list.last)
      }
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

}
