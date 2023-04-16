import random, os, bitstring


cwd = os.path.dirname(__file__)
iterations = 500
random.seed(7)
int_to_f: list[str] = []

# Writing testcases
for i in range(iterations):
    if (i % 2) == 0:
        f32 = float(random.randint(0, (2 ** 32) - 1))
    else:
        f32 = float(random.randint(0, (2 ** 23) - 1))

    int32bits = bitstring.BitArray(float=f32, length=32)

    int_to_f.append('{0}L -> "b{1}"'.format(int(f32), int32bits.bin))


testbench1: str = """package FPU.conversions

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FCVT_S_WU_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FCVT_S_WU" in {
    test(new FCVT_S_WU) {
      fcvt =>
        val testcases: Seq[(Long, String)] = Seq(
          """

testbench2: str = f',\n{" " * 10}'.join(int_to_f)

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
with open('{0}/FCVT_S_WU_Test.scala'.format(cwd), 'w', encoding='UTF-8') as f:
    f.write(testbench1 + testbench2 + testbench3)