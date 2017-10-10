# ExtraWarts

[![Build Status](https://travis-ci.org/danielnixon/extrawarts.svg?branch=master)](https://travis-ci.org/danielnixon/extrawarts)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.danielnixon/extrawarts_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.danielnixon/extrawarts_2.11)

Some extra [WartRemover](https://github.com/wartremover/wartremover) warts that aren't available out of the box.

## Versions

| ExtraWarts version | WartRemover version | Scala version   | sbt version   | Supported |
|--------------------|---------------------|-----------------|---------------|-----------|
| 1.0.1              | 2.2.1               | 2.11.11, 2.12.3 | 0.13.x, 1.0.x |           |
| 0.3.0              | 2.1.1               | 2.11.8, 2.12.2  | 0.13.x        | No        |

## Usage

1. Setup [WartRemover](http://www.wartremover.org/doc/install-setup.html).
2. Add the following to your `plugins.sbt`:

    ```scala
    addSbtPlugin("org.danielnixon" % "sbt-extrawarts" % "1.0.1")
    ```

3. Add the following to your `build.sbt`:
    ```scala
    wartremoverWarnings ++= Seq(
      ExtraWart.EnumerationPartial,
      ExtraWart.FutureObject,
      ExtraWart.GenMapLikePartial,
      ExtraWart.GenTraversableLikeOps,
      ExtraWart.GenTraversableOnceOps,
      ExtraWart.ScalaGlobalExecutionContext,
      ExtraWart.StringOpsPartial,
      ExtraWart.ThrowablePartial,
      ExtraWart.TraversableOnceOps,
      ExtraWart.UnsafeContains)
    ```

## Warts

### EnumerationPartial

`scala.Enumeration#withName` is disabled because is will throw a `NoSuchElementException` if there is no value matching the specified name. You can wrap it in an implicit that might look like this:

```scala
implicit class EnumerationWrapper[A <: Enumeration](val enum: A) extends AnyVal {
  @SuppressWarnings(Array("org.danielnixon.extrawarts.EnumerationPartial"))
  def withNameOpt(s: String): Option[A#Value] = {
    catching[A#Value](classOf[NoSuchElementException]) opt enum.withName(s)
  }
}
```

### FutureObject

`scala.concurrent.Future` has a `reduce` method that can throw `NoSuchElementException` if the collection is empty. Use `Future#fold` instead.

### GenMapLikePartial

`scala.collection.GenMapLike` has an `apply` method that can throw ` NoSuchElementException` if there is no mapping for the given key. Use `GenMapLike#get` instead.

### GenTraversableLikeOps

WartRemover's [TraversableOps](https://github.com/wartremover/wartremover#traversableops) wart only applies to `scala.collection.Traversable`. The `GenTraversableLikeOps` wart extends it to everything that implements `scala.collection.GenTraversableLike`.

`scala.collection.GenTraversableLike` has:

* `head`,
* `tail`,
* `init` and
* `last` methods,

all of which will throw if the list is empty. The program should be refactored to use:

* `GenTraversableLike#headOption`,
* `GenTraversableLike#drop(1)`,
* `GenTraversableLike#dropRight(1)` and
* `GenTraversableLike#lastOption` respectively,

to explicitly handle both populated and empty `GenTraversableLike`s.

### ScalaGlobalExecutionContext

Scala's global execution context `scala.concurrent.ExecutionContext#global` is disabled. Declare a dependency on an `ExecutionContext` instead. See [MUST NOT hardcode the thread-pool / execution context](https://github.com/alexandru/scala-best-practices/blob/master/sections/4-concurrency-parallelism.md#411-must-not-hardcode-the-thread-pool--execution-context).

### StringOpsPartial

`scala.collection.immutable.StringOps` has
* `toBoolean`,
* `toByte`,
* `toShort`,
* `toInt`,
* `toLong`,
* `toFloat` and
* `toDouble` methods,

all of which will throw `NumberFormatException` (or `IllegalArgumentException` in the case of `toBoolean`) if the string cannot be parsed.

You can hide these unsafe `StringOps` methods with an implicit class that might look something like this:

```scala
implicit class StringWrapper(val value: String) extends AnyVal {
  import scala.util.control.Exception.catching

  @SuppressWarnings(Array("org.danielnixon.extrawarts.StringOpsPartial"))
  def toIntOpt: Option[Int] = catching[Int](classOf[NumberFormatException]) opt value.toInt
}
```

### ThrowablePartial

`java.lang.Throwable#getCause` is disabled because it can return null. You can wrap it in an implicit that might look like this:

```scala
implicit class ThrowableWrapper(val t: Throwable) extends AnyVal {
  @SuppressWarnings(Array("org.danielnixon.extrawarts.ThrowablePartial"))
  def cause: Option[Throwable] = Option(t.getCause)
}
```

### TraversableOnceOps

`scala.collection.TraversableOnce` has a `reduceLeft` method that will throw if the collection is empty. Use `TraversableOnce#reduceLeftOption` or `TraversableOnce#foldLeft` instead.

`scala.collection.TraversableOnce` has

* `max`,
* `min`,
* `maxBy` and
* `minBy` methods, 

all of which will throw `UnsupportedOperationException` if the collection is empty. You can wrap these unsafe methods in an implicit class that might look something like this:

```scala
implicit class TraversableOnceWrapper[A](val traversable: TraversableOnce[A]) extends AnyVal {
  @SuppressWarnings(Array("org.danielnixon.extrawarts.TraversableOnceOps"))
  def maxOpt[B >: A](implicit cmp: Ordering[B]): Option[A] = {
    if (traversable.isEmpty) None else Some(traversable.max(cmp))
  }
}
```

### UnsafeContains

`scala.collection.SeqLike#contains` is based on [universal equality](http://www.wartremover.org/doc/warts.html#equals) (`seq.contains(elem)` is equivalent to `seq.exists(_ == elem)`) so it is disabled.

If you use [Scalaz](https://github.com/scalaz/scalaz), you can replace it with something like this:

```scala
  import scalaz._
  import Scalaz._
  
  implicit class SeqLikeWrapper[A](val seq: SeqLike[A, _]) extends AnyVal {
    def containsSafe[A1 >: A](elem: A1)(implicit ev: Equal[A1]): Boolean = {
      seq.exists(x => elem === x)
    }
  }
```

If you use [Cats](https://typelevel.org/cats/), you can replace it with something like this:

```scala
  import cats.Eq
  import cats.implicits._
  
  implicit class SeqLikeWrapper[A](val seq: SeqLike[A, _]) extends AnyVal {
    def containsSafe[A1 >: A](elem: A1)(implicit ev: Eq[A1]): Boolean = {
      seq.exists(x => elem === x)
    }
  }
```

Or simply:

```scala
  implicit class SeqLikeWrapper[A](val seq: SeqLike[A, _]) extends AnyVal {
    def containsSafe(elem: A): Boolean = seq.contains(elem)
  }
```

## See also

* [PlayWarts](https://github.com/danielnixon/playwarts):  WartRemover warts for [Play Framework](https://www.playframework.com/).
* [SlickWarts](https://github.com/danielnixon/slickwarts): WartRemover warts for [Slick](http://slick.typesafe.com/).
* [Scala.js Warts](https://github.com/danielnixon/scalajswarts):  WartRemover warts for [Scala.js](https://www.scala-js.org/).
