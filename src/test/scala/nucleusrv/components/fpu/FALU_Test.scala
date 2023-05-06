package nucleusrv.components.fpu

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FALU_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FALU" in {
    test(new FALU) {
      falu =>
        val testcases: Seq[(String, String)] = Seq(
          "b01101011011010000010110101001101" -> "b00001000101001010010111001110000"
        )

        for (i <- testcases) {
          falu.io.input(0).poke(i._1.U)
          falu.io.input(1).poke(i._2.U)
          falu.io.input(2).poke(0.U)
          falu.io.aluCtl.poke(13.U)
          falu.io.roundMode.poke(0.U)

          falu.clock.step(60)

          //falu.io.out.expect(i._2.U)
        }
    }
  }
}
