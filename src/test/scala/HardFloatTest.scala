//package experiment_fpu
//
//import chisel3._
//import chiseltest._
//import org.scalatest.freespec.AnyFreeSpec
//
//
//class HardFloatTest extends AnyFreeSpec with ChiselScalatestTester {
//  "HardFloat" in {
//    test(new HardFloat) {
//      hf =>
//        hf.io.in(0).poke(5.U)
//        hf.io.in(1).poke(6.U)
//        hf.io.roundingMode.poke(0.U)
//        hf.io.detectTininess.poke(1.U)
//        hf.io.subOp.poke(1.B)
//
//        hf.clock.step(4)
//
//        hf.io.out.expect(11.U)
//    }
//  }
//}
