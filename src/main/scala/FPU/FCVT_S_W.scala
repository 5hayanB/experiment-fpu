package FPU

import chisel3._, chisel3.util._
import firrtl.FirrtlProtos


class FCVT_S_W_IO extends Bundle with Parameters {
  val intIn: SInt = Input(SInt(flen.W))

  val floatOut: SInt = Output(SInt(flen.W))
}


class FCVT_S_W extends Module with Parameters {
  val io: FCVT_S_W_IO = IO(new FCVT_S_W_IO)

  val abs        : SInt = Wire(SInt(flen.W))
  val exponent   : SInt = Wire(SInt(biasLen.W))
  val significand: UInt = Wire(UInt(significandLen.W))
  val sign       : UInt = Wire(UInt(signLen.W))

  when (io.intIn < 0.S) {
    abs := -io.intIn
  } otherwise {
    abs := io.intIn
  }

  val magnitude: UInt = abs(flen - 2, 0)

  // The prioirity encoder is used to find the position of the leading one.
  // In CHISEL, the priority encoder has the highest priority starting from LSB.
  // Therefore the magnitude is reversed to find the leading one and then
  // the resulting position is subtracted from 30 (the no. of bits in magnitude)
  // to get the un-reversed (aka the original) position of the leading one (aka unbiased exponent)
  val unbias   : SInt = (30.U - PriorityEncoder(Reverse(magnitude))).asSInt

  sign        := io.intIn(flen - 1)
  exponent    := unbias + bias.S
  significand := magnitude << (23.U - unbias.asUInt)

  val float: SInt = Cat(sign, exponent, significand).asSInt

  io.floatOut := float
}
