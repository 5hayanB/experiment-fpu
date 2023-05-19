package nucleusrv.components

import chisel3._,
       chisel3.util._
import nucleusrv.components.fpu._
import hardfloat._


class NoteBookIO extends Bundle {
  val a: UInt = Input(UInt(32.W))

  val out: UInt = Output(UInt(32.W))
  val exceptions: UInt = Output(UInt(5.W))
}


class NoteBook extends Module {
  val io: NoteBookIO = IO(new NoteBookIO)

  val expWidth: Int = 8
  val sigWidth: Int = 24

  val fdiv: DivSqrtRecFN_small = Module(new DivSqrtRecFN_small(expWidth, sigWidth, 0))

  fdiv.io.a := recFNFromFN(expWidth, sigWidth, io.a)
  fdiv.io.b := 0.U
  fdiv.io.sqrtOp := 1.U
  fdiv.io.inValid := 1.B
  fdiv.io.roundingMode := consts.round_near_even
  fdiv.io.detectTininess := consts.tininess_afterRounding

  io.out := Mux(fdiv.io.outValid_sqrt, fNFromRecFN(expWidth, sigWidth, fdiv.io.out), 0.U)
  io.exceptions := fdiv.io.exceptionFlags
}
