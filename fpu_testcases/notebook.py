from icecream import ic
from fcvt_w_s import bin_to_float


def main() -> None:
    a = 3032069376
    ic(a)
    ic(bin(a)[2:])
    print(f'{a:032b}')
    ic(f'{a:032b}')
    ic(len(bin(a)[2:]))


if __name__ == '__main__':
    main()