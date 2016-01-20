package com.sung.rpc;


import com.sung.rpc.annotation.Codecs;

public class ResponseWrapper {

	private int requestId = 0;
	
	private Object response = null;
	
	private boolean isError = false;
	
	private Throwable exception = null;
	
	private int codecType = Codecs.JAVA_CODEC.ordinal();
	
	private int messageLen;
	
	private byte[] responseClassName;

	public ResponseWrapper(int requestId,int codecType){
		this.requestId = requestId;
		this.codecType = codecType;
	}
	
	public int getMessageLen() {
		return messageLen;
	}

	public void setMessageLen(int messageLen) {
		this.messageLen = messageLen;
	}

	public int getCodecType() {
		return codecType;
	}

	public int getRequestId() {
		return requestId;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public boolean isError() {
		return isError;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
		isError = true;
	}
	
	public byte[] getResponseClassName() {
		return responseClassName;
	}

	public void setResponseClassName(byte[] responseClassName) {
		this.responseClassName = responseClassName;
	}
	
}