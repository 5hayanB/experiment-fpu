import struct


def bin_to_float(b: str) -> int:
    bf = int(b, 2).to_bytes(4, 'big')
    return struct.unpack('>f', bf)[0]


a = '00111111110000000000000000000000'
print(bin_to_float(a))