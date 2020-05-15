import org.mockito.scalatest.MockitoSugar
import org.scalatest.Suite
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalatest.{DinoImpl, Tail, TailConfiguration}
import utils.FakeDino
import scala.concurrent.ExecutionContext

class Dino2Spec extends AnyFlatSpec with Matchers with Suite with ScalaFutures with MockitoSugar {

  "showMeYourTail" should "возвращать значение tail у динозавра" in new TestWiring {
    when(mockTailConfiguration.tail).thenReturn(testTail)

    whenReady(testDino.showMeYourTail()) { res =>
      res shouldBe testTail
    }
    verify(mockTailConfiguration).tail
  }

  "motherName" should "возвращать Option имени мамы динозавра в UpperCase" in new TestWiring {
    when(mockTestMother.nameUpper()).thenReturn("MOTHER")

  whenReady(testDino.motherName()) { res =>
      res shouldBe Some("MOTHER")
    }
    verify(mockTestMother).nameUpper()
  }

  private class TestWiring {
    private implicit val ec = ExecutionContext.global

    val mockTailConfiguration = mock[TailConfiguration]
    val mockTestMother = mock[FakeDino]

    val testDino = new DinoImpl("dino", Some(mockTestMother), mockTailConfiguration)
    val testTail = Tail("Value1", "Value2", 777)
  }

}