package FPU

import chisel3._, chiseltest._, org.scalatest.flatspec.AnyFlatSpec


class FCVT_S_WU_Test extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "FCVT_S_WU"
  it should "FCVT.S.WU Module" in {
    test(new FCVT_S_WU) { fcvt =>
      fcvt.io.intIn.poke(100.U)
      fcvt.clock.step(3)
    }
  }
}
