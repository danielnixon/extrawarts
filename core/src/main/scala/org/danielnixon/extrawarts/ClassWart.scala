package org.danielnixon.extrawarts

import org.wartremover.{ WartTraverser, WartUniverse }

abstract class ClassWart(targetClassName: String, errorMessage: String) extends WartTraverser {
  def apply(u: WartUniverse): u.Traverser = {
    import u.universe._

    val symbol = rootMirror.staticClass(targetClassName)

    new u.Traverser {
      override def traverse(tree: Tree): Unit = {
        tree match {
          // Ignore trees marked by SuppressWarnings
          case t if hasWartAnnotation(u)(t) =>
          case Select(left, _) if left.tpe.baseType(symbol) != NoType ⇒
            error(u)(tree.pos, errorMessage)
          // TODO: This ignores a lot
          case LabelDef(_, _, _) if isSynthetic(u)(tree) ⇒
          case _ ⇒
            super.traverse(tree)
        }
      }
    }
  }
}
