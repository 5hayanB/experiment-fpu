package FPU

import chisel3._, chisel3.util._


class FCVT_W_S_IO extends Bundle with Parameters {
  val floatIn: UInt = Input(UInt(flen.W))

  val intOut: SInt = Output(SInt(flen.W))
}


class FCVT_W_S extends Module with Parameters {
  val io: FCVT_W_S_IO = IO(new FCVT_W_S_IO)

  val sign       : UInt = io.floatIn(flen - 1)
  val exponent   : SInt = io.floatIn(flen - sign.getWidth - 1, significandLen).asSInt - bias.S
  val significand: UInt = io.floatIn(significandLen - 1, 0)
  val magnitude  : SInt = Mux(
    exponent >= 0.S,
    (Cat("b1".U, significand) >> (significandLen.S - exponent).asUInt).asSInt,
    0.S
  )
  val infinity   : Bool = WireInit(0.B)
  val nan        : Bool = WireInit(0.B)

  //Seq(
  //  (sign,        io.floatIn(flen - 1)),
  //  (exponent,    io.floatIn(flen - sign.getWidth - 1, significandLen).asSInt - bias.S),
  //  (significand, io.floatIn(significandLen - 1, 0)),
  //).map(f => f._1 := f._2)

  io.intOut := Mux(sign.asBool, -magnitude, magnitude)

  Seq(
    (nan,      1.B, 0.B),
    (infinity, 0.B, 1.B)
  ).map(f => f._1 := Mux((exponent === "b1111111".U.asSInt) && significand.orR, f._2, f._3))

  val debug_exponent = dontTouch(WireInit(io.floatIn(flen - sign.getWidth - 1, significandLen).asSInt))
  val debug_unbiased = dontTouch(WireInit(debug_exponent - bias.S))
}
