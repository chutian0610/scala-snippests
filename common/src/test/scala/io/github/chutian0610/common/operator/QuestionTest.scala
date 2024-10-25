package io.github.chutian0610.common.operator

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.util.{Failure, Success, Try}
import scala.util.boundary.break

class QuestionTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach  {

  it should "work in a simple case" in {
    val a: Option[Int] = Some(3)
    val b: Option[Int] = None

    Q {
      Some(a.? + b.?)
    } shouldBe None
  }

  it should "support either" in {
    val a: Either[String, Int]     = Right(23)
    val b: Either[String, Boolean] = Left("Bad")

     Q {
      val x = a.?
      val y = b.?
    } shouldBe Left("Bad")
  }

  it should "support either (success case with diverging error codes)" in {
    val a: Either[String, Int]     = Right(23)
    val b: Either[String, Boolean] = Right(true)

    val res = Q {
      val x = a.?
      val y = b.?
      val z = if (y) x else -1
      Right(z)
    }
    res shouldBe Right(23)
  }

  it should "support try" in {
    val a: Try[Int] = Success(10)
    val b: Try[Int] = Success(32)

    val rt          = new RuntimeException("BOOM")
    val c: Try[Int] = Failure(rt)

    val res =  Q {
      Success(a.? + b.?)
    } shouldBe Success(42)

    val res2 =  Q {
      Success(a.? + c.?)
    } shouldBe Failure(rt)
  }

  it should "support bailing" in {
    val x: Option[Int] =  Q {
      bail(None)
      Some(5)
    }
    x shouldBe None

    val y: Option[Int] = Q {
      bail(Some(3))
      None
    }
    y shouldBe Some(3)
  }

  // Testing custom types

  sealed trait Base
  case class Err(msg: String) extends Base
  case class Ok(value: Int)   extends Base

  given support: QuestionOperatorSupport.Aux[Base, Err, Int] = new QuestionOperatorSupport[Base] {
    override type Failure = Err
    override type Success = Int

    override def decode[X <: Base](value: X): Either[Err, Int] = {
      value match {
        case e: Err => Left(e)
        case ok: Ok => Right(ok.value)
      }
    }
  }

  it should "support custom types" in {
    val x =  Q {
      Ok(123).?
      Ok(235).?
      Ok(400)
    }
    x shouldBe Ok(400)

    val y =  Q {
      Ok(123).?
      Err("boom").?
      Ok(400).?
    }
    y shouldBe Err("boom")
  }

  it should "handle correct return type" in {
    val x =  Q {
      bail(Err("Boom!"))
      Ok(42)
    }

    x shouldBe Err("Boom!")
  }
}
