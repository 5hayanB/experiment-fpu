import random, os, struct, numpy as np
from icecream import ic


cwd = os.path.dirname(__file__)
iterations: int = 500
random.seed(7)
f_to_int: list[str] = []


def bin_to_float(b: str) -> float:
    bf = int(b, 2).to_bytes(4, 'big')
    return struct.unpack('>f', bf)[0]


def neg_bin(f32: str, i: int) -> None:
    if f32[0] == '-':
        ic(i)
    return


def main() -> None:
    for i in range(iterations):
        while True:
            f32: str = ('0'
                        f'{random.randint(0, (2 ** 8) - 2):08b}'
                        f'{random.randint(0, (2 ** 23) - 1):023b}')

            if (i % 9) == 0:
                if bin_to_float(f32) >= ((2 ** 32) - 1):
                    f_to_int.append('"b{0}" -> "b{1:032b}",  // {2}'.format(
                        f32,
                        (2 ** 32) - 1,
                        bin_to_float(f32)
                    ))
                    break
                elif bin_to_float(f32) <= 0:
                    f_to_int.append('"b{0}" -> "b{1:032b}",  // {2}'.format(
                        f32,
                        0,
                        bin_to_float(f32)
                    ))
                    break
                else:
                    continue
            elif (i % 9) == 2:
                if int(bin_to_float(f32)) == 0:
                    f_to_int.append('"b{0}" -> "b{1:032b}",  // {2}'.format(
                        f32,
                        np.uint32(bin_to_float(f32)),
                        bin_to_float(f32)
                    ))
                    break
                else:
                    continue
            else:
                if (0 < bin_to_float(f32) < ((2 ** 32) - 1)) and (int(bin_to_float(f32)) != 0):
                    f_to_int.append('"b{0}" -> "b{1:032b}",  // {2}'.format(
                        f32,
                        np.uint32(bin_to_float(f32)),
                        bin_to_float(f32)
                    ))
                    break
                else:
                    continue


    testbench1: str = """package fpu.conversions

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FCVT_WU_S_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FCVT_WU_S" in {
    test(new FCVT_WU_S) {
      fcvt =>
        val testcases: Seq[(String, String)] = Seq(
          """

    testbench2: str = f'\n{" " * 10}'.join(f_to_int)

    testbench3: str = """
        )

        for (i <- testcases) {
          fcvt.io.in.poke(i._1.U)

          fcvt.clock.step(1)

          fcvt.io.out.expect(i._2.U)
        }
    }
  }
}"""


    # Writing test file
    with open('{0}/FCVT_WU_S_Test.scala'.format(cwd), 'w', encoding='UTF-8') as f:
        f.write(testbench1 + testbench2 + testbench3)


if __name__ == '__main__':
    main()