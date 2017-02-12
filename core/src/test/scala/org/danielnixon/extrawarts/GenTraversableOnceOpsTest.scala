package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

import scala.collection.GenTraversableOnce

class GenTraversableOnceOpsTest extends FunSuite {

  val list: GenTraversableOnce[Int] = Nil

  test("can't use GenTraversableOnce#reduce") {
    val result = WartTestTraverser(GenTraversableOnceOps) {
      println(list.reduce(_ + _))
    }
    assertResult(List("[wartremover:GenTraversableOnceOps] GenTraversableOnce#reduce is disabled - use GenTraversableOnce#reduceOption or GenTraversableOnce#fold instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use GenTraversableOnce#reduceRight") {
    val result = WartTestTraverser(GenTraversableOnceOps) {
      println(list.reduceRight(_ + _))
    }
    assertResult(List("[wartremover:GenTraversableOnceOps] GenTraversableOnce#reduceRight is disabled - use GenTraversableOnce#reduceRightOption or GenTraversableOnce#foldRight instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("GenTraversableOnceOps wart obeys SuppressWarnings") {
    val result = WartTestTraverser(GenTraversableOnceOps) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.GenTraversableOnceOps"))
      val foo = {
        println(list.reduce(_ + _))
        println(list.reduceRight(_ + _))
      }
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

}
