import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LargeScalaTest extends Specification {

  val props = System.getProperties()

  "mysetting should be 1" in {
        System.getProperty("mysetting") must beEqualTo("1")
  }
}
