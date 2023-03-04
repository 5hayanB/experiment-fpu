package FPU

import chisel3._, chiseltest._, org.scalatest.flatspec.AnyFlatSpec


class FCVT_Test extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "FCVT"
  it should "FCVT Module" in {
    test(new FCVT) { fcvt =>
      fcvt.io.sintIn.poke(13.S)
      fcvt.clock.step(3)
    }
  }
}
