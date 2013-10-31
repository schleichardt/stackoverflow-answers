import org.specs2.mutable.Specification

class LargeScalaTest extends Specification {

  val props = System.getProperties()

  "mysetting should be 1" in {
        System.getProperty("mysetting") must beEqualTo("1")
  }
}
