package com.sung.base.common.exception;

/**
 * @author sungang
 */
public class BaseException extends RuntimeException {

	/**
	 * the serialVersionUID
	 */
	private static final long serialVersionUID = 1381325479896057076L;

	/**
	 * message key
	 */
	private String code;

	/**
	 * message params
	 */
	private Object[] values;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the values
	 */
	public Object[] getValues() {
		return values;
	}
	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Object[] values) {
		this.values = values;
	}
	
	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Throwable e) {
		super(e);
	}

	public BaseException(String message, Throwable e) {
		super(message, e);
	}

	public BaseException(String message, Throwable cause, String code, Object[] values) {
		super(message, cause);
		this.code = code;
		this.values = values;
	}
}
