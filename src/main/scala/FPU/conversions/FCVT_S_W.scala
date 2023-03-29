package FPU.conversions

import chisel3._, chisel3.util._
import FPU._


class FCVT_S_W_IO extends Bundle with Parameters {
  val intIn: SInt = Input(SInt(flen.W))

  val floatOut: SInt = Output(SInt(flen.W))
}


class FCVT_S_W extends Module with Parameters {
  val io: FCVT_S_W_IO = IO(new FCVT_S_W_IO)

  val abs        : SInt = Wire(SInt(flen.W))
  val exponent   : SInt = Wire(SInt(exponentWidth.W))
  val significand: UInt = Wire(UInt(significandWidth.W))
  val sign       : UInt = Wire(UInt(signWidth.W))
  val unbias     : UInt = Wire(UInt(exponentWidth.W))

  abs := Mux(io.intIn < 0.S, -io.intIn, io.intIn)

  val magnitude: UInt = abs(flen - 2, 0)
  //val magnitude = abs.asUInt

  // The prioirity encoder is used to find the position of the leading one.
  // In CHISEL, the priority encoder has the highest priority starting from LSB.
  // Therefore the magnitude is reversed to find the leading one and then
  // the resulting position is subtracted from 30 (the no. of bits in magnitude)
  // to get the un-reversed (aka the original) position of the leading one (aka unbiased exponent)
  unbias := 30.U - PriorityEncoder(Reverse(magnitude))
  val rev = dontTouch(Reverse(magnitude))
  val priorityEn = dontTouch(PriorityEncoder(rev))
  val shiftOp = dontTouch(30.U - unbias)

  sign        := io.intIn(flen - 1)
  exponent    := unbias.asSInt + bias.S
  significand := (magnitude << shiftOp)(flen - 2, flen - 2 - significandWidth)

  val float: SInt = Cat(sign, exponent, significand).asSInt

  io.floatOut := float
}
