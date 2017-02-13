package org.danielnixon.extrawarts

import org.wartremover.{ WartTraverser, WartUniverse }

@deprecated("Use wartremover-contrib's OldTime wart instead.", "0.3.0")
object Calendar extends ClassWart("java.util.Calendar", "java.util.Calendar is disabled - use java.time.* instead")
@deprecated("Use wartremover-contrib's OldTime wart instead.", "0.3.0")
object Date extends ClassWart("java.util.Date", "java.util.Date is disabled - use java.time.* instead")
@deprecated("Use wartremover-contrib's OldTime wart instead.", "0.3.0")
object TimeZone extends ClassWart("java.util.TimeZone", "java.util.TimeZone is disabled - use java.time.* instead")

@deprecated("Use wartremover-contrib's OldTime wart instead.", "0.3.0")
object LegacyDateTimeCode extends WartTraverser {
  def apply(u: WartUniverse): u.Traverser =
    WartTraverser.sumList(u)(List(Calendar, Date, TimeZone))
}

