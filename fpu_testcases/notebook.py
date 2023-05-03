from bitstring import BitArray
from icecream import ic
from fcvt_w_s import bin_to_float


def main() -> None:
    a: BitArray = BitArray(float=float(2795684029), length=32)
    ic(a.bin)


if __name__ == '__main__':
    main()