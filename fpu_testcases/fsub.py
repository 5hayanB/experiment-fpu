import random, os, numpy as np
from utils import bin_to_float, float_to_bin


cwd = os.path.dirname(__file__)
iterations = 500
random.seed(7)
fsub: list[list[str]] = []


def main() -> None:
    for i in range(iterations):
        rs: list[str] = [''.join([f'{random.randint(0, 1):01b}',
                                  f'{random.randint(1, 254):08b}',
                                  f'{random.randint(0, (2 ** 23) - 1):023b}'])
                         for _ in range(2)]

        f32: float = np.float32(bin_to_float(rs[0]) - bin_to_float(rs[1]))

        fsub.append([rs[0], rs[1], float_to_bin(f32)])

    for e in fsub:
        rs1: float = bin_to_float(e[0])
        rs2: float = bin_to_float(e[1])
        rd : float = bin_to_float(e[2])

        if np.float32(rs1 - rs2) != rd:
            print('Mismatch at:\n'
                  'rs1 = {0}\n'
                  'rs2 = {1}\n'
                  'rs1 - rs2 = {2}\n'
                  'rd        = {3}'.format(
                rs1, rs2, np.float32(rs1 - rs2), rd
            ))
            return

    print('All cases verified')

    fsub_seq: list[str] = [f'("b{e[0]}", "b{e[1]}") -> "b{e[2]}"'
                           for e in fsub]


    testbench1: str = """package nucleusrv.components.fpu

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FALU_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FALU" in {
    test(new FALU) {
      falu =>
        val testcases: Seq[((String, String), String)] = Seq(
          """

    testbench2: str = f',\n{" " * 10}'.join(fsub_seq)

    testbench3: str = """
        )

        for (i <- testcases) {
          falu.io.input(0).poke(i._1._1.U)
          falu.io.input(1).poke(i._1._2.U)
          falu.io.input(2).poke(0.U)
          falu.io.aluCtl.poke(21.U)
          falu.io.roundMode.poke(0.U)

          falu.clock.step(1)

          falu.io.out.expect(i._2.U)
        }
    }
  }
}"""

    # Writing test file
    with open('{0}/FALU_Test.scala'.format(cwd), 'w', encoding='UTF-8') as f:
        f.write(testbench1 + testbench2 + testbench3)


if __name__ == '__main__':
    main()