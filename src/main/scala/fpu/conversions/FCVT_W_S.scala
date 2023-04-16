package fpu.conversions

import chisel3._, chisel3.util._, scala.math.pow
import fpu._


class FCVT_W_S_IO extends Bundle with Parameters {
  val in: UInt = Input(UInt(flen.W))

  val out       : SInt = Output(SInt(flen.W))
  val exceptions: UInt = Output(UInt(2.W))
}


class FCVT_W_S extends Module with Parameters {
  val io: FCVT_W_S_IO = IO(new FCVT_W_S_IO)

  val sign       : UInt = io.in(flen - 1)
  val exponent   : SInt = io.in(flen - 2, sigWidth).asSInt
  val significand: UInt = io.in(sigWidth - 1, 0)

  // Setting exceptions
  val outType: Map[String, Bool] = Map(
    "inf" -> Seq(1.B, 0.B),
    "NaN" -> Seq(0.B, 1.B)
  ).map(
    f => f._1 -> Mux(
      (exponent === "hFF".U.asSInt) && significand.orR,
      f._2(0),
      f._2(1)
    )
  )
  io.exceptions := Mux(
    outType.values.reduce(
      (f, s) => f || s
    ),
    1.U,
    0.U
  )

  // Setting output
  val unbias    : UInt = WireInit(0.U(expWidth.W))
  val magnitude : UInt = WireInit(0.U((flen - 1).W))
  val shiftedMag: UInt = WireInit(0.U(magnitude.getWidth.W))

  unbias     := (exponent - bias.S).asUInt
  magnitude  := Cat("b1".U, significand, 0.U(7.W))
  shiftedMag := magnitude >> (unbias - 1.U)

  io.out := Mux(
    !exponent.asUInt.orR,
    0.S,
    Mux(
      unbias <= 7.U,
      Cat(sign, shiftedMag).asSInt,
      Cat(sign, "h7FFFFFFF".U((flen - 1).W)).asSInt
    )
  )



  // DEBUG Section
  val debug_unbias = dontTouch(WireInit(unbias))
  val debug_magnitude = dontTouch(WireInit(magnitude))
  val debug_shiftedMag = dontTouch(WireInit(shiftedMag))
}
