package FPU.FloatRegisterFile

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class RegisterFileTest extends AnyFreeSpec with ChiselScalatestTester {
  "Register File" in {
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
