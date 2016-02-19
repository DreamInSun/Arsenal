package cyan.core.util.crypto;

public class CryptoDES51 {

    // 置换表 IP
    private static final byte[] ip = { 0, 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59,
	    51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };

    // 逆置换表 FP = IP^-1
    private static final byte[] fp = { 8, 40, 16, 48, 24, 56, 32, 64, 7, 39, 15, 47, 23, 55, 31, 63, 6, 38, 14, 46, 22, 54, 30, 62, 5, 37, 13, 45, 21, 53, 29, 61, 4, 36, 12, 44, 20, 52, 28, 60, 3,
	    35, 11, 43, 19, 51, 27, 59, 2, 34, 10, 42, 18, 50, 26, 58, 1, 33, 9, 41, 17, 49, 25, 57 };

    // 置换函数 P [16][48]
    private static final byte[] pc = { 10, 51, 34, 60, 49, 17, 33, 57, 2, 9, 19, 42, 3, 35, 26, 25, 44, 58, 59, 1, 36, 27, 18, 41, 22, 28, 39, 54, 37, 4, 47, 30, 5, 53, 23, 29, 61, 21, 38, 63, 15,
	    20, 45, 14, 13, 62, 55, 31, 2, 43, 26, 52, 41, 9, 25, 49, 59, 1, 11, 34, 60, 27, 18, 17, 36, 50, 51, 58, 57, 19, 10, 33, 14, 20, 31, 46, 29, 63, 39, 22, 28, 45, 15, 21, 53, 13, 30, 55, 7,
	    12, 37, 6, 5, 54, 47, 23, 51, 27, 10, 36, 25, 58, 9, 33, 43, 50, 60, 18, 44, 11, 2, 1, 49, 34, 35, 42, 41, 3, 59, 17, 61, 4, 15, 30, 13, 47, 23, 6, 12, 29, 62, 5, 37, 28, 14, 39, 54, 63,
	    21, 53, 20, 38, 31, 7, 35, 11, 59, 49, 9, 42, 58, 17, 27, 34, 44, 2, 57, 60, 51, 50, 33, 18, 19, 26, 25, 52, 43, 1, 45, 55, 62, 14, 28, 31, 7, 53, 63, 13, 46, 20, 21, 12, 61, 23, 38, 47,
	    5, 37, 4, 22, 15, 54, 19, 60, 43, 33, 58, 26, 42, 1, 11, 18, 57, 51, 41, 44, 35, 34, 17, 2, 3, 10, 9, 36, 27, 50, 29, 39, 46, 61, 12, 15, 54, 37, 47, 28, 30, 4, 5, 63, 45, 7, 22, 31, 20,
	    21, 55, 6, 62, 38, 3, 44, 27, 17, 42, 10, 26, 50, 60, 2, 41, 35, 25, 57, 19, 18, 1, 51, 52, 59, 58, 49, 11, 34, 13, 23, 30, 45, 63, 62, 38, 21, 31, 12, 14, 55, 20, 47, 29, 54, 6, 15, 4,
	    5, 39, 53, 46, 22, 52, 57, 11, 1, 26, 59, 10, 34, 44, 51, 25, 19, 9, 41, 3, 2, 50, 35, 36, 43, 42, 33, 60, 18, 28, 7, 14, 29, 47, 46, 22, 5, 15, 63, 61, 39, 4, 31, 13, 38, 53, 62, 55, 20,
	    23, 37, 30, 6, 36, 41, 60, 50, 10, 43, 59, 18, 57, 35, 9, 3, 58, 25, 52, 51, 34, 19, 49, 27, 26, 17, 44, 2, 12, 54, 61, 13, 31, 30, 6, 20, 62, 47, 45, 23, 55, 15, 28, 22, 37, 46, 39, 4,
	    7, 21, 14, 53, 57, 33, 52, 42, 2, 35, 51, 10, 49, 27, 1, 60, 50, 17, 44, 43, 26, 11, 41, 19, 18, 9, 36, 59, 4, 46, 53, 5, 23, 22, 61, 12, 54, 39, 37, 15, 47, 7, 20, 14, 29, 38, 31, 63,
	    62, 13, 6, 45, 41, 17, 36, 26, 51, 19, 35, 59, 33, 11, 50, 44, 34, 1, 57, 27, 10, 60, 25, 3, 2, 58, 49, 43, 55, 30, 37, 20, 7, 6, 45, 63, 38, 23, 21, 62, 31, 54, 4, 61, 13, 22, 15, 47,
	    46, 28, 53, 29, 25, 1, 49, 10, 35, 3, 19, 43, 17, 60, 34, 57, 18, 50, 41, 11, 59, 44, 9, 52, 51, 42, 33, 27, 39, 14, 21, 4, 54, 53, 29, 47, 22, 7, 5, 46, 15, 38, 55, 45, 28, 6, 62, 31,
	    30, 12, 37, 13, 9, 50, 33, 59, 19, 52, 3, 27, 1, 44, 18, 41, 2, 34, 25, 60, 43, 57, 58, 36, 35, 26, 17, 11, 23, 61, 5, 55, 38, 37, 13, 31, 6, 54, 20, 30, 62, 22, 39, 29, 12, 53, 46, 15,
	    14, 63, 21, 28, 58, 34, 17, 43, 3, 36, 52, 11, 50, 57, 2, 25, 51, 18, 9, 44, 27, 41, 42, 49, 19, 10, 1, 60, 7, 45, 20, 39, 22, 21, 28, 15, 53, 38, 4, 14, 46, 6, 23, 13, 63, 37, 30, 62,
	    61, 47, 5, 12, 42, 18, 1, 27, 52, 49, 36, 60, 34, 41, 51, 9, 35, 2, 58, 57, 11, 25, 26, 33, 3, 59, 50, 44, 54, 29, 4, 23, 6, 5, 12, 62, 37, 22, 55, 61, 30, 53, 7, 28, 47, 21, 14, 46, 45,
	    31, 20, 63, 26, 2, 50, 11, 36, 33, 49, 44, 18, 25, 35, 58, 19, 51, 42, 41, 60, 9, 10, 17, 52, 43, 34, 57, 38, 13, 55, 7, 53, 20, 63, 46, 21, 6, 39, 45, 14, 37, 54, 12, 31, 5, 61, 30, 29,
	    15, 4, 47, 18, 59, 42, 3, 57, 25, 41, 36, 10, 17, 27, 50, 11, 43, 34, 33, 52, 1, 2, 9, 44, 35, 26, 49, 30, 5, 47, 62, 45, 12, 55, 38, 13, 61, 31, 37, 6, 29, 46, 4, 23, 28, 53, 22, 21, 7,
	    63, 39 };

    // S盒 256 B
    /*
    private static final byte[] pk_si = { (byte) 0xe0, (byte) 0x4f, (byte) 0xd7, (byte) 0x14, (byte) 0x2e, (byte) 0xf2, (byte) 0xbd, (byte) 0x81, (byte) 0x3a, (byte) 0xa6, (byte) 0x6c, (byte) 0xcb,
	    (byte) 0x59, (byte) 0x95, (byte) 0x03, (byte) 0x78, (byte) (byte) 0x4f, (byte) 0x1c, (byte) 0xe8, (byte) 0x82, (byte) 0xd4, (byte) 0x69, (byte) 0x21, (byte) 0xb7, (byte) 0xf5,
	    (byte) 0xcb, (byte) 0x93, (byte) 0x7e, (byte) 0x3a, (byte) 0xa0, (byte) 0x56, (byte) 0x0d, (byte) 0xf3, (byte) 0x1d, (byte) 0x84, (byte) 0xe7, (byte) 0x6f, (byte) 0xb2, (byte) 0x38,
	    (byte) 0x4e, (byte) 0x9c, (byte) 0x70, (byte) 0x21, (byte) 0xda, (byte) 0xc6, (byte) 0x09, (byte) 0x5b, (byte) 0xa5, (byte) (byte) 0x0d, (byte) 0xe8, (byte) 0x7a, (byte) 0xb1,
	    (byte) 0xa3, (byte) 0x4f, (byte) 0xd4, (byte) 0x12, (byte) 0x5b, (byte) 0x86, (byte) 0xc7, (byte) 0x6c, (byte) 0x90, (byte) 0x35, (byte) 0x2e, (byte) 0xf9, (byte) (byte) 0xad,
	    (byte) 0x07, (byte) 0x90, (byte) 0xe9, (byte) 0x63, (byte) 0x34, (byte) 0xf6, (byte) 0x5a, (byte) 0x12, (byte) 0xd8, (byte) 0xc5, (byte) 0x7e, (byte) 0xbc, (byte) 0x4b, (byte) 0x2f,
	    (byte) 0x81, (byte) (byte) 0xd1, (byte) 0x6a, (byte) 0x4d, (byte) 0x90, (byte) 0x86, (byte) 0xf9, (byte) 0x38, (byte) 0x07, (byte) 0xb4, (byte) 0x1f, (byte) 0x2e, (byte) 0xc3,
	    (byte) 0x5b, (byte) 0xa5, (byte) 0xe2, (byte) 0x7c, (byte) (byte) 0x7d, (byte) 0xd8, (byte) 0xeb, (byte) 0x35, (byte) 0x06, (byte) 0x6f, (byte) 0x90, (byte) 0xa3, (byte) 0x14,
	    (byte) 0x27, (byte) 0x82, (byte) 0x5c, (byte) 0xb1, (byte) 0xca, (byte) 0x4e, (byte) 0xf9, (byte) (byte) 0xa3, (byte) 0x6f, (byte) 0x90, (byte) 0x06, (byte) 0xca, (byte) 0xb1,
	    (byte) 0x7d, (byte) 0xd8, (byte) 0xf9, (byte) 0x14, (byte) 0x35, (byte) 0xeb, (byte) 0x5c, (byte) 0x27, (byte) 0x82, (byte) 0x4e, (byte) (byte) 0x2e, (byte) 0xcb, (byte) 0x42,
	    (byte) 0x1c, (byte) 0x74, (byte) 0xa7, (byte) 0xbd, (byte) 0x61, (byte) 0x85, (byte) 0x50, (byte) 0x3f, (byte) 0xfa, (byte) 0xd3, (byte) 0x09, (byte) 0xe8, (byte) 0x96,
	    (byte) (byte) 0x4b, (byte) 0x28, (byte) 0x1c, (byte) 0xb7, (byte) 0xa1, (byte) 0xde, (byte) 0x72, (byte) 0x8d, (byte) 0xf6, (byte) 0x9f, (byte) 0xc0, (byte) 0x59, (byte) 0x6a,
	    (byte) 0x34, (byte) 0x05, (byte) 0xe3, (byte) (byte) 0xca, (byte) 0x1f, (byte) 0xa4, (byte) 0xf2, (byte) 0x97, (byte) 0x2c, (byte) 0x69, (byte) 0x85, (byte) 0x06, (byte) 0xd1,
	    (byte) 0x3d, (byte) 0x4e, (byte) 0xe0, (byte) 0x7b, (byte) 0x53, (byte) 0xb8, (byte) (byte) 0x94, (byte) 0xe3, (byte) 0xf2, (byte) 0x5c, (byte) 0x29, (byte) 0x85, (byte) 0xcf,
	    (byte) 0x3a, (byte) 0x7b, (byte) 0x0e, (byte) 0x41, (byte) 0xa7, (byte) 0x16, (byte) 0xd0, (byte) 0xb8, (byte) 0x6d, (byte) (byte) 0x4d, (byte) 0xb0, (byte) 0x2b, (byte) 0xe7,
	    (byte) 0xf4, (byte) 0x09, (byte) 0x81, (byte) 0xda, (byte) 0x3e, (byte) 0xc3, (byte) 0x95, (byte) 0x7c, (byte) 0x52, (byte) 0xaf, (byte) 0x68, (byte) 0x16, (byte) (byte) 0x16,
	    (byte) 0x4b, (byte) 0xbd, (byte) 0xd8, (byte) 0xc1, (byte) 0x34, (byte) 0x7a, (byte) 0xe7, (byte) 0xa9, (byte) 0xf5, (byte) 0x60, (byte) 0x8f, (byte) 0x0e, (byte) 0x52, (byte) 0x93,
	    (byte) 0x2c, (byte) (byte) 0xd1, (byte) 0x2f, (byte) 0x8d, (byte) 0x48, (byte) 0x6a, (byte) 0xf3, (byte) 0xb7, (byte) 0x14, (byte) 0xac, (byte) 0x95, (byte) 0x36, (byte) 0xeb,
	    (byte) 0x50, (byte) 0x0e, (byte) 0xc9, (byte) 0x72, (byte) (byte) 0x72, (byte) 0xb1, (byte) 0x4e, (byte) 0x17, (byte) 0x94, (byte) 0xca, (byte) 0xe8, (byte) 0x2d, (byte) 0x0f,
	    (byte) 0x6c, (byte) 0xa9, (byte) 0xd0, (byte) 0xf3, (byte) 0x35, (byte) 0x56, (byte) 0x8b };
     */

    // 8 B
    private static final byte[] shift = { (byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02, (byte) 0x01 };

    // code const unsigned char newpc[16][48];
    // newsi[8][64][4]
    private static final byte[][][] newsi = new byte[8][64][4];

    /* ========== Function : DES_crypt ========== */
    /**
     * 
     * @param mode
     * @param key
     *            16 * 8 key[16][8]
     * @param input
     *            8 Byte
     * @param output
     *            8 Byte
     */
    static byte[] DES_crypt(boolean isEncrypt, byte[][] key, byte[] input ) {
	int i, j;
	byte k;
	byte m;
	int s1i, s2i;
	byte[] s1, s2;
	byte[] r0_6 = new byte[8];
	byte[] textbit = new byte[65];
	byte[] tranx = new byte[65];
	//
	byte[] output = new byte[8];
	
	k = 0;
	for (i = 0; i < 8; ++i) {
	    m = input[i];
	    for (j = 0; j < 8; ++j) {
		textbit[++k] = (byte) (((m & shift[j]) == 0) ? 0x00 : 0x01);
	    }
	}

	for (i = 0; i < 65; ++i)
	    tranx[i] = textbit[i];
	for (i = 1; i < 65; ++i)
	    textbit[i] = tranx[ip[i]];

	for (i = 0; i < 16; ++i) {
	    for (j = 1; j < 33; ++j)
		tranx[j] = textbit[32 + j];
	    r0_6[0] = textbit[37];
	    r0_6[1] = textbit[41];
	    r0_6[2] = textbit[45];
	    r0_6[3] = textbit[49];
	    r0_6[4] = textbit[53];
	    r0_6[5] = textbit[57];
	    r0_6[6] = textbit[61];
	    r0_6[7] = textbit[33];
	    k = textbit[32];
	    textbit[32] = textbit[64];

	    for (j = 0; j < 5; ++j) {
		m = shift[2 + j];
		if (textbit[32 + j] != 0x00)
		    r0_6[0] |= m;
		if (textbit[36 + j] != 0x00)
		    r0_6[1] |= m;
		if (textbit[40 + j] != 0x00)
		    r0_6[2] |= m;
		if (textbit[44 + j] != 0x00)
		    r0_6[3] |= m;
		if (textbit[48 + j] != 0x00)
		    r0_6[4] |= m;
		if (textbit[52 + j] != 0x00)
		    r0_6[5] |= m;
		if (textbit[56 + j] != 0x00)
		    r0_6[6] |= m;
		if (textbit[60 + j] != 0x00)
		    r0_6[7] |= m;
	    }

	    textbit[32] = k;
	    k = 32;
	    if (isEncrypt)
		s1 = key[i];
	    else
		s1 = key[15 - i];

	    for (j = 0; j < 8; j++ ) {
		m = (byte) (r0_6[j] ^ s1[j]);
		s2 = newsi[j][m];
		s2i = 0;
		textbit[++k] = s2[s2i++];
		textbit[++k] = s2[s2i++];
		textbit[++k] = s2[s2i++];
		textbit[++k] = s2[s2i++];
	    }

	    k = textbit[33]; /* transpose */
	    textbit[33] = textbit[48];
	    textbit[48] = textbit[42];
	    textbit[42] = textbit[47];
	    textbit[47] = textbit[63];
	    textbit[63] = textbit[36];
	    textbit[36] = textbit[53];
	    textbit[53] = textbit[64];
	    textbit[64] = textbit[57];
	    textbit[57] = textbit[51];
	    textbit[51] = textbit[56];
	    textbit[56] = textbit[41];
	    textbit[41] = k;

	    k = textbit[34];
	    textbit[34] = textbit[39];
	    textbit[39] = textbit[60];
	    textbit[60] = textbit[38];
	    textbit[38] = textbit[44];
	    textbit[44] = textbit[58];
	    textbit[58] = textbit[45];
	    textbit[45] = textbit[37];
	    textbit[37] = textbit[61];
	    textbit[61] = textbit[54];
	    textbit[54] = textbit[59];
	    textbit[59] = textbit[62];
	    textbit[62] = textbit[43];
	    textbit[43] = textbit[55];
	    textbit[55] = textbit[35];
	    textbit[35] = textbit[52];
	    textbit[52] = textbit[46];
	    textbit[46] = textbit[50];
	    textbit[50] = textbit[40];
	    textbit[40] = textbit[49];
	    textbit[49] = k;

	    for (j = 1; j < 33; ++j) {
		textbit[j + 32] ^= textbit[j];
		textbit[j] = tranx[j];
	    }
	}

	k = 0;
	s1 = output;
	s1i = 0;
	for (i = 0; i < 8; ++i) {
	    output[s1i] = 0;
	    for (j = 0; j < 8; ++j) {
		if (textbit[fp[k++]] != 0)
		    output[s1i] |= shift[j];
	    }
	    s1i++;
	}
	
	return output;
    }

    /* ========== Function : DES_key ========== */
    /**
     * @function DES_key
     * @brief
     */
    static byte[][] DES_genKey(byte[] temp) {
	int i, j, si;
	byte k, m;
	byte[] s;
	byte[] tempbit = new byte[65];
	
	byte[][] key = new byte[16][8];
	
	m = 0;
	for (i = 0; i < 8; ++i) {
	    k = temp[i];
	    for (j = 0; j < 8; ++j)
		tempbit[++m] = (byte) (((k & shift[j]) == 0) ? 0x00 : 0x01);
	}

	s = pc;
	si = 0;
	for (i = 0; i < 16; ++i) {
	    for (j = 0; j < 8; ++j) {
		key[i][j] = 0;
		for (k = 2; k < 8; ++k) {
		    if (tempbit[s[si++]] != 0x00)
			key[i][j] |= shift[k];
		}

	    }
	}
	return key;
    }
}
