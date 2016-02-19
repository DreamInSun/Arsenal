package cyan.util.byteop;

public class CRC16 {

	/*========== Constant ==========*/
	public static final short DEFAULT_POLY = (short)0x1021;
	public static final short MODBUS_POLY = (short)0xA001;
	
	/*========== Properties ==========*/
	private short[] crcTable = new short[256];
	private int gPloy = DEFAULT_POLY; // 生成多项式

	/*========== Constructor ==========*/
	public CRC16() {
		this(DEFAULT_POLY);
	}
	
	public CRC16(int poly){
		this.gPloy = poly;
		computeCrcTable();
	}

	/*========== Export CRC ==========*/
	public short getCrc(byte[] data) {
		return this.getCrc(data, (short) 0x0000 );
	}
	
	public short getCrc(byte[] data, short initVal){
		int crc = initVal;
		int length = data.length;
		for (int i = 0; i < length; i++) {
			crc = ((crc & 0xFF) << 8) ^ crcTable[(((crc & 0xFF00) >> 8) ^ data[i]) & 0xFF];
		}
		crc = crc & 0xFFFF;
		return (short) crc;
	}

	/*========== Assistance Function ==========*/
	private short getCrcOfByte(int aByte) {
		int value = aByte << 8;
		for (int count = 7; count >= 0; count--) {
			if ((value & 0x8000) != 0) { // 高第16位为1，可以按位异或
				value = (value << 1) ^ gPloy;
			} else {
				value = value << 1; // 首位为0，左移
			}
		}
		value = value & 0xFFFF; // 取低16位的值
		return (short) value;
	}

	/*
	 * 生成0 - 255对应的CRC16校验码
	 */
	private void computeCrcTable() {
		for (int i = 0; i < 256; i++) {
			crcTable[i] = getCrcOfByte(i);
		}
	}

	/*=============================================================*/
	/*==================== Direct Calculation =====================*/
	/*=============================================================*/
	public short getDirectCRC16(byte[] data) {
		return this.getDirectCRC16(data, (short)0xFFFF, DEFAULT_POLY);
	}
	
	public short getDirectCRC16(byte[] data, short initVal ) {
		return this.getDirectCRC16(data, initVal, DEFAULT_POLY);
	}

	public short getDirectCRC16(byte[] data, short initVal, short poly) {
		short tmpRet = initVal;
		for (byte b : data) {
			tmpRet = update2(tmpRet, b, poly);
		}
		return tmpRet;
	}
	
	private short update2(short value, byte aByte, short poly) {
		int crc = ( value ^ ( aByte & 0x000000FF ) ) & 0x0000FFFF;
		for (int count = 8; count > 0; count--) {
			 if( 0x01 == ( crc & 0x01 ) ){
				 crc >>= 1;
				 crc ^= poly;
			 }else{
				 crc >>= 1;
			 }
			 crc &= 0x0000FFFF;
		}
		return (short) crc;
	}

	private short update(int value, byte aByte, short poly) {
		int a, b;
		a = (int) aByte;
		for (int count = 7; count >= 0; count--) {
			a = a << 1;
			b = (a >>> 8) & 1;
			if ((value & 0x8000) != 0) {
				value = ((value << 1) + b) ^ poly;
			} else {
				value = (value << 1) + b;
			}
		}
		return (short) ( value & 0x0000FFFF );
	}
}
