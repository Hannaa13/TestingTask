import org.scalatest.Suite
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalatest.{Tail, TailConfigurationImpl}


class TailConfigurationSpec extends AnyFlatSpec with Matchers with Suite{

  "tail" should "возвращать дефолтное значение" in new TestWiring {
    val testdefaultTail = Tail("Value1", "Value2", 777)

    tailConfiguration.tail shouldBe testdefaultTail
  }
"test test" should "true" in  {
  1 shouldBe 1
}
  private class TestWiring {
    val tailConfiguration = new TailConfigurationImpl()
  }

}