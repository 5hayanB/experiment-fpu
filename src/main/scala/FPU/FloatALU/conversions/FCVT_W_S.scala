//package FPU.FloatALU.conversions
//
//import chisel3._, chisel3.util._
//import FPU._
//
//
//class FCVT_W_S_IO extends Bundle with Parameters {
//  val floatIn: UInt = Input(UInt(flen.W))
//
//  val intOut: SInt = Output(SInt(flen.W))
//}
//
//
//class FCVT_W_S extends Module with Parameters {
//  val io: FCVT_W_S_IO = IO(new FCVT_W_S_IO)
//
//  val unbias: SInt = WireInit(0.S(exponentWidth.W))
//
//  val sign       : UInt = io.floatIn(flen - 1)
//  val exponent   : SInt = io.floatIn(flen - 2, significandWidth).asSInt
//  val significand: UInt = io.floatIn(significandWidth - 1, 0)
//
//  unbias := exponent - bias.S
//  val magnitude  : SInt = Mux(
//    exponent >= 0.S,
//    (Cat("b1".U, significand) >> ().asUInt).asSInt,
//    0.S
//  )
//  val infinity   : Bool = WireInit(0.B)
//  val nan        : Bool = WireInit(0.B)
//
//  io.intOut := Mux(sign.asBool, -magnitude, magnitude)
//
//  Seq(
//    (nan,      1.B, 0.B),
//    (infinity, 0.B, 1.B)
//  ).map(f => f._1 := Mux((exponent === "b1111111".U.asSInt) && significand.orR, f._2, f._3))
//}
