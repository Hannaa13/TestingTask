
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.Suite
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scalatest.{CountLessThenZiroException, DinoImpl, TailConfigurationImpl}


class DinoSpec extends AnyFlatSpec with Matchers with Suite with ScalaFutures {

  private class TestWiring {
    implicit val ec = scala.concurrent.ExecutionContext.global

    val tailConfiguration = new TailConfigurationImpl()
    val testMother = new DinoImpl("mother", None, tailConfiguration)
    val testDino = new DinoImpl("dino", Option(testMother), tailConfiguration)
  }


  "sumThis Simple" should "сумма четырех значений" in new TestWiring {
    assert(testDino.sumThis(1, 2, 3, 4) == 10)
  }

  "sumThis Curring" should "сумма трех значений" in new TestWiring {

    testDino.sumCurring(1)(2)(3) shouldBe 6

  }

  "sumThis with implicit" should "сумма пяти значений" in new TestWiring {
    implicit val five: Int = 5

    testDino.sumThis(1)(2)(3)(4) shouldBe 15


  }

  "show tail" should "возвращать значение tail у динозавра" in new TestWiring {

    whenReady(testDino.showMeYourTail()) { r =>
      r shouldBe tailConfiguration.tail
    }

  }

  "mother name" should "возвращать Option имени мамы динозавра в UpperCase" in new TestWiring {

    whenReady(testDino.motherName()) { res =>
      res shouldBe Some("MOTHER")
      res shouldBe Some(testMother.nameUpper())

    }
  }

  "cope name" should "написать имя динозавра через запятую count-раз" in new TestWiring {
    whenReady(testDino.copeName(2)) { res =>
      res shouldBe "dino,dino"

    }
    //  Await.result(testDino.copeName(2), Duration.Inf) shouldBe "dino,dino"
  }
  "fail " should "ошибка: отрицательное чисо" in new TestWiring {

    assert(testDino.copeName(-2).failed.futureValue.isInstanceOf[CountLessThenZiroException])

  }

}