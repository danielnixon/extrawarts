package org.danielnixon.extrawarts

import java.text.SimpleDateFormat

import org.scalatest.FunSuite
import org.wartremover.test.WartTestTraverser

class DateFormatPartialTest extends FunSuite {
  val format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")

  test("can't use Cookies#get") {
    val result = WartTestTraverser(DateFormatPartial) {
      val foo = format.parse("foo")
    }
    assertResult(List("DateFormat#parse is disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("DateFormatPartial wart obeys SuppressWarnings") {
    val result = WartTestTraverser(DateFormatPartial) {
      @SuppressWarnings(Array("org.danielnixon.extrawarts.DateFormatPartial"))
      val foo = format.parse("foo")
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}