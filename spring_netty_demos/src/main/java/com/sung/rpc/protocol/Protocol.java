package com.sung.rpc.protocol;

public interface Protocol {
	
	/**
	 * encode Message to byte & write to network framework
	 * 
	 * @param message
	 * @param byteBuffer
	 * @throws Exception
	 */
	public ByteBufferWrapper encode(Object message, ByteBufferWrapper bytebufferWrapper) throws Exception;

	/**
	 * decode stream to object
	 * 
	 * @param wrapper
	 * @param errorObject stream not enough,then return this object
	 * @return Object 
	 * @throws Exception
	 */
	public Object decode(ByteBufferWrapper wrapper, Object errorObject) throws Exception;

}