package FPU

import chisel3._, chiseltest._, org.scalatest.flatspec.AnyFlatSpec


class FCVT_S_W_Test extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "FCVT"
  it should "FCVT.S.W Module" in {
    test(new FCVT_S_W) { fcvt =>
      fcvt.io.intIn.poke(13.S)
      fcvt.clock.step(3)
    }
  }
}
