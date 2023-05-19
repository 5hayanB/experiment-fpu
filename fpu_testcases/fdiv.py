import numpy as np, os, random
from utils import bin_to_float, float_to_bin, float_to_hex


root = os.path.dirname(__file__)
iterations = 500
random.seed(4)
fdiv: list[list[str]] = []


def main() -> None:
    for i in range(iterations):
        rs: list[str] = [''.join([f'{random.randint(0, 1):01b}',
                                  f'{random.randint(1, 254):08b}',
                                  f'{random.randint(0, (2 ** 23) - 1):023b}'])
                         for _ in range(2)]

        f32: float = np.float32(bin_to_float(rs[0]) / bin_to_float(rs[1]))

        fdiv.append(
            [rs[0], rs[1], float_to_bin(np.PINF if np.isposinf(f32)
             else np.NINF if np.isneginf(f32)
             else np.nan if np.isnan(f32)
             else f32)]
        )

    # Verification
    for e in fdiv:
        rs1: float = bin_to_float(e[0])
        rs2: float = bin_to_float(e[1])
        rd: float = bin_to_float(e[2])

        if np.float32(rs1 / rs2) != rd:
            print('Mismatch at:\n'
                  'rs1 = {0}\n'
                  'rs2 = {1}\n'
                  'rs1 / rs2 = {2}'
                  'rd = {3}'.format(
                rs1, rs2, np.float32(rs1 / rs2), rd
            ))
            return

    print('All cases verified')

    fdiv_seq: list[str] = [f'val reg{i}rs1: UInt = RegInit("h{float_to_hex(bin_to_float(fdiv[i][0]))}".U(32.W))\n'
                           f'  val reg{i}rs2: UInt = RegInit("h{float_to_hex(bin_to_float(fdiv[i][1]))}".U(32.W))'
                           for i in range(len(fdiv))]


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
  falu.io.aluCtl := 15.U
  falu.io.roundMode := 0.U
  falu.io.input(2) := 0.U 

  """

    testbench2: str = f'\n{" " * 2}'.join(fdiv_seq)

    testbench3: str = """

  falu.io.input(0) := MuxLookup(regSel, 0.U(32.W), Seq(
    """

    testbench4: str = f',\n{" " * 4}'.join([f'{i}.U -> reg{i}rs1'
                                            for i in range(iterations)])

    testbench5: str = """
  ))

  falu.io.input(1) := MuxLookup(regSel, 0.U(32.W), Seq(
    """

    testbench6: str = f',\n{" " * 4}'.join([f'{i}.U -> reg{i}rs2'
                                            for i in range(iterations)])

    testbench7: str = """
  ))
  
  when (falu.io.stallValidOut(1) && printReg) {
    printf("[out] %x / %x = %x\\n", falu.io.input(0), falu.io.input(1), falu.io.out)
  }
}"""

    # Writing test file
    with open('{0}/Top.scala'.format(root), 'w', encoding='UTF-8') as f:
        f.write(testbench1 + testbench2 + testbench3 + testbench4 + testbench5 + testbench6 + testbench7)

    # Dumping test values
    fdiv_test: str = '\n'.join([
        f'{float_to_hex(bin_to_float(e[0]))} / {float_to_hex(bin_to_float(e[1]))} = {float_to_hex(bin_to_float(e[2]))}'
        for e in fdiv
    ])
    with open(f'{root}/div_sqrt_testcases/test_fdiv.s', 'w', encoding='UTF-8') as f:
        f.write(fdiv_test)


if __name__ == '__main__':
    main()