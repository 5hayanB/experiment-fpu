package FPU

import chisel3._, chisel3.util._
import firrtl.FirrtlProtos


class FCVT_IO extends Bundle with Parameters {
  val sintIn: SInt = Input(SInt(flen.W))

  val floatOut: SInt = Output(SInt(flen.W))
}


class FCVT extends Module with Parameters {
  val io: FCVT_IO = IO(new FCVT_IO)

  val exponent   : SInt = Wire(SInt(biasLen.W))
  val significand: UInt = Wire(UInt(significandLen.W))
  val sign       : UInt = Wire(UInt(signLen.W))

  val magnitude: UInt = io.sintIn(flen - 2, 0)
  val unbias   : SInt = (30.U - PriorityEncoder(Reverse(magnitude))).asSInt

  sign        := io.sintIn(flen - 1)
  exponent    := unbias + bias.S
  significand := magnitude << (23.U - unbias.asUInt)

  val float: SInt = Cat(sign, exponent, significand).asSInt

  io.floatOut := float
}
