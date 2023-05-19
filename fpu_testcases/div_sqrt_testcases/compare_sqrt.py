from icecream import ic


def main() -> None:
    # Reading testcases
    with open('test_fsqrt.s', 'r', encoding='UTF-8') as f:
        fsqrt_test: list[str] = f.readlines()
    fsqrt_test = [e[:-1]
                  for e in fsqrt_test]

    # Reading sbt dump
    with open(f'fsqrt.s', 'r', encoding='UTF-8') as f:
        fsqrt_dump: list[str] = f.readlines()
    fsqrt_dump = [e[6:-1]
                  for e in fsqrt_dump if e[:6] == '[out] ']

    fsqrt: list[list[str]] = [[fsqrt_dump[i], fsqrt_test[i]]
                              for i in range(len(fsqrt_dump))]

    # Running test and comparing
    for i in range(len(fsqrt)):
        if fsqrt[i][0] != fsqrt[i][1]:
            print('Comparison failed')
            ic(i)
            ic(fsqrt[i][0])
            ic(fsqrt[i][1])
            return
    print('Comparison successful')


if __name__ == '__main__':
    main()