package FPU

import chisel3._, chisel3.util._


class RegisterFileIO extends Bundle with Parameters {
  // Inputs
  val rAddr : Vec[UInt] = Input(Vec(5, UInt(regAddrLen.W)))
  val rdData: Valid[SInt] = Flipped(Valid(SInt(flen.W)))

  // Outputs
  val rsData: Vec[SInt] = Output(Vec(3, SInt(flen.W)))
}


class RegisterFile extends Module with Parameters {
  val io: RegisterFileIO = IO(new RegisterFileIO)

  val addrWires: Map[String, UInt] = Map(
    "rdAddr"  -> io.rAddr(0),
    "rs1Addr" -> io.rAddr(1),
    "rs2Addr" -> io.rAddr(2),
    "rs3Addr" -> io.rAddr(3)
  )

  val regFile: Vec[SInt] = Reg(Vec(flen, SInt(flen.W)))

  when (io.rdData.valid) {
    regFile(addrWires("rdAddr")) := io.rdData.bits
  }

  for (i <- 0 until io.rsData.length) {
    io.rsData(i) := regFile(addrWires(s"rs${i + 1}Addr"))
  }
}
