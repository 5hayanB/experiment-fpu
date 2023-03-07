package FPU

import chisel3._, chiseltest._, org.scalatest.flatspec.AnyFlatSpec


class FCVT_WU_S_Test extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "FCVT_WU_S"

  it should "pass +ve floats" in {
    test(new FCVT_W_S) { fcvt =>
      fcvt.io.floatIn.poke("h40C80000".U)
      fcvt.clock.step(5)
      fcvt.io.intOut.expect(6.S)
    }
  }
}
