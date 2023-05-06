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
      14.U -> 0.U,
      15.U -> 3.U
    ),

    fcmp.io.signaling -> Seq(
      16.U -> 1.B
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
  Seq(
    io.out -> (Seq(
      fadd.io.out,
      fdiv.io.out,
      fmadd.io.out
    ).map(
      f => fNFromRecFN(expWidth, sigWidth, f)
    ) ++ Seq(
      fcmp.io.lt,
      (fcmp.io.lt || fcmp.io.eq)
    )),
    io.exceptions -> Seq(
      fadd.io.exceptionFlags,
      fdiv.io.exceptionFlags,
      fmadd.io.exceptionFlags,
      fcmp.io.exceptionFlags,
      fcmp.io.exceptionFlags
    )
  ).map(
    f => f._1 := MuxCase(0.U, Seq(
      Seq(12.U)       -> f._2(0),
      Seq(13.U)       -> f._2(1),
      Seq(14.U, 15.U) -> f._2(2),
      Seq(16.U)       -> f._2(3),
      Seq(17.U)       -> f._2(4)
    ).map(
      x => x._1.map(
        y => io.aluCtl === y
      ).reduce(
        (a, b) => a || b
      ) -> x._2
    ))
  )



  // DEBUG
}
