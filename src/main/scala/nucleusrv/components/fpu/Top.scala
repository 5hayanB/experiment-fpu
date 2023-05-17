package nucleusrv.components.fpu

import chisel3._, chisel3.util._


class Top extends Module {
  val falu: FALU  = Module(new FALU)

  val regSel: UInt = RegInit(0.U(32.W))
  val printReg: Bool = RegInit(0.B)

  val reg0rs1: UInt = RegInit("h7562ab24".U(32.W))
  val reg0rs2: UInt = RegInit("h591fb4ae".U(32.W))

  falu.io.input(0) := MuxLookup(regSel, 0.U(32.W), Seq(
    0.U -> reg0rs1,
    //1.U -> reg1rs1
  ))
  falu.io.input(1) := MuxLookup(regSel, 0.U(32.W), Seq(
    0.U -> reg0rs2,
    //1.U -> reg1rs2
  ))
  regSel := Mux(falu.io.stallValidOut(1), regSel + 1.U, regSel)
  printReg := Mux(regSel === 0.U, 1.B, 0.B)
  falu.io.stallValidIn := 1.B
  falu.io.aluCtl := 15.U
  falu.io.roundMode := 0.U
  falu.io.input(2) := 0.U

  when (falu.io.stallValidOut(1) && printReg) {
    printf("[out] %x %% %x -> %x\n", falu.io.input(0), falu.io.input(1), falu.io.out)
  }
}
