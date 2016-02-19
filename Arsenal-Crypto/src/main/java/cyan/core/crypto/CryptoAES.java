package cyan.core.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoAES {
	public static final String TAG = "UtilAES";

	/* ========== Constant ========== */
	public static final int KEY_BLOCK_SIZE_128_BITS = 128;
	public static final int KEY_BLOCK_SIZE_192_BITS = 192;
	public static final int KEY_BLOCK_SIZE_256_BITS = 256;

	/* ========== Key Block Generator ========== */
	/**
	 * Convert input keyword to key block fits AES algorithm.
	 * 
	 * @param strKeyword
	 *            String of keyword, should be less then 15/23/31 characters
	 * @param blockBytes
	 *            Block bits length
	 * @return Byte array of key block
	 * @see CryptoAES.KEY_BLOCK_SIZE_128_BITS
	 * @see CryptoAES.KEY_BLOCK_SIZE_192_BITS
	 * @see CryptoAES.KEY_BLOCK_SIZE_256_BITS
	 * @return
	 */
	public static byte[] generateKey(String strKeyword, int blockBytes) {
		return generateKey(strKeyword.getBytes(), blockBytes);
	}

	/**
	 * Convert input keyword to key block fits AES algorithm.
	 * 
	 * @param bsKeyword
	 *            Array bytes of keyword, should be less then 15/23/31 bytes
	 * @param blockBytes
	 *            Block bits length
	 * @return Byte array of key block
	 * @see CryptoAES.KEY_BLOCK_SIZE_128_BITS
	 * @see CryptoAES.KEY_BLOCK_SIZE_192_BITS
	 * @see CryptoAES.KEY_BLOCK_SIZE_256_BITS
	 * @return
	 */
	public static byte[] generateKey(byte[] bsKeyword, int blockBytes) {
		int blockSize = blockBytes / 8;
		byte[] tempRet = new byte[blockSize];
		int keyLen = bsKeyword.length;
		int i;
		for (i = 0; i < keyLen; i++) {
			tempRet[i] = bsKeyword[i];
		}
		int blankCount = 0;

		for (; i < (blockSize - 1); i++) {
			tempRet[i] = 0x00;
			blankCount++;
		}
		tempRet[blockSize - 1] = (byte) (blankCount & 0xFF);
		return tempRet;
	}

	/* ========== Bytes Encryption ========== */
	public static byte[] encryptBytes(byte[] bsKeyword, byte[] bsPlain) {
		byte[] ret = null;
		try {
			ret = encrypt(CryptoAES.generateKey(bsKeyword, KEY_BLOCK_SIZE_128_BITS), bsPlain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static byte[] decryptBytes(byte[] bsKeyword, byte[] bsCipher) {
		byte[] ret = null;
		try {
			ret = decrypt(CryptoAES.generateKey(bsKeyword, KEY_BLOCK_SIZE_128_BITS), bsCipher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/* ========== String Encryption ========== */
	/**
	 * Encrypt Bytes Array to BASE 64 String
	 * 
	 * @param bsKeyBlock
	 * @param strPlain
	 *            UTF-8
	 * @return
	 */
	public static String encryptToBase64(byte[] bsKeyBlock, byte[] bsPlain) {
		String strEncrypt = null;
		try {
			byte[] ret = encrypt(bsKeyBlock, bsPlain);
			strEncrypt = new String(new Base64().encode(ret));
		} catch (Exception e) {
			System.out.print(e);
		}
		return strEncrypt;
	}

	public static String encryptToBase64(String keyword, String strPlain) throws UnsupportedEncodingException {
		return encryptToBase64(CryptoAES.generateKey(keyword, KEY_BLOCK_SIZE_128_BITS), strPlain.getBytes("UTF-8"));
	}

	public static String encryptToBase64(String keyword, byte[] bsPlain) {
		return encryptToBase64(CryptoAES.generateKey(keyword, KEY_BLOCK_SIZE_128_BITS), bsPlain);
	}

	/**
	 * Encrypt Bytes Array from BASE 64 String
	 * 
	 * @param bsKeyBlock
	 * @param strCipher
	 * @return
	 */
	public static String decryptFromBase64(byte[] bsKeyBlock, byte[] bsCipher) {
		String strDecrypt = null;
		try {
			byte[] ret = decrypt(bsKeyBlock, bsCipher);
			strDecrypt = new String(ret, "UTF-8");
		} catch (Exception e) {
			System.out.print(e);
		}
		return strDecrypt;
	}

	public static String decryptFromBase64(String keyword, String strCipher) {
		return decryptFromBase64(CryptoAES.generateKey(keyword, KEY_BLOCK_SIZE_128_BITS), new Base64().decode(strCipher));
	}

	public static String decryptFromBase64(String keyword, byte[] bsCipher) {
		return decryptFromBase64(CryptoAES.generateKey(keyword, KEY_BLOCK_SIZE_128_BITS), bsCipher);
	}

	/* ========== String Encryption ========== */
	/**
	 * 
	 * @param seed
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
	public static String encryptToHexStr(String seed, String cleartext) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());

		String publicKey = bytesArrayToHexStr(rawKey, rawKey.length);
		String strCipher = bytesArrayToHexStr(result, result.length);
		return publicKey + "|" + strCipher;
	}

	/**
	 * 
	 * @param seed
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	public static String decryptFromHexStr(String encrypted) throws Exception {
		String[] tmpStr = encrypted.split("\\|");
		byte[] rawKey = HexStrToBytesArray(tmpStr[0]);
		byte[] enc = HexStrToBytesArray(tmpStr[1]);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	/* ========== Byte Array Encryption ========== */
	/**
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	/**
	 * 
	 * @param raw
	 * @param plainText
	 * @return bytes Array of Cipher Text
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] raw, byte[] plainText) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(plainText);
	}

	/**
	 * 
	 * @param raw
	 * @param cipherText
	 * @return bytes Array of Plain Text
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] raw, byte[] cipherText) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(cipherText);
	}

	/* ========== String & Hex String Converter ========== */
	public static String stringToHexStr(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			String strHex = String.format(" 0x%02X", strPart.charAt(i));
			hexString = hexString + strHex;
		}
		return hexString;
	}

	public static String stringFromHexStr(String strHex) {
		String string = "";

		return string;
	}

	/* ========== Byte Array & Hex String Converter ========== */
	public static String byteToHex(byte b) {
		byte[] appendBytes = new byte[2];
		appendBytes[0] = changeByteToASC((byte) ((b & 0xF0) >> 4));
		appendBytes[1] = changeByteToASC((byte) (b & 0x0F));
		return new String(appendBytes);
	}

	public static byte hexToByte(String str) {
		byte tmpRet = 0x00;
		byte[] appendBytes = str.getBytes();
		tmpRet = (byte) (((changeASCtoByte(appendBytes[0]) << 4) & 0xF0) + (changeASCtoByte(appendBytes[0]) & 0x0F));
		return tmpRet;
	}

	public static byte hexToByte(byte b1, byte b2) {
		byte tmpRet = 0x00;
		tmpRet = (byte) (((changeASCtoByte(b1) << 4) & 0xF0) + (changeASCtoByte(b2) & 0x0F));
		return tmpRet;
	}

	private static byte changeByteToASC(byte b) {
		if (b >= 0x00 && b <= 0x09) {
			b += '0';
		} else if (b >= 0x0A && b <= 0x0F) {
			b += ('A' - 0x0A);
		}
		return b;
	}

	private static byte changeASCtoByte(byte b) {
		if (b >= '0' && b <= '9') {
			b -= '0';
		} else if (b >= 'A' && b <= 'F') {
			b -= ('A' - 0x0A);
		}
		return b;
	}

	public static String bytesArrayToHexStr(byte[] byteArray, int length) {
		String hexString = "";
		for (int i = 0; i < length; i++) {
			hexString += byteToHex(byteArray[i]);
		}
		return hexString;
	}

	public static byte[] HexStrToBytesArray(String strHex) {
		int bufferLen = strHex.length() / 2;
		byte[] tmpBytes = new byte[bufferLen];
		byte[] strBytes = strHex.getBytes();
		for (int i = 0; i < bufferLen; i++) {
			tmpBytes[i] = hexToByte(strBytes[2 * i], strBytes[2 * i + 1]);
		}
		return tmpBytes;
	}
}
