import struct


def bin_to_float(b: str) -> float:
    bf = int(b, 2).to_bytes(4, 'big')
    return struct.unpack('>f', bf)[0]


def float_to_bin(f: float) -> str:
    return ''.join('{:0>8b}'.format(c) for c in struct.pack('!f', f))