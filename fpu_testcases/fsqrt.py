import numpy as np, os, random, math
from utils import bin_to_float, float_to_bin, float_to_hex, hex_to_float
from icecream import ic

root = os.path.dirname(__file__)
iterations = 500
random.seed(6)
fsqrt: list[list[str]] = []


def main() -> None:
    for i in range(iterations):
        rs: str = ''.join([f'{random.randint(0, 1):01b}',
                           f'{random.randint(1, 254):08b}',
                           f'{random.randint(0, (2 ** 23) - 1):023b}'])

        if rs[0] == '0':
            f32: float = np.float32(math.sqrt(bin_to_float(rs)))
        else:
            f32 = np.float32(hex_to_float("7FC00000"))

        fsqrt.append(
            [rs, float_to_bin(np.PINF if np.isposinf(f32)
             else np.NINF if np.isneginf(f32)
             else np.nan if np.isnan(f32)
             else f32)]
        )

    fsqrt_seq: list[str] = [f'val reg{i}rs1: UInt = RegInit("h{float_to_hex(bin_to_float(fsqrt[i][0]))}".U(32.W))\n'
                            for i in range(len(fsqrt))]

    testbench1: str = """package nucleusrv.components.fpu

import chisel3._,
       chisel3.util._


class Top extends Module {
  val falu: FALU  = Module(new FALU)

  val regSel: UInt = RegInit(0.U(32.W))
  val printReg: Bool = RegInit(0.B)
  val stallValid: Bool = RegInit(falu.io.stallValidOut(0))

  regSel := Mux(falu.io.stallValidOut(1), regSel + 1.U, regSel)
  printReg := Mux((0.U <= regSel) && (regSel < 500.U), 1.B, 0.B)
  stallValid := falu.io.stallValidOut(0)
  falu.io.stallValidIn := Mux(stallValid, 1.B, 0.B)
  falu.io.aluCtl := 24.U
  falu.io.roundMode := 0.U
  falu.io.input(2) := 0.U 

  """

    testbench2: str = f'\n{" " * 2}'.join(fsqrt_seq)

    testbench3: str = """

  falu.io.input(0) := MuxLookup(regSel, 0.U(32.W), Seq(
    """

    testbench4: str = f',\n{" " * 4}'.join([f'{i}.U -> reg{i}rs1'
                                            for i in range(iterations)])

    testbench5: str = """
  ))

  falu.io.input(1) := 0.U"""

    testbench6: str = """

  when (falu.io.stallValidOut(1) && printReg) {
    printf("[out] sqrt(%x) = %x\\n", falu.io.input(0), falu.io.out)
  }
}"""

    # Writing test file
    with open('{0}/Top.scala'.format(root), 'w', encoding='UTF-8') as f:
        f.write(testbench1 + testbench2 + testbench3 + testbench4 + testbench5 + testbench6)

    # Dumping test values
    fsqrt_test: str = '\n'.join([
        f'sqrt({float_to_hex(bin_to_float(e[0]))}) = {float_to_hex(bin_to_float(e[1]))}'
        for e in fsqrt
    ])
    with open(f'{root}/div_sqrt_testcases/test_fsqrt.s', 'w', encoding='UTF-8') as f:
        f.write(fsqrt_test)


if __name__ == '__main__':
    main()