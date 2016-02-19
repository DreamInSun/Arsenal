package cyan.util.byteop;

public class BytesOperation {

	/**
	 * Get 2 Bytes Integer value from buffer, in Big Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static short getInt16_BE(byte[] bytes, int offset) {
		int tmpRet = 0;
		if (bytes.length >= (offset + 2)) {
			int H = bytes[offset] & 0xFF;
			int L = bytes[offset + 1] & 0xFF;
			tmpRet = ((H << 8) + (L));
		}
		return (short) tmpRet;
	}

	/**
	 * Get 2 Bytes Integer value from buffer, in Little Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static short getInt16_LE(byte[] bytes, int offset) {
		int tmpRet = 0;
		if (bytes.length >= (offset + 2)) {
			int H = bytes[offset + 1] & 0xFF;
			int L = bytes[offset] & 0xFF;
			tmpRet = ((H << 8) + (L));
		}
		return (short) tmpRet;
	}

	/**
	 * Get 4 Bytes Integer value from buffer, in Little Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static int getInt32_LE(byte[] bytes, int offset) {
		int tmpRet = 0;
		if (bytes.length >= (offset + 4)) {
			int HH = bytes[offset + 3] & 0xFF;
			int HL = bytes[offset + 2] & 0xFF;
			int LH = bytes[offset + 1] & 0xFF;
			int LL = bytes[offset] & 0xFF;
			tmpRet = ((HH << 24) + (HL << 16) + (LH << 8) + (LL));
		}
		return tmpRet;
	}

	/**
	 * Get 4 Bytes Integer value from buffer, in Big Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static int getInt32_BE(byte[] bytes, int offset) {
		int tmpRet = 0;
		if (bytes.length >= (offset + 4)) {
			int HH = bytes[offset] & 0xFF;
			int HL = bytes[offset + 1] & 0xFF;
			int LH = bytes[offset + 2] & 0xFF;
			int LL = bytes[offset + 3] & 0xFF;
			tmpRet = ((HH << 24) + (HL << 16) + (LH << 8) + (LL));
		}
		return tmpRet;
	}

	/**
	 * Get 6 Bytes Integer value from buffer, in Big Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static long getInt48_BE(byte[] bytes, int offset) {
		long tmpRet = 0;
		if (bytes.length >= (offset + 6)) {
			long B5 = bytes[offset + 5] & 0xFF;
			long B4 = bytes[offset + 4] & 0xFF;
			long B3 = bytes[offset + 3] & 0xFF;
			long B2 = bytes[offset + 2] & 0xFF;
			long B1 = bytes[offset + 1] & 0xFF;
			long B0 = bytes[offset] & 0xFF;
			tmpRet = (B0 << 40) + (B1 << 32) + (B2 << 24) + (B3 << 16) + (B4 << 8) + (B5);
		}
		return tmpRet;
	}

	/**
	 * Get 6 Bytes Integer value from buffer, in Little Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static long getInt48_LE(byte[] bytes, int offset) {
		long tmpRet = 0;
		if (bytes.length >= (offset + 6)) {
			long B5 = bytes[offset + 5] & 0xFF;
			long B4 = bytes[offset + 4] & 0xFF;
			long B3 = bytes[offset + 3] & 0xFF;
			long B2 = bytes[offset + 2] & 0xFF;
			long B1 = bytes[offset + 1] & 0xFF;
			long B0 = bytes[offset] & 0xFF;
			tmpRet = (B5 << 40) + (B4 << 32) + (B3 << 24) + (B2 << 16) + (B1 << 8) + (B0);

		}
		return tmpRet;
	}

	/**
	 * Get 8 Bytes Integer value from buffer, in Big Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static long getInt64_BE(byte[] bytes, int offset) {
		long tmpRet = 0;
		if (bytes.length >= (offset + 8)) {
			long B7 = bytes[offset + 7] & 0xFF;
			long B6 = bytes[offset + 6] & 0xFF;
			long B5 = bytes[offset + 5] & 0xFF;
			long B4 = bytes[offset + 4] & 0xFF;
			long B3 = bytes[offset + 3] & 0xFF;
			long B2 = bytes[offset + 2] & 0xFF;
			long B1 = bytes[offset + 1] & 0xFF;
			long B0 = bytes[offset] & 0xFF;
			tmpRet = (B0 << 56) + (B1 << 48) + (B2 << 40) + (B3 << 32) + (B4 << 24) + (B5 << 16) + (B6 << 8) + (B7);

		}
		return tmpRet;
	}

	/**
	 * Get 8 Bytes Integer value from buffer, in Little Endian Order.
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static long getInt64_LE(byte[] bytes, int offset) {
		long tmpRet = 0;
		if (bytes.length >= (offset + 8)) {
			long B7 = bytes[offset + 7] & 0xFF;
			long B6 = bytes[offset + 6] & 0xFF;
			long B5 = bytes[offset + 5] & 0xFF;
			long B4 = bytes[offset + 4] & 0xFF;
			long B3 = bytes[offset + 3] & 0xFF;
			long B2 = bytes[offset + 2] & 0xFF;
			long B1 = bytes[offset + 1] & 0xFF;
			long B0 = bytes[offset] & 0xFF;
			tmpRet = (B7 << 56) + (B6 << 48) + (B5 << 40) + (B4 << 32) + (B3 << 24) + (B2 << 16) + (B1 << 8) + (B0);

		}
		return tmpRet;
	}
}
