package FPU.FloatALU.conversions

import chisel3._, hardfloat._
import FPU.Parameters


class FCVT_S_W_hf_IO extends Bundle with Parameters {
  val in: UInt = Input(UInt(flen.W))
  val roundingMode: UInt = Input(UInt(3.W))
  val detectTininess: UInt = Input(UInt(1.W))

  val out: UInt = Output(UInt(flen.W))
}


class FCVT_S_W_hf extends RawModule with Parameters {
  val io: FCVT_S_W_hf_IO = IO(new FCVT_S_W_hf_IO)

  val intToRec: INToRecFN = Module(new INToRecFN(flen, exponentWidth, significandWidth))

  Seq(
    (intToRec.io.signedIn, 1.B),
    (intToRec.io.in, io.in),
    (intToRec.io.detectTininess, io.detectTininess),
    (intToRec.io.roundingMode, io.roundingMode),
    (io.out, fNFromRecFN(
      exponentWidth,
      significandWidth,
      intToRec.io.out))
  ).map(x => x._1 := x._2)
}
