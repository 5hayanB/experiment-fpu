import numpy as np
from icecream import ic
from utils import bin_to_float, float_to_bin


def main() -> None:
    a: float = bin_to_float('11001110110111111001000101011110')
    b: float = bin_to_float('10010010001011111010011100110010')

    ic(a), ic(b), ic(a < b)


if __name__ == '__main__':
    main()