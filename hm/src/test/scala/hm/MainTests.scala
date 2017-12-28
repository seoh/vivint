package hm

import utest._

object MainTests extends TestSuite {
  val tests = Tests{
    'test1 - {
      throw new Exception("test1")
    }
    'test2 - {
      1
    }
    'test3 - {
      Main.add(1, 2) == 3
    }
  }
}
