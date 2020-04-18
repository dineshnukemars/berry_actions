package com.sky.tm1638


val fontByteMappings = mapOf(
        ' ' to 0b00000000,  // (32) <space>
        '!' to 0b10000110,  // (33) !
        '"' to 0b00100010,  // (34) "
        '(' to 0b00110000,  // (40) (
        ')' to 0b00000110,  // (41) )
        ',' to 0b00000100,  // (44) ,
        '-' to 0b01000000,  // (45) -
        '.' to 0b10000000,  // (46) .
        '/' to 0b01010010,  // (47) /
        '0' to 0b00111111,  // (48) 0
        '1' to 0b00000110,  // (49) 1
        '2' to 0b01011011,  // (50) 2
        '3' to 0b01001111,  // (51) 3
        '4' to 0b01100110,  // (52) 4
        '5' to 0b01101101,  // (53) 5
        '6' to 0b01111101,  // (54) 6
        '7' to 0b00100111,  // (55) 7
        '8' to 0b01111111,  // (56) 8
        '9' to 0b01101111,  // (57) 9
        '=' to 0b01001000,  // (61) =
        '?' to 0b01010011,  // (63) ?
        '@' to 0b01011111,  // (64) @
        'A' to 0b01110111,  // (65) A
        'B' to 0b01111111,  // (66) B
        'C' to 0b00111001,  // (67) C
        'D' to 0b00111111,  // (68) D
        'E' to 0b01111001,  // (69) E
        'F' to 0b01110001,  // (70) F
        'G' to 0b00111101,  // (71) G
        'H' to 0b01110110,  // (72) H
        'I' to 0b00000110,  // (73) I
        'J' to 0b00011111,  // (74) J
        'K' to 0b01101001,  // (75) K
        'L' to 0b00111000,  // (76) L
        'M' to 0b00010101,  // (77) M
        'N' to 0b00110111,  // (78) N
        'O' to 0b00111111,  // (79) O
        'P' to 0b01110011,  // (80) P
        'Q' to 0b01100111,  // (81) Q
        'R' to 0b00110001,  // (82) R
        'S' to 0b01101101,  // (83) S
        'T' to 0b01111000,  // (84) T
        'U' to 0b00111110,  // (85) U
        'V' to 0b00101010,  // (86) V
        'W' to 0b00011101,  // (87) W
        'X' to 0b01110110,  // (88) X
        'Y' to 0b01101110,  // (89) Y
        'Z' to 0b01011011,  // (90) Z
        '[' to 0b00111001,  // (91) [
        ']' to 0b00001111,  // (93) ]
        '_' to 0b00001000,  // (95) _
        '`' to 0b00100000,  // (96) `
        'a' to 0b01011111,  // (97) a
        'b' to 0b01111100,  // (98) b
        'c' to 0b01011000,  // (99) c
        'd' to 0b01011110,  // (100) d
        'e' to 0b01111011,  // (101) e
        'f' to 0b00110001,  // (102) f
        'g' to 0b01101111,  // (103) g
        'h' to 0b01110100,  // (104) h
        'i' to 0b00000100,  // (105) i
        'j' to 0b00001110,  // (106) j
        'k' to 0b01110101,  // (107) k
        'l' to 0b00110000,  // (108) l
        'm' to 0b01010101,  // (109) m
        'n' to 0b01010100,  // (110) n
        'o' to 0b01011100,  // (111) o
        'p' to 0b01110011,  // (112) p
        'q' to 0b01100111,  // (113) q
        'r' to 0b01010000,  // (114) r
        's' to 0b01101101,  // (115) s
        't' to 0b01111000,  // (116) t
        'u' to 0b00011100,  // (117) u
        'v' to 0b00101010,  // (118) v
        'w' to 0b00011101,  // (119) w
        'x' to 0b01110110,  // (120) x
        'y' to 0b01101110,  // (121) y
        'z' to 0b01000111,  // (122) z
        '{' to 0b01000110,  // (123) {
        '|' to 0b00000110,  // (124) |
        '}' to 0b01110000,  // (125) }
        '~' to 0b00000001  // (126) ~
)

val segmentMappings = listOf(
        0b00000000,
        0b00000001,
        0b00000010,
        0b00000100,
        0b00001000,
        0b00010000,
        0b00100000,
        0b01000000,
        0b10000000
)