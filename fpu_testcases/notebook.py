import struct, numpy as np, bitstring


def bin_to_float(binary: str) -> int:
    bf = int(binary, 2).to_bytes(4, 'big')
    return struct.unpack('>f', bf)[0]


a = '010011110' + ('0' * 22) + '1'
print(bin_to_float(a))

exponent = 127 + 24
print(exponent)

c = (2 ** 31) - 1
print(np.int32(c))
print(hex(np.int32(c)))

d = bitstring.BitArray(float=c, length=32)
print(d.bin)

e = bitstring.BitArray(float=-(2 ** 31), length=32)
print(e.bin)

f = 'abcde'
print(f[0])