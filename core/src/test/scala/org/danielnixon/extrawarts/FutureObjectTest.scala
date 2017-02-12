package org.danielnixon.extrawarts

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FutureObjectTest extends FunSuite {

  val futs: List[Future[String]] = Nil

  test("can't use scala.concurrent.Future#reduce") {
    val result = WartTestTraverser(FutureObject) {
      val foo = Future.reduce(futs)((r, t) => r)
    }
    assertResult(List("[wartremover:FutureObject] Future#reduce is disabled - use Future#fold instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("FutureObject wart obeys SuppressWarnings") {
    val result = WartTestTraverser(FutureObject) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.FutureObject"))
      val foo = Future.reduce(futs)((r, t) => r)
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
