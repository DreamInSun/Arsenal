package cyan.terminal.hardware;

import java.util.Properties;

public class RomMapper {

	/*========== Factory ==========*/

	/*========== Constant ==========*/

	/*========== Properties ==========*/
	/** */

	/** Properties describing the Memory Address of */
	Properties propMap;

	/*========== Constructor ==========*/
	public RomMapper(Properties prop) {

	}

	public RomMapper(String jsonString) {
		RomMapper.parseFromJson(jsonString);
	}

	/*========== Static Function ==========*/
	public static RomMapper parseFromJson(String jsonString) {
		RomMapper tmpRet = null;
		return tmpRet;
	}
}
