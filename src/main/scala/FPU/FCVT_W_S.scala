package FPU

import chisel3._, chisel3.util._


class FCVT_W_S_IO extends Bundle with Parameters {
  val floatInt: UInt = Input(UInt(flen.W))

  val intOut: SInt = Output(SInt(flen.W))
}


class FCVT_W_S extends Module with Parameters {
  val io: FCVT_W_S_IO = IO(new FCVT_W_S_IO)

  val sign: UInt = io.floatInt(flen - 1)
}
