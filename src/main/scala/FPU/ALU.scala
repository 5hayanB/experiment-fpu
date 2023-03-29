package FPU

import chisel3._
import chisel3.util._
import FPU.conversions._
import hardfloat._


class ALU_IO extends Bundle with Parameters {
  // Inputs
  val input: Vec[SInt] = Input(Vec(3, SInt(flen.W)))
  val aluCtl: UInt = Input(UInt(4.W))
  //val roundingMode: UInt = Input(UInt(3.W))
  //val detectTininess: UInt = Input(UInt(1.W))

  // Outputs
  val out: UInt = Output(UInt(flen.W))
}


class ALU extends Module with Parameters {
  val io: ALU_IO = IO(new ALU_IO)

  //val fcvt_s_w_hf: FCVT_S_W_hf = Module(new FCVT_S_W_hf)
  val fcvt_s_w: FCVT_S_W = Module(new FCVT_S_W)

  Seq(
    //(fcvt_s_w_hf.io.in, io.input(0).asUInt),
    //(fcvt_s_w_hf.io.roundingMode, io.roundingMode),
    //(fcvt_s_w_hf.io.detectTininess, io.detectTininess),
    (fcvt_s_w.io.intIn, io.input(0))
  ).map(x => x._1 := x._2)

  io.out := MuxCase(0.U, Seq(
      //(io.aluCtl === 1.U) -> (fcvt_s_w_hf.io.out << 1.U),
      (io.aluCtl === 2.U) -> (fcvt_s_w.io.floatOut.asUInt)
  ))
}
