import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.Suite
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalatest._

import scala.concurrent.Future


class DinoSpec extends AnyFlatSpec with Matchers with Suite with ScalaFutures with MockFactory {

  "sumThis" should "считать сумму четырех значений" in new TestWiring {
    testDino.sumThis(1, 2, 3, 4) shouldBe 10
  }

  it should "считать сумму пяти значений с пятым имплиситом" in new TestWiring {
    implicit val five: Int = 5
    testDino.sumThis(1)(2)(3)(4) shouldBe 15
  }

  "sumCurring" should "считать сумму трех значений" in new TestWiring {
    testDino.sumCurring(1)(2)(3) shouldBe 6

  }

  "showMeYourTail" should "возвращать значение tail у динозавра" in new TestWiring {
    (mockTailConfiguration.tail _)
      .expects()
      .returns(testTail)
    whenReady(testDino.showMeYourTail()) { res =>
      res shouldBe testTail
    }
  }

  "motherName" should "возвращать Option имени мамы динозавра в UpperCase" in new TestWiring {
    whenReady(testDino.motherName()) { res =>
      res shouldBe Some("MOTHER")
    }
  }

  "copeName" should "написать имя динозавра через запятую count-раз" in new TestWiring {
    whenReady(testDino.copeName(2)) { res =>
      res shouldBe "dino,dino"
    }
  }

  it should "выдавать ошибку: отрицательное число" in new TestWiring {
    whenReady(testDino.copeName(-2).failed) { res =>
      res.isInstanceOf[CountLessThenZiroException]
    }
  }

  private class TestWiring {
    implicit val ec = scala.concurrent.ExecutionContext.global
    val mockTailConfiguration = mock[TailConfigurationImpl]
    val testTail = Tail("Value1", "Value2", 777)
    val mockTestMother = mock[DinoImpl]
    val testDino = new DinoImpl("dino", Option(mockTestMother), mockTailConfiguration)
  }

}