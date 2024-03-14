package com.github.legionivo.testtask.utils;

public final class OsUtils {

	private static String OS = null;

	static String getOsName() {
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
		return OS;
	}

	public static boolean isWindows() {
		return getOsName().toLowerCase().contains("win");
	}

	public static boolean isUnix() {
		return getOsName().toLowerCase().contains("darwin");
	}

}
