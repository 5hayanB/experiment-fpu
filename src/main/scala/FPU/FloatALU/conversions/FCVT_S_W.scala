package FPU.FloatALU.conversions

import chisel3._, chisel3.util._
import FPU._


class FCVT_S_W_IO extends Bundle with Parameters {
  val intIn: SInt = Input(SInt(flen.W))

  val floatOut: SInt = Output(SInt(flen.W))
}


class FCVT_S_W extends Module with Parameters with RoundingModes {
  val io: FCVT_S_W_IO = IO(new FCVT_S_W_IO)

  //val initUIntWires: Map[String, UInt] = Map(
  //  "" -> Wire(UInt(1.W))
  //)
  val abs         : SInt = Wire(SInt(flen.W))
  val exponent    : SInt = Wire(SInt(exponentWidth.W))
  val significand : UInt = Wire(UInt(significandWidth.W))
  val rSignificand: UInt = Wire(UInt(significandWidth.W))
  val sign        : UInt = Wire(UInt(signWidth.W))
  val unbias      : UInt = Wire(UInt(exponentWidth.W))

  abs := Mux(io.intIn < 0.S, -io.intIn, io.intIn)

  val magnitude : UInt = abs(flen - 2, 0)
  val shiftedMag: UInt = dontTouch(Wire(UInt(magnitude.getWidth.W)))
  //val magnitude = abs.asUInt

  // The prioirity encoder is used to find the position of the leading one.
  // In CHISEL, the priority encoder has the highest priority starting from LSB.
  // Therefore the magnitude is reversed to find the leading one and then
  // the resulting position is subtracted from 30 (the no. of bits in magnitude)
  // to get the un-reversed (aka the original) position of the leading one (aka unbiased exponent)
  val rev = Reverse(magnitude)
  val priorityEn = PriorityEncoder(rev)
  //val shiftOp = 30.U - unbias
  unbias := (magnitude.getWidth - 1).U - priorityEn

  val magnLSB: Int = flen - significandWidth - 1
  shiftedMag := magnitude << (priorityEn + 1.U)
  significand := shiftedMag(flen - 2, magnLSB)
  val grs: UInt = dontTouch(Map(
    "G" -> shiftedMag(magnLSB - 1).asUInt,
    "R" -> shiftedMag(magnLSB - 2).asUInt,
    "S" -> shiftedMag(magnLSB - 3).asUInt
  ).values.reduce((x, y) => Cat(x, y)))

  rSignificand := MuxCase(RNE(significand, grs), Seq(
  ))

  sign        := io.intIn(flen - 1)
  exponent    := unbias.asSInt + bias.S

  val float: SInt = Cat(sign, exponent, rSignificand).asSInt

  io.floatOut := float


  // Debug Section
  val debug_magnLSB = dontTouch(WireInit(magnLSB.U))
  val debug_G = dontTouch(grs(2))
  val debug_R = dontTouch(grs(1))
  val debug_S = dontTouch(grs(0))
  //val debug_shiftedMag = dontTouch(shiftedMag)
}
