package nucleusrv.components.fpu

import chisel3._,
       chiseltest._,
       org.scalatest.freespec.AnyFreeSpec


class TopTest extends AnyFreeSpec with ChiselScalatestTester {
  "FALU" in {
    test(new Top) {
      falu => falu.clock.step(100)
    }
  }
}
