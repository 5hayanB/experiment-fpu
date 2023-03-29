//package FPU
//
//import chisel3._, chisel3.util._
//
//
//class FCVT_S_WU_IO extends Bundle with Parameters {
//  val intIn: UInt = Input(UInt(flen.W))
//
//  val floatOut: SInt = Output(SInt(flen.W))
//}
//
//
//class FCVT_S_WU extends Module with Parameters {
//  val io: FCVT_S_WU_IO = IO(new FCVT_S_WU_IO)
//
//  val exponent   : SInt = Wire(SInt(biasLen.W))
//  val significand: UInt = Wire(UInt(significandLen.W))
//  val sign       : UInt = Wire(UInt(signLen.W))
//
//  val magnitude: UInt = io.intIn(flen - 2, 0)
//
//  // The prioirity encoder is used to find the position of the leading one.
//  // In CHISEL, the priority encoder has the highest priority starting from LSB.
//  // Therefore the magnitude is reversed to find the leading one and then
//  // the resulting position is subtracted from 30 (the no. of bits in magnitude)
//  // to get the un-reversed (aka the original) position of the leading one (aka unbiased exponent)
//  val unbias   : SInt = (30.U - PriorityEncoder(Reverse(magnitude))).asSInt
//
//  sign        := io.intIn(flen - 1)
//  exponent    := unbias + bias.S
//  significand := magnitude << (23.U - unbias.asUInt)
//
//  val float: SInt = Cat(sign, exponent, significand).asSInt
//
//  io.floatOut := float
//}
