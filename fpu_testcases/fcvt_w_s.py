import random, os, numpy as np
from utils import bin_to_float


cwd = os.path.dirname(__file__)
iterations: int = 500
random.seed(7)
f_to_int: list[str] = []




def main() -> None:
    for i in range(iterations):
        while True:
            f32: str = ''.join([f'{random.randint(0, 1):01b}',
                                f'{random.randint(0, (2 ** 8) - 2):08b}',
                                f'{random.randint(0, (2 ** 23) - 1):023b}'])

            if (i % 9) == 0:
                if bin_to_float(f32) >= ((2 ** 31) - 1):
                    f_to_int.append('"b{0}" -> {1},  // {2}'.format(
                        f32,
                        (2 ** 31) - 1,
                        bin_to_float(f32)
                    ))
                    break
                elif bin_to_float(f32) <= -(2 ** 31):
                    f_to_int.append('"b{0}" -> {1},  // {2}'.format(
                        f32,
                        -(2 ** 31),
                        bin_to_float(f32)
                    ))
                    break
                else:
                    continue
            elif (i % 9) == 2:
                if int(bin_to_float(f32)) == 0:
                    f_to_int.append('"b{0}" -> {1},  // {2}'.format(
                        f32,
                        np.int32(bin_to_float(f32)),
                        bin_to_float(f32)
                    ))
                    break
                else:
                    continue
            else:
                if (-(2 ** 31) < bin_to_float(f32) < ((2 ** 31) - 1)) and (int(bin_to_float(f32)) != 0):
                    f_to_int.append('"b{0}" -> {1},  // {2}'.format(
                        f32,
                        np.int32(bin_to_float(f32)),
                        bin_to_float(f32)
                    ))
                    break
                else:
                    continue

    testbench1: str = """package fpu.conversions

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FCVT_W_S_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FCVT_W_S" in {
    test(new FCVT_W_S) {
      fcvt =>
        val testcases: Seq[(String, Int)] = Seq(
          """

    testbench2: str = f'\n{" " * 10}'.join(f_to_int)

    testbench3: str = """
        )

        for (i <- testcases) {
          fcvt.io.in.poke(i._1.U)

          fcvt.clock.step(1)

          fcvt.io.out.expect(i._2.S)
        }
    }
  }
}"""


    # Writing test file
    with open('{0}/FCVT_W_S_Test.scala'.format(cwd), 'w', encoding='UTF-8') as f:
        f.write(testbench1 + testbench2 + testbench3)


if __name__ == '__main__':
    main()