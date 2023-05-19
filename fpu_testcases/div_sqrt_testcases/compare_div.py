from icecream import ic


def main() -> None:
    # Reading testcases
    with open('test_fdiv.s', 'r', encoding='UTF-8') as f:
        fdiv_test: list[str] = f.readlines()
    fdiv_test = [e[:-1]
                 for e in fdiv_test]

    # Reading sbt dump
    with open(f'fdiv.s', 'r', encoding='UTF-8') as f:
        fdiv_dump: list[str] = f.readlines()
    fdiv_dump = [e[6:-1]
                 for e in fdiv_dump if e[:6] == '[out] ']

    fdiv: list[list[str]] = [[fdiv_dump[i], fdiv_test[i]]
                             for i in range(len(fdiv_dump))]

    # Running test and comparing
    for i in range(len(fdiv)):
        if fdiv[i][0] != fdiv[i][1]:
            print('Comparison failed')
            ic(i)
            ic(fdiv[i][0])
            ic(fdiv[i][1])
            return
    print('Comparison successful')


if __name__ == '__main__':
    main()