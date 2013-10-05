package com.company

import org.specs2.mutable._

class LibSpec extends Specification {

  "the test library" should {
    "available" in {
      "test" === TestLib.test
    }
  }

  "the runtime library" should {
    "be available" in {
      "runtime" === RuntimeLib.runtime
    }
  }
} 
