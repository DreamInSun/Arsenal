package com.cyan.arsenal;

public class Console {

	/*========== Constants ==========*/
	public static int LV_DEBUG = 1;
	public static int LV_INFO = 2;
	public static int LV_WARN = 3;
	public static int LV_ERROR = 4;
	public static int LV_FATAL = 5;

	/*========== Static Properties =========*/
	private static int g_TraceLevel = LV_DEBUG;

	public static void setTraceLevel(int lvl) {
		g_TraceLevel = lvl;
	}

	/*========== Trace Level ==========*/
	public static final void debug(Object obj) {
		if (g_TraceLevel <= LV_DEBUG)
			System.out.println(obj);
	}

	public static final void info(Object obj) {
		if (g_TraceLevel <= LV_INFO)
			System.out.println(obj);
	}

	public static final void warn(Object obj) {
		if (g_TraceLevel <= LV_WARN)
			System.out.println(obj);
	}

	public static final void error(Object obj) {
		if (g_TraceLevel <= LV_ERROR)
			System.out.println(obj);
	}

	public static final void fatal(Object obj) {
		if (g_TraceLevel <= LV_FATAL)
			System.out.println(obj);
	}

}
