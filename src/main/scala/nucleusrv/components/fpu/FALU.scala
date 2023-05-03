package nucleusrv.components.fpu

import chisel3._, chisel3.util._
import hardfloat._


class FALU_IO extends Bundle {
  // Inputs
  val input    : Vec[UInt] = Input(Vec(3, UInt(32.W)))
  val aluCtl   : UInt      = Input(UInt(4.W))
  val roundMode: UInt      = Input(UInt(3.W))

  // Outputs
  val out: UInt = Output(UInt(32.W))
}


class FALU extends Module {
  val io: FALU_IO = IO(new FALU_IO)

  // Constants
  val expWidth: Int = 8
  val sigWidth: Int = 24

  // Modules
  val fadd = Module(new AddRecFN(expWidth, sigWidth))

  // Debug Counter
  val counter: UInt = dontTouch(RegInit(0.U(32.W)))
  counter := counter + 1.U

  // Connections
  Seq(
    (fadd.io.subOp, 0.B),
    (fadd.io.a, recFNFromFN(expWidth, sigWidth, io.input(0))),
    (fadd.io.b, recFNFromFN(expWidth, sigWidth, io.input(1))),
    (fadd.io.roundingMode, io.roundMode),
    (fadd.io.detectTininess, 1.B)
  ).map(
    f =>
      f._1 := f._2
  )

  // Operation Selection
  io.out := MuxLookup(io.aluCtl, 0.U, Seq(
    12.U -> fNFromRecFN(expWidth, sigWidth, fadd.io.out)
  ))



  // DEBUG
}
