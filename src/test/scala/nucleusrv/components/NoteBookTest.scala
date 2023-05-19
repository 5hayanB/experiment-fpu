package nucleusrv.components

import chisel3._,
       chiseltest._,
       org.scalatest.freespec.AnyFreeSpec


class NoteBookTest extends AnyFreeSpec with ChiselScalatestTester {
  "FDIV" in {
    test(new NoteBook) {
      nb =>
        nb.io.a.poke("h090512bd".U)
        nb.io.b.poke("hc6ca1487".U)

        nb.clock.step(100)
    }
  }
}
