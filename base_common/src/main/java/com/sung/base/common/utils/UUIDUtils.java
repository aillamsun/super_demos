package com.sung.base.common.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getUUIDToUpperCase() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	public static String getUUIDAndReplaceLine() {
		return replaceLine(UUID.randomUUID().toString());
	}

	public static String getUUIDAndReplaceLineToUpperCase() {
		return replaceLine(UUID.randomUUID().toString()).toUpperCase();
	}

	public static long longUuid() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	public static String replaceLine(String str) {
		return str.replaceAll("-", "");
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(longUuid());
		}
	}
}
