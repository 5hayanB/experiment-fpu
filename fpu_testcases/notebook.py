import numpy as np, struct
from bitstring import BitArray
from icecream import ic
from utils import bin_to_float, float_to_bin


def main() -> None:
    a: float = bin_to_float('11110101101101010011101000001001')
    b: float = bin_to_float('00001000000011111110110011110001')

    ic(a), ic(b)
    f32: float = np.float32(a / b)

    c: str = (
        float_to_bin(np.PINF) if np.isposinf(f32)
        else float_to_bin(np.NINF) if np.isneginf(f32)
        else float_to_bin(np.NAN) if np.isnan(f32)
        else f32
    )
    ic(c)


if __name__ == '__main__':
    main()