package nucleusrv.components.fpu

import chisel3._, chisel3.util._
import hardfloat._


class FALU_IO extends Bundle {
  // Inputs
  val input    : Vec[UInt] = Input(Vec(3, UInt(32.W)))
  val aluCtl   : UInt      = Input(UInt(5.W))
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
  val fadd  = Module(new AddRecFN(expWidth, sigWidth))
  val fdiv  = Module(new DivSqrtRecFN_small(expWidth, sigWidth, 0))
  val fmadd = Module(new MulAddRecFN(expWidth, sigWidth))
  val fcmp  = Module(new CompareRecFN(expWidth, sigWidth))

  // Debug Counter
  val counter: UInt = dontTouch(RegInit(0.U(32.W)))
  counter := counter + 1.U

  // Connections
  // - Inputs
  (Seq(
    fadd.io.a,
    fdiv.io.a,
    fmadd.io.a,
    fcmp.io.a
  ).map (
    f => f -> 0
  ) ++ Seq(
    fadd.io.b,
    fdiv.io.b,
    fmadd.io.b,
    fcmp.io.b
  ).map(
    f => f -> 1
  ) ++ Seq(
    fmadd.io.c -> 2
  )).map(
    f => f._1 := recFNFromFN(expWidth, sigWidth, io.input(f._2))
  )

  (Seq(  // - Module Specific
    fadd.io.subOp -> 0.B,

    fdiv.io.inValid -> fdiv.io.inReady,
    fdiv.io.sqrtOp -> 0.U
  ) ++ Seq(
    fmadd.io.op -> Seq(
      16.U -> 0.U,
      17.U -> 3.U
    ),

    fcmp.io.signaling -> Seq(
      18, 19, 20
    ).map(
      f => f.U -> 1.B
    )
  ).map(
    f => f._1 -> MuxLookup(io.aluCtl, 0.U, f._2)
  ) ++ Seq(  // - roundingMode
    fadd.io.roundingMode,
    fdiv.io.roundingMode,
    fmadd.io.roundingMode
  ).map(
    f => f -> io.roundMode
  ) ++ Seq(  // - detectTininess
    fadd.io.detectTininess,
    fdiv.io.detectTininess,
    fmadd.io.detectTininess
  ).map(
    f => f -> consts.tininess_afterRounding
  )).map(
    f => f._1 := f._2
  )

  // Operation Selection
  io.out := MuxCase(0.U, (Seq(
    Seq(14) -> fadd.io.out,
    Seq(15) -> fdiv.io.out,
    Seq(16, 17) -> fmadd.io.out
  ).map(
    f => f._1 -> fNFromRecFN(expWidth, sigWidth, f._2)
  ) ++ Seq(
    Seq(18) -> fcmp.io.lt,
    Seq(19) -> (fcmp.io.lt || fcmp.io.eq),
    Seq(20) -> Mux(fcmp.io.lt, io.input(0), io.input(1))
  )).map(
    x => x._1.map(
      y => io.aluCtl === y.U
    ).reduce(
      (y, z) => y || z
    ) -> x._2
  ))

  io.exceptions := MuxCase(0.U, Seq(
    Seq(14) -> fadd.io.exceptionFlags,
    Seq(15) -> fdiv.io.exceptionFlags,
    Seq(16, 17) -> fmadd.io.out,
    Seq(18, 19, 20) -> fcmp.io.exceptionFlags
  ).map(
    x => x._1.map(
      y => io.aluCtl === y.U
    ).reduce(
      (y, z) => y || z
    ) -> x._2
  ))



  // DEBUG
}
