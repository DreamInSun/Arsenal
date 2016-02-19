package cyan.util.str;

/**
 * 
 * @author DreamInSun
 * 
 */
public class StringUtil {

	/*==========  ==========*/
	public static String stringToHexString(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			String strHex = String.format(" 0x%02X", strPart.charAt(i));
			hexString = hexString + strHex;
		}
		return hexString;
	}

	/*==========  ==========*/
	public static String byteArrayToHexString(byte[] byteArray, int length) {
		String hexString = "";
		for (int i = 0; i < length; i++) {
			String strHex = String.format(" 0x%02X", byteArray[i]);
			hexString = hexString + strHex;
		}
		return hexString;
	}

	/*==========  ==========*/

}
