package FPU.conversions

import chisel3._, chiseltest._, org.scalatest.freespec.AnyFreeSpec


class FCVT_W_S_Test_Test extends AnyFreeSpec with ChiselScalatestTester {
  "FCVT_W_S" in {
    test(new FCVT_W_S) {
      fcvt =>
        val testcases: Seq[(String, Int)] = Seq(
          "b11111001101001101001111000001101" -> -2147483648,
          "b11010011100011000101110001111111" -> -2147483648,
          "b01101001100110000001100011101000" -> 2147483647,
          "b11001011000011101101100100000100" -> -9361668,
          "b00000101000101100000000010100011" -> 0,
          "b10110110000100011110001000001011" -> 0,
          "b00001100011011001010110101001010" -> 0,
          "b01101010000111111011000101111100" -> 2147483647,
          "b01010001000011111101011000110000" -> 2147483647,
          "b10000110101110001001100011010001" -> 0,
          "b01000111101000100001011110111110" -> 82991,
          "b10110110001001001110110111100110" -> 0,
          "b01001001110011101111100010101010" -> 1695509,
          "b00001101101100000001100001010000" -> 0,
          "b10001100100100000001001011110000" -> 0,
          "b01001111101101001011100110110101" -> 2147483647,
          "b11010111111011010111011010110000" -> -2147483648,
          "b10111100011101000000001111100100" -> 0,
          "b10100110101111111001100011100010" -> 0,
          "b01011001101111100111110100011011" -> 2147483647,
          "b01001010010011001101110100100000" -> 3356488,
          "b11110000110101111110111000000101" -> -2147483648,
          "b10100101000100101011110101001010" -> 0,
          "b01000010011010110000101000011000" -> 58,
          "b01100001010101111001000011111000" -> 2147483647,
          "b01110111111111010010110010101111" -> 2147483647,
          "b10000101100100111101111011101111" -> 0,
          "b10101100010110011010010101001010" -> 0,
          "b11001010111101001100100111011111" -> -8021231,
          "b01101100000101111111010111101000" -> 2147483647,
          "b10111101000100001010001111010110" -> 0,
          "b01011110010011110100001001101101" -> 2147483647,
          "b10100100111000101100001100111010" -> 0,
          "b10000011011101100011000110101001" -> 0,
          "b10010110000111011111100111111101" -> 0,
          "b10001000001101111101110001110110" -> 0,
          "b10010001001111110110001110101111" -> 0,
          "b10110010111111110001101100010000" -> 0,
          "b00010101111100101111110111110010" -> 0,
          "b11000110110001110010000001110111" -> -25488,
          "b01101001011011100011011010101010" -> 2147483647,
          "b11011010111010100101000011011111" -> -2147483648,
          "b11010111111000010110010010011001" -> -2147483648,
          "b00010011100101010011111001111100" -> 0,
          "b00010011101110110110000110000110" -> 0,
          "b00000010011111000010011010000100" -> 0,
          "b00100010010010000010110010011100" -> 0,
          "b00010011011010110100000000010011" -> 0,
          "b11001110110100011001000010001000" -> -1757955072,
          "b01011000100011011101001001111010" -> 2147483647,
          "b11110011111001000111001011110001" -> -2147483648,
          "b10110011111001001110010100001100" -> 0,
          "b00111110011001101000001101101000" -> 0,
          "b00011000100100010011110110110001" -> 0,
          "b00111000101010011000110010110011" -> 0,
          "b00101100000011010111010110011000" -> 0,
          "b00000000101001101011100101001100" -> 0,
          "b01111001110111010001010110001010" -> 2147483647,
          "b00001001101101010011110001100011" -> 0,
          "b10010011110000001001001111110110" -> 0,
          "b11001101110111010011100111010000" -> -463944192,
          "b10010000000111011000011111001110" -> 0,
          "b11111101111101110100101100010101" -> -2147483648,
          "b10111110010011111101010110001101" -> 0,
          "b00010010100110100010100011110111" -> 0,
          "b11011111010000111100011100011011" -> -2147483648,
          "b11101010101010010101010000001010" -> -2147483648,
          "b00011010110111001001101111001111" -> 0,
          "b01011000100001101110110001000001" -> 2147483647,
          "b11111101100101110100110001110111" -> -2147483648,
          "b11000010110111011110000000001001" -> -110,
          "b00101110001110010000100011110010" -> 0,
          "b11010001101110010001100101000010" -> -2147483648,
          "b01100111101111010100100010000010" -> 2147483647,
          "b11011111001110100000101110011001" -> -2147483648,
          "b01000010111111100010011011110011" -> 127,
          "b11011110000001110110101100111110" -> -2147483648,
          "b01100101110001111000011111111001" -> 2147483647,
          "b10100001101100011001001010110111" -> 0,
          "b10111001110110010111101000011110" -> 0,
          "b10001010101110000111000000111000" -> 0,
          "b00011101111110000101011100101001" -> 0,
          "b00101011101101000101000111010000" -> 0,
          "b11010000000000000111110100010000" -> -2147483648,
          "b11110100110110000001000011010110" -> -2147483648,
          "b01101011000111101011001000000001" -> 2147483647,
          "b11100100101100110000011010011000" -> -2147483648,
          "b11110010001011011011001110011001" -> -2147483648,
          "b11100101110101010001111111011000" -> -2147483648,
          "b01100111011001010101010110101011" -> 2147483647,
          "b10110011100101011011110101000100" -> 0,
          "b00010110001000001000010110010110" -> 0,
          "b00010011111101110010000101101110" -> 0,
          "b01001110111110010110111101110100" -> 2092415488,
          "b10010100001000011000100000101000" -> 0,
          "b00000010000110100100111101000100" -> 0,
          "b00111000001100011101111011000100" -> 0,
          "b00000100010000000111100000111111" -> 0,
          "b00100101101111011001001111111101" -> 0,
          "b10100001111010110100010001101000" -> 0,
          "b00001000010110101001000110010110" -> 0,
          "b11010101011010111010111001001011" -> -2147483648,
          "b01000100101001101101111010111111" -> 1334,
          "b01110000011100001010110000000110" -> 2147483647,
          "b01001110000000010000000110111000" -> 541093376,
          "b00010110101001000011110100110101" -> 0,
          "b11001111100111101100111001100001" -> -2147483648,
          "b00101010011110111000010001000100" -> 0,
          "b01110001100011101000101111101100" -> 2147483647,
          "b00011000110001101110010000001001" -> 0,
          "b01100011000110010000010111010101" -> 2147483647,
          "b11001000000001110010001000110101" -> -138376,
          "b00111001010100110101101101101010" -> 0,
          "b01011001010001101111010110100001" -> 2147483647,
          "b11000001111110100110000010010110" -> -31,
          "b01011001110000100111010010100011" -> 2147483647,
          "b01101100011100101001000100110101" -> 2147483647,
          "b00110101100111110010001010011101" -> 0,
          "b10111001010100001110010000001101" -> 0,
          "b01010110001111011001101010000000" -> 2147483647,
          "b10001001101101100111001011010110" -> 0,
          "b11100100100111110101001001010010" -> -2147483648,
          "b01111000110111011011111000110000" -> 2147483647,
          "b00100000101000110010001100011110" -> 0,
          "b10011100100110000001100010011010" -> 0,
          "b11110001111111001011110100011111" -> -2147483648,
          "b01111111001110010100010100110011" -> 2147483647,
          "b01011010111011100111100000110110" -> 2147483647,
          "b10101011111010111101100011000110" -> 0,
          "b00101110010100011000101011100100" -> 0,
          "b01011100110111011010111100010000" -> 2147483647,
          "b00101011111101010110101101110010" -> 0,
          "b11011010100001001010000100000101" -> -2147483648,
          "b10101010110010111010001011100001" -> 0,
          "b00001110101110101000001010000001" -> 0,
          "b00001011010000111111110000000101" -> 0,
          "b10000101101011100111101000100110" -> 0,
          "b11100001001000010010101010001101" -> -2147483648,
          "b11101101010000100011010000110011" -> -2147483648,
          "b10010011111111101001111011100101" -> 0,
          "b10001011110001110111000010100000" -> 0,
          "b01100110101011101110111110100010" -> 2147483647,
          "b11110011000100101000100110111010" -> -2147483648,
          "b11111000100001000100111100010101" -> -2147483648,
          "b01100111010000101011001110000111" -> 2147483647,
          "b01001110001110001110111110111010" -> 775679616,
          "b00100010000111110010011001000010" -> 0,
          "b10000001110101101101001010100110" -> 0,
          "b11110111010001001001001001110100" -> -2147483648,
          "b00000110001111010000101000100111" -> 0,
          "b01111100101010010101010010111010" -> 2147483647,
          "b10000110101011100101111110010101" -> 0,
          "b01110111110011111101111010111011" -> 2147483647,
          "b11000100001101001011001111111111" -> -722,
          "b10111001101011011000101011011000" -> 0,
          "b10101100100001001010011001010110" -> 0,
          "b10000101000000111110110110111001" -> 0,
          "b01011110001100001000000000111000" -> 2147483647,
          "b10011111111100100111001000111011" -> 0,
          "b01010100111011101010001100110000" -> 2147483647,
          "b11000110011001001010000101001001" -> -14632,
          "b11011000101101110001011000011100" -> -2147483648,
          "b00101100001100101101100100001101" -> 0,
          "b00110100010110001111100100101101" -> 0,
          "b01101011101000010011101111001010" -> 2147483647,
          "b00001001110000010110111010011001" -> 0,
          "b10010101000011100010111011000100" -> 0,
          "b01010101111000011000000101110111" -> 2147483647,
          "b11001101001111100000000110101010" -> -199236256,
          "b10000110011101011001111010110101" -> 0,
          "b00010100110001001101111110010110" -> 0,
          "b10000000110000110110001111100101" -> 0,
          "b11111011110101000011010010000001" -> -2147483648,
          "b10011111100010001101000110000000" -> 0,
          "b10011100010110110100100100010101" -> 0,
          "b00000000110101011101100001011110" -> 0,
          "b10001011011110011000001000111110" -> 0,
          "b11000000101100110111001101101101" -> -5,
          "b01000001000000010100010001110000" -> 8,
          "b00100010000101101111101000010100" -> 0,
          "b00110011100010101010101010101111" -> 0,
          "b10000011010011001011010110011010" -> 0,
          "b11010001001110111001100101101000" -> -2147483648,
          "b01001011001001111011111010011010" -> 10993306,
          "b11100010010100110111110110010001" -> -2147483648,
          "b10010011110010001011111111001011" -> 0,
          "b00000110011011011110001011111011" -> 0,
          "b01110100100001000001110111001101" -> 2147483647,
          "b00001011000001111111101000100010" -> 0,
          "b00010001110111000101011101010011" -> 0,
          "b00110000111100111000111000001011" -> 0,
          "b01010000100001001101001010111110" -> 2147483647,
          "b00111111010000111000011111101110" -> 0,
          "b00111010100100011111001011010100" -> 0,
          "b01010100100100001110100010101101" -> 2147483647,
          "b10100000100100110000111100100111" -> 0,
          "b10011110101101001000100100100010" -> 0,
          "b01011111011101011101100011011000" -> 2147483647,
          "b11101100111000011110111101111011" -> -2147483648,
          "b00111101110010011000110110111111" -> 0,
          "b01001111001100101100001100100100" -> 2147483647,
          "b01001101001001011011110110100110" -> 173791840,
          "b10100001010011011110111001001000" -> 0,
          "b00000010011110110111111111101100" -> 0,
          "b00111110110001001100111001001010" -> 0,
          "b01011001001101111011101011000010" -> 2147483647,
          "b10100101110010010001100101100001" -> 0,
          "b10111100011101110110001000000000" -> 0,
          "b01110010101100110000001000001100" -> 2147483647,
          "b11111101100101011111101010001011" -> -2147483648,
          "b10000010110010100010001001111111" -> 0,
          "b10001010011100110000111100110111" -> 0,
          "b10110010001101011011011111100100" -> 0,
          "b00001010000101110001111000011010" -> 0,
          "b01100000010000110000010111101001" -> 2147483647,
          "b10010001010001111001000111000010" -> 0,
          "b01011010110111010111110011111110" -> 2147483647,
          "b01000000011111000111001110110110" -> 3,
          "b10000011101010001011100010000000" -> 0,
          "b01111010011111011101111111001011" -> 2147483647,
          "b10110100010011010100110010101001" -> 0,
          "b00110101110110000000110111000101" -> 0,
          "b10101000100111101111001111101010" -> 0,
          "b10000000110100110001010110001100" -> 0,
          "b11101011111001011111010001010110" -> -2147483648,
          "b01111000101100100001110000010111" -> 2147483647,
          "b01110011110010100011001001111110" -> 2147483647,
          "b10110000000100001010001001011011" -> 0,
          "b10110010000100111000111011111110" -> 0,
          "b11110110111011011001010011011101" -> -2147483648,
          "b11101101100011000101101101001100" -> -2147483648,
          "b10001101100011010011011011001110" -> 0,
          "b11010001101001100001111101000000" -> -2147483648,
          "b01111100110001000000011011000000" -> 2147483647,
          "b11000001110100001100101101000000" -> -26,
          "b01100011010111111001001111010001" -> 2147483647,
          "b11110001100001110110110101001001" -> -2147483648,
          "b11110101001101000001010001011110" -> -2147483648,
          "b00000110111010010010111111010011" -> 0,
          "b11001111001000110111100101111101" -> -2147483648,
          "b10111110100011001000100111000000" -> 0,
          "b00010110011110001110000100001110" -> 0,
          "b10101100010010000010000010000010" -> 0,
          "b10100001010000101001101001110000" -> 0,
          "b11010100001111010001100100100110" -> -2147483648,
          "b10111110011001001111010101001001" -> 0,
          "b00010101101010010110001001011001" -> 0,
          "b00011011011111110100000001011011" -> 0,
          "b00111010010101010011010010100000" -> 0,
          "b10110111001000111011110010010001" -> 0,
          "b00011111100101110011100100010000" -> 0,
          "b00101100000101110101000111110101" -> 0,
          "b10011111010111100100100101000010" -> 0,
          "b11101000001100111011111110010001" -> -2147483648,
          "b01100000011010011010110000001111" -> 2147483647,
          "b10110101001101011100001011100010" -> 0,
          "b10100011010101101001010001111010" -> 0,
          "b01000000010001110000101101001111" -> 3,
          "b10010000101101110100100101011100" -> 0,
          "b00100011001111111001101010101000" -> 0,
          "b10110011111100100010001111000110" -> 0,
          "b11111010110011111110000001001000" -> -2147483648,
          "b00010000100010000100000100011100" -> 0,
          "b11011011011110010010100000011100" -> -2147483648,
          "b10000000100100101011100100101010" -> 0,
          "b11110111111101111101100011000101" -> -2147483648,
          "b10100000000110111110101001110000" -> 0,
          "b00010100001001101110110111110001" -> 0,
          "b01111001011101010001001101000001" -> 2147483647,
          "b01000111000010100001111110110100" -> 35359,
          "b01100100101000000010101010110110" -> 2147483647,
          "b01001001000010011001111110011100" -> 563705,
          "b11111011101000001100001001101111" -> -2147483648,
          "b11000100011011111111101101110010" -> -959,
          "b00001101000100100000001010010101" -> 0,
          "b11000011101100010001001101011101" -> -354,
          "b10100001101110010011110010111100" -> 0,
          "b00000001110011010011000001111111" -> 0,
          "b10100100010100001111110011000110" -> 0,
          "b00111101001111000001100111000011" -> 0,
          "b00000100011010010110110001100011" -> 0,
          "b10000111100001011001001111011011" -> 0,
          "b01000000011010111000011000101001" -> 3,
          "b00100001001110100101001111000001" -> 0,
          "b11110110110111101100011010011011" -> -2147483648,
          "b00111111100010001011101010011011" -> 1,
          "b11011100011010111010100110011101" -> -2147483648,
          "b11010111111001010111011110111011" -> -2147483648,
          "b00000001010010101100011111001100" -> 0,
          "b00011010111111101110010111101000" -> 0,
          "b00101000001100011010010110011100" -> 0,
          "b00111100001110001011000001111001" -> 0,
          "b11100001110010111000000010111000" -> -2147483648,
          "b01111010011111101110101001101111" -> 2147483647,
          "b01110011001110010010101111000101" -> 2147483647,
          "b10110101100011100111000101011001" -> 0,
          "b01110110111001001011100111001011" -> 2147483647,
          "b00011011100001100000110010001000" -> 0,
          "b00110101100011010100010101101011" -> 0,
          "b00011000011001001011000010111011" -> 0,
          "b11110011010100000110111101101000" -> -2147483648,
          "b00001010101010100110011011111001" -> 0,
          "b10011000101011110111110110111010" -> 0,
          "b10000100110011111101001111100111" -> 0,
          "b11101011110111111011011011010110" -> -2147483648,
          "b10111001001010110101010010101111" -> 0,
          "b00000000100101000000011110101011" -> 0,
          "b10001010110110011111100110111011" -> 0,
          "b11111010100111111010101101011000" -> -2147483648,
          "b00110001010110110100110000001101" -> 0,
          "b11101001111011101011010011111111" -> -2147483648,
          "b00000110111110010011010011110000" -> 0,
          "b00110000011100100100001111010100" -> 0,
          "b00101001110111010011111101101001" -> 0,
          "b10000100011010010010101001001111" -> 0,
          "b01101000011001111001111100101101" -> 2147483647,
          "b00110000100010001110110000110111" -> 0,
          "b10001000100011111101111101111100" -> 0,
          "b10011001000100000001011100001101" -> 0,
          "b10101110110001011011011001101001" -> 0,
          "b11111011000010110010100001101100" -> -2147483648,
          "b11100000010100010000010100010010" -> -2147483648,
          "b10100110100000001111011100101101" -> 0,
          "b00000011101110111101111010101000" -> 0,
          "b00111101011101110011101011111110" -> 0,
          "b11100101110000000100010010011010" -> -2147483648,
          "b11101000111111100101010001001101" -> -2147483648,
          "b01110111011111110001110101001001" -> 2147483647,
          "b00000001110011011010011000001001" -> 0,
          "b01001110001111000111001111010101" -> 790426944,
          "b11101110110100011100110111110010" -> -2147483648,
          "b10101110100101000011101001010001" -> 0,
          "b00110010101010001111000110101000" -> 0,
          "b00110100100100001001001001010111" -> 0,
          "b00111110010100110110010011100110" -> 0,
          "b01111101111011010011001010101001" -> 2147483647,
          "b01111110100100100111100101101000" -> 2147483647,
          "b11010000000101011000011001101111" -> -2147483648,
          "b00001100111010111100101010011011" -> 0,
          "b11111111011100100110110000101100" -> -2147483648,
          "b00011110001000100000011111000110" -> 0,
          "b10111011001111000010010010010110" -> 0,
          "b01100100010010110011111010010000" -> 2147483647,
          "b10100100010001001000010111000000" -> 0,
          "b10100001010000101010010101010001" -> 0,
          "b00111000101111110101011110000011" -> 0,
          "b00011111101111000100100111111101" -> 0,
          "b00100100101100000011000100101001" -> 0,
          "b10001000111001010110010011010001" -> 0,
          "b10011111101110110011101111001000" -> 0,
          "b01010100011101101100001100101101" -> 2147483647,
          "b00001101100000010010011001100100" -> 0,
          "b11110001101110110010101001000010" -> -2147483648,
          "b11110101110111111011011001011011" -> -2147483648,
          "b01110000110010110010111001110010" -> 2147483647,
          "b00001111100011001110011001101111" -> 0,
          "b01001101001100011011010010010011" -> 186337584,
          "b00110000001011011000000110011101" -> 0,
          "b11001101110000101000101111110111" -> -407994080,
          "b00001110010110011000010111101010" -> 0,
          "b00000101010111100110001110101111" -> 0,
          "b10010010100010110100111001111111" -> 0,
          "b00100001000010011100100111010101" -> 0,
          "b01101000100000101110100111001001" -> 2147483647,
          "b10110100110111110010111011100100" -> 0,
          "b01001111110011111110110000001111" -> 2147483647,
          "b00011010100010000000111000110001" -> 0,
          "b11000110111110111100011100011101" -> -32227,
          "b00110100100110011111010010001100" -> 0,
          "b11010101001001111001000010111011" -> -2147483648,
          "b01010100001010011110011110001011" -> 2147483647,
          "b11011001110001010110101100110001" -> -2147483648,
          "b11111110110010001000011001101101" -> -2147483648,
          "b10110101100011010010010111111001" -> 0,
          "b11011111110110110111000001000010" -> -2147483648,
          "b10110101100001001010100110011110" -> 0,
          "b11010010101100100111101111001101" -> -2147483648,
          "b11011101111001111010110001010110" -> -2147483648,
          "b01111001000000011000000100100000" -> 2147483647,
          "b11110011101010000001010011000100" -> -2147483648,
          "b10001111000101110010101000111001" -> 0,
          "b11001010010111010101111011000001" -> -3626928,
          "b11100011001010011001110010000101" -> -2147483648,
          "b00000010000011010011101111101000" -> 0,
          "b01010010111001011000111101100010" -> 2147483647,
          "b01001001110111101110111110011011" -> 1826291,
          "b00010011010110010001001011101011" -> 0,
          "b10010101001010111111101000011111" -> 0,
          "b00001110011000100011110001110000" -> 0,
          "b11100000101100101000010011111100" -> -2147483648,
          "b10010000100010110010001010100100" -> 0,
          "b10101000100011011010100111110100" -> 0,
          "b10001011101010010000011111011011" -> 0,
          "b01001111111001111000110001001100" -> 2147483647,
          "b01101010111110010001001110010111" -> 2147483647,
          "b01001000101101111101011111010001" -> 376510,
          "b00110011101010000000111100000000" -> 0,
          "b10101110000111111000000010100100" -> 0,
          "b00100000001100010100110111110011" -> 0,
          "b01110001100010011100001011001101" -> 2147483647,
          "b10001111111000111100110001010011" -> 0,
          "b11000110110011100110010000001100" -> -26418,
          "b10100111101111111100111101101101" -> 0,
          "b10110010010111100001000100110100" -> 0,
          "b11000000111100000011011111100000" -> -7,
          "b00000011000000001110010111101000" -> 0,
          "b10111100001111000011100101100111" -> 0,
          "b11100010011101010101001001101110" -> -2147483648,
          "b01101000011110010010010011011110" -> 2147483647,
          "b10001110000100010010111011010001" -> 0,
          "b00101110011011100011101110111100" -> 0,
          "b10001100011100010010010011000010" -> 0,
          "b00000101101000010101100101110000" -> 0,
          "b01110110110100000101000001010110" -> 2147483647,
          "b00000111011000001011101110011010" -> 0,
          "b00000011100100001111111001010010" -> 0,
          "b00011001001000011011000110101110" -> 0,
          "b10100101001010100100010010111111" -> 0,
          "b00001000110110011101010001101001" -> 0,
          "b10010100110100101110011100011100" -> 0,
          "b11110100011101001101011011010001" -> -2147483648,
          "b00100001011110101110100001010100" -> 0,
          "b01001100010000110100101101001011" -> 51195180,
          "b00101001010111110100110011100011" -> 0,
          "b00011001101011101001110111011110" -> 0,
          "b10010101010001110011011111111110" -> 0,
          "b11110011011000000111100010100100" -> -2147483648,
          "b01100101110000111010101111010111" -> 2147483647,
          "b01100010100011000110111100101111" -> 2147483647,
          "b11111100011100111111101001010110" -> -2147483648,
          "b00100000111001001110110111111100" -> 0,
          "b10100010011000000011000001111011" -> 0,
          "b11001010001001010110110100010000" -> -2710340,
          "b10101010100101001101010110101110" -> 0,
          "b10011101101011010011111111100010" -> 0,
          "b00100110010000001110111101011110" -> 0,
          "b11010010010100000000100111000000" -> -2147483648,
          "b01100000000010001010011010101011" -> 2147483647,
          "b00010011110010100111110100011101" -> 0,
          "b10110101110111010011010110010111" -> 0,
          "b00010001011111010000011101101100" -> 0,
          "b01001110100010111010101101011111" -> 1171632000,
          "b00000111000000001010101101101000" -> 0,
          "b10100111000110110011101010010101" -> 0,
          "b11000100101110010110100100001001" -> -1483,
          "b11001011010011010001100001111110" -> -13441150,
          "b00011010110111011100000110001011" -> 0,
          "b10010100101000100111111011100100" -> 0,
          "b01111000001111100101101111001100" -> 2147483647,
          "b00111010000110001000011010100111" -> 0,
          "b01010010001001010000101010000010" -> 2147483647,
          "b10110011110000111010010100111000" -> 0,
          "b00000111110110011010111101100111" -> 0,
          "b11001101111111100010101110000110" -> -533033152,
          "b00010101100000000001101000101111" -> 0,
          "b00001000000001100111010100101001" -> 0,
          "b10011000001111001101011111011100" -> 0,
          "b00000111100110101101101111100101" -> 0,
          "b01001110101100100111111110000010" -> 1497350400,
          "b00110101001100110001001110100001" -> 0,
          "b11101000101011001011010100101100" -> -2147483648,
          "b10001000110011001101111000111110" -> 0,
          "b01110010011110100101100101001111" -> 2147483647,
          "b00110000111011111100100000100000" -> 0,
          "b10001010111100111101011000110100" -> 0,
          "b00011101000110101111001110111101" -> 0,
          "b10011110000010011110111111110010" -> 0,
          "b00101011010000110110011110001000" -> 0,
          "b00100010111011111010000100100110" -> 0,
          "b10100110001101111000110100000100" -> 0,
          "b01110001000000111110010111110110" -> 2147483647,
          "b00100001101111000111000110101000" -> 0,
          "b01111001001010001100000001101111" -> 2147483647,
          "b10011001011000111000001001010000" -> 0,
          "b11001101001111010011101000011001" -> -198418832,
          "b11110100111110000011000010110000" -> -2147483648,
          "b11101011100000011010001000111011" -> -2147483648,
          "b00111000001110111101110000101110" -> 0,
          "b11100101101101100100001101101001" -> -2147483648,
          "b11010000000100111110101011011010" -> -2147483648,
          "b00010011000010000110110100000110" -> 0,
          "b00001110100110110100111101000110" -> 0,
          "b00101100101001000100111110111010" -> 0,
          "b00000100000010101010100110001001" -> 0,
          "b01011001000010101110101011011110" -> 2147483647,
          "b01011110100010111111001111010000" -> 2147483647,
          "b01101110010111010000100000101110" -> 2147483647,
          "b01101001000100001110000111111110" -> 2147483647,
          "b10001110001111110001111110110010" -> 0,
          "b00011010100111001010101000001100" -> 0,
          "b00000100100101100110010001101010" -> 0,
          "b10111101100110011001000110001011" -> 0,
          "b00001101001101000111101001110011" -> 0,
          "b10101001010101100010010111100110" -> 0,
          "b10100001100001010101101011101001" -> 0,
          "b10100001010010000101100000000111" -> 0,
          "b01011100010111100011011011010111" -> 2147483647,
          "b11100010111110011110000010001111" -> -2147483648,
          "b11001111100001111110111001100100" -> -2147483648,
          "b10000100011011111011101100101000" -> 0
        )

        for (i <- testcases) {
          fcvt.io.input(0).poke(i._1.U)

          fcvt.clock.step(1)

          fcvt.io.out.expect(i._2.S)
        }
    }
  }
}