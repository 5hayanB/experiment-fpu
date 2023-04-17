//package fpu.FloatALU
//
//import chisel3._, chisel3.util._
//import hardfloat._
//
//
//class FALU_IO extends Bundle {
//  // Inputs
//  val input         : Vec[UInt] = Input(Vec(3, UInt(32.W)))
//  val aluCtl        : UInt      = Input(UInt(4.W))
//  val roundingMode  : UInt      = Input(UInt(3.W))
//
//  // Outputs
//  val out: UInt = Output(UInt(32.W))
//}
//
//
//class FALU extends Module {
//  val io: FALU_IO = IO(new FALU_IO)
//
//  val signExtendedWires: SInt = WireInit(0.S(32.W))
//
//  // Constants
//  val expWidth: Int = 8
//  val sigWidth: Int = 24
//
//  // Modules
//  val fadd: AddRecFN = Module(new AddRecFN(expWidth, sigWidth))
//
//  // Debug Counter
//  val counter: UInt = dontTouch(RegInit(0.U(32.W)))
//  counter := counter + 1.U
//
//  // Connections
//  Seq(
//    (fadd.io.a, io.input(0)),
//    (fadd.io.b, io.input(2)),
//  ).map(x => x._1 := recFNFromFN(expWidth, sigWidth, x._2))
//
//  Seq(
//    (fadd.io.subOp, Mux(io.aluCtl === 2.U, 1.B, 0.B)),
//    (fadd.io.roundingMode, io.roundingMode),
//    (fadd.io.detectTininess, 1.U)
//  ).map(x => x._1 := x._2)
//
//  // Operation Selection
//  io.out := MuxLookup(io.aluCtl, 0.U, Seq(
//    1.U -> fadd.io.out
//  ).map(
//    f => f._1 -> fNFromRecFN(expWidth, sigWidth, f._2)
//  ))
//
//  //io.out := signExtendedWires.asUInt
//
//
//
//  // DEBUG
//  val debug_fadd_fN_out_S: SInt = dontTouch(WireInit(0.S(32.W)))
//  val debug_fadd_fN_out_U: UInt = dontTouch(WireInit(fNFromRecFN(expWidth, sigWidth, fadd.io.out)))
//  debug_fadd_fN_out_S := fNFromRecFN(expWidth, sigWidth, fadd.io.out).asSInt
//}
