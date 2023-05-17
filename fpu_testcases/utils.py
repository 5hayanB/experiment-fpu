import struct


def bin_to_float(b: str) -> float:
    bf = int(b, 2).to_bytes(4, 'big')
    return struct.unpack('>f', bf)[0]


def float_to_bin(f: float) -> str:
    return ''.join('{:0>8b}'.format(c) for c in struct.pack('!f', f))


def hex_to_float(h: str) -> float:
    bf = int(h, 16).to_bytes(4, 'big')
    return struct.unpack('>f', bf)[0]


def float_to_hex(f: float) -> str:
    return ''.join('{:0>2x}'.format(c) for c in struct.pack('!f', f))


if __name__ == '__main__':
    print(float_to_hex(hex_to_float('464c670a')))