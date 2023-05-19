package nucleusrv.components

import chisel3._,
       chiseltest._,
       org.scalatest.freespec.AnyFreeSpec


class NoteBookTest extends AnyFreeSpec with ChiselScalatestTester {
  "FSQRT" in {
    test(new NoteBook) {
      nb =>
        nb.io.a.poke("h48c3f930".U)

        nb.clock.step(100)
    }
  }
}
