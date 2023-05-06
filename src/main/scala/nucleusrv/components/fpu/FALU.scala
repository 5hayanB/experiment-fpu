package nucleusrv.components.fpu

import chisel3._, chisel3.util._
import hardfloat._


class FALU_IO extends Bundle {
  // Inputs
  val input    : Vec[UInt] = Input(Vec(3, UInt(32.W)))
  val aluCtl   : UInt      = Input(UInt(4.W))
  val roundMode: UInt      = Input(UInt(3.W))

  // Outputs
  val out       : UInt = Output(UInt(32.W))
  val exceptions: UInt = Output(UInt(5.W))
}


class FALU extends Module {
  val io: FALU_IO = IO(new FALU_IO)

  // Constants
  val expWidth: Int = 8
  val sigWidth: Int = 24

  // Modules
  val fadd = Module(new AddRecFN(expWidth, sigWidth))
  val fdiv = Module(new DivSqrtRecFN_small(expWidth, sigWidth, 0))
  val fcmp = Module(new CompareRecFN(expWidth, sigWidth))

  // Debug Counter
  val counter: UInt = dontTouch(RegInit(0.U(32.W)))
  counter := counter + 1.U

  // Connections
  // - Inputs
  (Seq(
    fadd.io.a,
    fdiv.io.a,
    fcmp.io.a
  ).map (
    f => f -> 0
  ) ++ Seq(
    fadd.io.b,
    fdiv.io.b,
    fcmp.io.b
  ).map(
    f => f -> 1
  )).map(
    f => f._1 := recFNFromFN(expWidth, sigWidth, io.input(f._2))
  )

  (Seq(  // - Module Specific
    fadd.io.subOp -> 0.B,

    fdiv.io.inValid -> fdiv.io.inReady,
    fdiv.io.sqrtOp -> 0.U,

    fcmp.io.signaling -> 0.B
  ) ++ Seq(  // - roundingMode
    fadd.io.roundingMode,
    fdiv.io.roundingMode
  ).map(
    f => f -> io.roundMode
  ) ++ Seq(  // - detectTininess
    fadd.io.detectTininess,
    fdiv.io.detectTininess
  ).map(
    f => f -> consts.tininess_afterRounding
  )).map(
    f => f._1 := f._2
  )

  // Operation Selection
  Seq(
    io.out -> Seq(
      fadd.io.out,
      fdiv.io.out
    ).map(
      f => fNFromRecFN(expWidth, sigWidth, f)
    ),
    io.exceptions -> Seq(
      fadd.io.exceptionFlags,
      fdiv.io.exceptionFlags,
      fcmp.io.exceptionFlags
    )
  ).map(
    f => f._1 := MuxLookup(io.aluCtl, 0.U, Seq(
      12.U -> f._2(0),
      13.U -> f._2(1)
    ))
  )



  // DEBUG
}
