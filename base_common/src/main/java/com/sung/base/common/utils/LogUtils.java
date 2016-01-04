package com.sung.base.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
	public static final Logger log = LoggerFactory.getLogger(LogUtils.class);

	/**
	 * 记录一直 info信息
	 * 
	 * @param message
	 */
	public static void logInfo(String message) {
		StringBuilder s = new StringBuilder();
		s.append((message));
		log.info(s.toString());
	}

	public static void logInfo(String message, Object... arguments) {
		StringBuilder s = new StringBuilder();
		s.append(message);
		log.info(message, arguments);
	}

	public static void logInfo(String message, Throwable e) {
		StringBuilder s = new StringBuilder();
		s.append(("exception : -->>"));
		s.append((message));
		log.info(s.toString(), e);
	}

	public static void logWarn(String message) {
		StringBuilder s = new StringBuilder();
		s.append((message));

		log.warn(s.toString());
	}

	public static void logWarn(String message, Object... arguments) {
		StringBuilder s = new StringBuilder();
		s.append(message);
		log.warn(message, arguments);
	}

	public static void logWarn(String message, Throwable e) {
		StringBuilder s = new StringBuilder();
		s.append(("exception : -->>"));
		s.append((message));
		log.warn(s.toString(), e);
	}

	public static void logDebug(String message) {
		StringBuilder s = new StringBuilder();
		s.append((message));
		log.debug(s.toString());
	}

	public static void logDebug(String message, Object... arguments) {
		StringBuilder s = new StringBuilder();
		s.append(("exception : -->>"));
		s.append((message));
		log.debug(message, arguments);
	}

	public static void logDebug(String message, Throwable e) {
		StringBuilder s = new StringBuilder();
		s.append(("exception : -->>"));
		s.append((message));
		log.debug(s.toString(), e);
	}

	public static void logError(String message) {
		StringBuilder s = new StringBuilder();
		s.append(message);
		log.error(s.toString());
	}

	public static void logError(String message, Object... arguments) {
		StringBuilder s = new StringBuilder();
		s.append(message);
		log.error(message, arguments);
	}

	/**
	 * 记录日志错误信息
	 * 
	 * @param message
	 * @param e
	 */
	public static void logError(String message, Throwable e) {
		StringBuilder s = new StringBuilder();
		s.append(("exception : -->>"));
		s.append((message));
		log.error(s.toString(), e);

	}

	public static boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public static boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public static boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

	public static boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}
}
