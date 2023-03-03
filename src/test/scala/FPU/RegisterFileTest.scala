package FPU

import chisel3._, chiseltest._, org.scalatest.flatspec.AnyFlatSpec


class RegisterFileTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "RegisterFile"
  it should "run successfully" in {
    test(new RegisterFile) { regFile =>
      for (i <- 0 until 3) {
        regFile.io.rAddr(i).poke(i.U)
      }
      regFile.io.rdData.bits.poke(0.S)
      regFile.io.rdData.valid.poke(0.B)
      regFile.clock.step(10)
    }
  }
}
