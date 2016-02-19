package cyan.terminal.addr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.cyan.util.byteop.BytesOperation;

public class MAC implements Externalizable {

	/*========== Constants =========*/

	/*========== Properties =========*/
	private long m_val;

	/*========== Getter & Setter =========*/
	public long getVal() {
		return m_val;
	}

	public void setVal(Long macVal) {
		m_val = macVal;
	}

	/*========== Constructor ==========*/
	public MAC() {
		m_val = 0;
	}

	public MAC(long macVal) {
		m_val = macVal;
	}

	public MAC(byte[] bytes) {
		m_val = BytesOperation.getInt48_BE(bytes, 0);
	}

	public MAC(String macStr) {
		m_val = MAC.toLong(macStr);
	}

	/*========== Converter ==========*/
	/**
	 * Convert MAC String to MAC Value;
	 * 
	 * @param strMac
	 *            Support "00-11-22-33-44-55" or "001122334455" MAC String;
	 */
	public static long toLong(String strMac) {
		String regStrMac = strMac.replace("-", "");
		return Long.valueOf(regStrMac, 16);
	}

	/*==========  ==========*/
	public String toMacString() {
		String tmpRet = Long.toHexString(m_val);
		/*===== Leading Zero =====*/
		int cnt = 12 - tmpRet.length();
		if (cnt > 0) {
			byte[] leadZero = new byte[cnt];
			Arrays.fill(leadZero, (byte) '0');
			tmpRet = new String(leadZero) + tmpRet;
		}
		return tmpRet.toUpperCase();
	}

	public String toIEEEString() {
		StringBuffer sb = new StringBuffer(this.toMacString());
		sb.insert(10, '-').insert(8, '-').insert(6, '-').insert(4, '-').insert(2, '-');
		return sb.toString();
	}

	public byte[] getBytes() {
		byte[] tmpBuf = new byte[6];
		tmpBuf[0] = (byte) ((m_val >> 40) & 0xFF);
		tmpBuf[1] = (byte) ((m_val >> 32) & 0xFF);
		tmpBuf[2] = (byte) ((m_val >> 24) & 0xFF);
		tmpBuf[3] = (byte) ((m_val >> 16) & 0xFF);
		tmpBuf[4] = (byte) ((m_val >> 8) & 0xFF);
		tmpBuf[5] = (byte) ((m_val) & 0xFF);
		return tmpBuf;
	}

	/*========== toString ==========*/
	@Override
	public String toString() {
		return toIEEEString();
	}

	/*========== Interface : Externalizable ==========*/

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MAC))
			return false;
		if (((MAC) obj).m_val != m_val)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		m_val = in.readLong();

	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(m_val);
	}

}
