import numpy as np, struct
from bitstring import BitArray
from icecream import ic
from utils import bin_to_float, float_to_bin


def main() -> None:
    a: float = bin_to_float('11000110110111111110110010001001')
    b: float = bin_to_float('00111000100110111010000101100010')
    c: float = bin_to_float('01000011011001001010110001011101')

    ic(a), ic(b), ic(c)
    f32: float = np.float32((a * b) + c)
    ic(float_to_bin(f32))

    #c: str = (
    #    float_to_bin(np.PINF) if np.isposinf(f32)
    #    else float_to_bin(np.NINF) if np.isneginf(f32)
    #    else float_to_bin(np.NAN) if np.isnan(f32)
    #    else f32
    #)
    #ic(c)


if __name__ == '__main__':
    main()