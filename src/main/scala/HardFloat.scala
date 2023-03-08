package scala

import chisel3._, hardfloat._, FPU.Parameters, chisel3.experimental.DataMirror._


class HardFloatIO extends Bundle with Parameters {
  val in            : Vec[UInt] = Input(Vec(2, UInt(flen.W)))
  val roundingMode  : UInt      = Input(UInt(3.W))
  val detectTininess: UInt      = Input(UInt(1.W))
  val subOp         : Bool      = Input(Bool())

  val out           : UInt = Output(UInt((biasLen + significandLen + 1).W))
}


class HardFloat extends Module with Parameters {
  val io: HardFloatIO = IO(new HardFloatIO)

  val inToRecFN: Map[String, INToRecFN] = (
    for (i <- 0 until io.in.length)
      yield s"in${i}" -> Module(new INToRecFN(flen, biasLen, significandLen))
  ).toMap
  val addRecFN  : AddRecFN  = Module(new AddRecFN(biasLen, significandLen))
  val recFNtoIN : RecFNToIN = Module(new RecFNToIN(biasLen, significandLen, flen))

  inToRecFN.map(
    f => f._2
  ).toSeq.map {
    f =>
      f.io.roundingMode   := 0.U
      f.io.detectTininess := 1.U
  }

  for (i <- 0 until io.in.length) {
    inToRecFN(s"in${i}").io.in       := io.in(i)
    inToRecFN(s"in${i}").io.signedIn := io.in(i)(flen - 1).asBool
  }

  Seq(
    (addRecFN.io.a,              inToRecFN("in0").io.out),
    (addRecFN.io.b,              inToRecFN("in1").io.out),
    (addRecFN.io.subOp,          io.subOp),
    (addRecFN.io.roundingMode,   0.U),
    (addRecFN.io.detectTininess, 1.U),
    (recFNtoIN.io.in,            addRecFN.io.out),
    (recFNtoIN.io.signedOut,     1.B),
    (recFNtoIN.io.roundingMode,  0.U),
    (io.out,                     recFNtoIN.io.out),
  ).map(f => f._1 := f._2)
}
