package FPU.conversions

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FCVT_S_W_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FCVT.S.W" in {
    test(new FCVT_S_W) { fcvt =>
      val testcases: Seq[Int] = Seq(8388612, 13, 648258640)

      for (i <- testcases) {
        fcvt.io.intIn.poke(i.S)
        fcvt.clock.step(1)
      }
      //fcvt.clock
    }
  }
}
