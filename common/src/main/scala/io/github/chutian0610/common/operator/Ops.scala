package io.github.chutian0610.common.operator

import scala.util.boundary.{Label, break}
import scala.util.boundary

inline def Q[T](f: Label[T] ?=> T): T = {
  boundary {
    f
  }
}

extension [T](in: T) {

  /**
   * Return the success value or return the quest-block with the error value.
   *
   * Must be used inside a quest block
   */
  inline def ?[F, S](using support: QuestionOperatorSupport.Aux[T, F, S], label: Label[F]): S = {
    support.decode(in) match {
      case Left(bad: F) => break(bad)
      case Right(ok)    => ok
    }
  }
}

inline def bail[T: Label](value: T): Nothing = {
  break(value)
}