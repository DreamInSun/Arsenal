package cyan.util.pool;

public class PoolConfig {

	/*========== Properties ==========*/
	public int maxConnectionPerHost;
	public int maxTotalConnections;

	/*========== Constructor ==========*/
	public PoolConfig() {

	}

	/*==========  ==========*/
	public int getMaxConnectionPerHost() {
		return maxConnectionPerHost;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}
}
