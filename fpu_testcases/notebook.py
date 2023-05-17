import numpy as np, random
from icecream import ic
from utils import bin_to_float, float_to_bin, float_to_hex


def main() -> None:
    rs: list[str] = [''.join([f'{random.randint(0, 1):01b}',
                              f'{random.randint(1, 254):08b}',
                              f'{random.randint(0, (2 ** 23) - 1):023b}'])
                     for _ in range(2)]

    f32: float = np.float32(bin_to_float(rs[0])) / np.float32(bin_to_float(rs[1]))

    ic(rs)
    ic(float_to_bin(f32))

    with open('draft.txt', 'w', encoding='UTF-8') as f:
        f.write(f'{float_to_hex(bin_to_float(rs[0]))} % {float_to_hex(bin_to_float(rs[1]))} = {float_to_hex(f32)}')


if __name__ == '__main__':
    main()