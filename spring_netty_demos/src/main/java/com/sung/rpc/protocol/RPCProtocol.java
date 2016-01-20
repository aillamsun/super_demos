package com.sung.rpc.protocol;

import java.util.ArrayList;
import java.util.List;

import com.sung.rpc.RequestWrapper;
import com.sung.rpc.ResponseWrapper;
import com.sung.rpc.annotation.Codecs;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Common RPC Protocol
 * 
 *  Request Protocol
 * 	VERSION(1B):   
 *  TYPE(1B):      request/response 
 *  CODECTYPE(1B):  serialize/deserialize type
 *  KEEPED(1B):    
 *  KEEPED(1B):    
 *  KEEPED(1B):    
 *  ID(4B):        request id
 *  TIMEOUT(4B):   request timeout
 *  TARGETINSTANCELEN(4B):  target service name length
 *  METHODNAMELEN(4B):      method name length
 *  ARGSCOUNT(4B):          method args count
 *  ARG1TYPELEN(4B):        method arg1 type len
 *  ARG2TYPELEN(4B):        method arg2 type len
 *  ...
 *  ARG1LEN(4B):            method arg1 len
 *  ARG2LEN(4B):            method arg2 len
 *  ...
 *  TARGETINSTANCENAME
 *  METHODNAME
 *  ARG1TYPENAME
 *  ARG2TYPENAME
 *  ...
 *  ARG1
 *  ARG2
 *  ...
 * 
 *  Response Protocol
 *  VERSION(1B):   
 *  TYPE(1B):      request/response 
 *  DATATYPE(1B):  serialize/deserialize type
 *  KEEPED(1B):    
 *  KEEPED(1B):    
 *  KEEPED(1B):    
 *  ID(4B):        request id
 *  BodyClassNameLen(4B): body className Len
 *  LENGTH(4B):    body length
 *  BodyClassName
 *  BODY if need than set
 *  
 */
public class RPCProtocol implements Protocol {
	
	private static final Log LOGGER = LogFactory.getLog(RPCProtocol.class);
	
	private static final int REQUEST_HEADER_LEN = 1 * 6 + 5 * 4;
	
	private static final int RESPONSE_HEADER_LEN = 1 * 6 + 2 * 4;
	
	private static final byte VERSION = (byte)1;
	
	private static final byte REQUEST = (byte)0;
	
	private static final byte RESPONSE = (byte)1;
	
	/**
	 * 客户端请求响应编码、服务器端响应编码
	 * 
	 */
	public ByteBufferWrapper encode(Object message,ByteBufferWrapper bytebufferWrapper) throws Exception{
		if(!(message instanceof RequestWrapper) && !(message instanceof ResponseWrapper)){
			throw new Exception("only support send RequestWrapper && ResponseWrapper");
		}
		int id = 0;
		byte type = REQUEST;
		if(message instanceof RequestWrapper) {
			try{
				int requestArgTypesLen = 0;
				int requestArgsLen = 0;
				List<byte[]> requestArgTypes = new ArrayList<byte[]>();
				List<byte[]> requestArgs = new ArrayList<byte[]>();
				RequestWrapper wrapper = (RequestWrapper) message;
				byte[][] requestArgTypeStrings = wrapper.getArgTypes();
				for (byte[] requestArgType : requestArgTypeStrings) {
					requestArgTypes.add(requestArgType);
					requestArgTypesLen += requestArgType.length;
				}
				Object[] requestObjects = wrapper.getRequestObjects();
				if(requestObjects!=null){
					for (Object requestArg : requestObjects) {
						byte[] requestArgByte = Codecs.getEncoder(wrapper.getCodecType()).encode(requestArg);
						requestArgs.add(requestArgByte);
						requestArgsLen += requestArgByte.length;
					}
				}
				byte[] targetInstanceNameByte = wrapper.getTargetInstanceName();
				byte[] methodNameByte = wrapper.getMethodName();
				id = wrapper.getId();
				int timeout = wrapper.getTimeout();
				int capacity = REQUEST_HEADER_LEN + requestArgs.size() * 4 * 2 + targetInstanceNameByte.length 
							   + methodNameByte.length + requestArgTypesLen + requestArgsLen;
				ByteBufferWrapper byteBuffer = bytebufferWrapper.get(capacity);
				byteBuffer.writeByte(VERSION);
				byteBuffer.writeByte(type);
				byteBuffer.writeByte((byte)wrapper.getCodecType());
				byteBuffer.writeByte((byte)0);
				byteBuffer.writeByte((byte)0);
				byteBuffer.writeByte((byte)0);
				byteBuffer.writeInt(id);
				byteBuffer.writeInt(timeout);
				byteBuffer.writeInt(targetInstanceNameByte.length);
				byteBuffer.writeInt(methodNameByte.length);
				byteBuffer.writeInt(requestArgs.size());
				for (byte[] requestArgType : requestArgTypes) {
					byteBuffer.writeInt(requestArgType.length);
				}
				for (byte[] requestArg : requestArgs) {
					byteBuffer.writeInt(requestArg.length);
				}
				byteBuffer.writeBytes(targetInstanceNameByte);
				byteBuffer.writeBytes(methodNameByte);
				for (byte[] requestArgType : requestArgTypes) {
					byteBuffer.writeBytes(requestArgType);
				}
				for (byte[] requestArg : requestArgs) {
					byteBuffer.writeBytes(requestArg);
				}
				return byteBuffer;
			}
			catch(Exception e){
				LOGGER.error("encode request object error",e);
				throw e;
			}
		}
		else{
			ResponseWrapper wrapper = (ResponseWrapper) message;
			byte[] body = new byte[0];
			byte[] className = new byte[0];
			try{
				// no return object
				if(wrapper.getResponse() != null){
					className = wrapper.getResponse().getClass().getName().getBytes();
					body = Codecs.getEncoder(wrapper.getCodecType()).encode(wrapper.getResponse());
				}
				if(wrapper.isError()){
					className = wrapper.getException().getClass().getName().getBytes();
					body = Codecs.getEncoder(wrapper.getCodecType()).encode(wrapper.getException());
				}
				id = wrapper.getRequestId();
			}
			catch(Exception e){
				LOGGER.error("encode response object error", e);
				// still create responsewrapper,so client can get exception
				wrapper.setResponse(new Exception("serialize response object error",e));
				className = Exception.class.getName().getBytes();
				body = Codecs.getEncoder(wrapper.getCodecType()).encode(wrapper.getResponse());
			}
			type = RESPONSE;
			int capacity = RESPONSE_HEADER_LEN + body.length;
			ByteBufferWrapper byteBuffer = bytebufferWrapper.get(capacity);
			byteBuffer.writeByte(VERSION);
			byteBuffer.writeByte(type);
			byteBuffer.writeByte((byte)wrapper.getCodecType());
			byteBuffer.writeByte((byte)0);
			byteBuffer.writeByte((byte)0);
			byteBuffer.writeByte((byte)0);
			byteBuffer.writeInt(id);
			byteBuffer.writeInt(className.length);
			byteBuffer.writeInt(body.length);
			byteBuffer.writeBytes(className);
			byteBuffer.writeBytes(body);
			return byteBuffer;
		}
	}
	
	
	/**
	 * 服务器请求解码、客户端响应解码
	 * 
	 */
	public Object decode(ByteBufferWrapper wrapper, Object errorObject) throws Exception{
		int originPos = wrapper.readerIndex();
		
		if(wrapper.readableBytes() < 2){
			wrapper.setReaderIndex(originPos);
        	return errorObject;
        }
		
        byte version = wrapper.readByte();
        if(version == (byte)1){
        	byte type = wrapper.readByte();
        	if(type == REQUEST){
        		if(wrapper.readableBytes() < REQUEST_HEADER_LEN -2){
        			wrapper.setReaderIndex(originPos);
        			return errorObject;
        		}
        		int codecType = wrapper.readByte();
        		wrapper.readByte();
        		wrapper.readByte();
        		wrapper.readByte();
        		int requestId = wrapper.readInt();
        		int timeout = wrapper.readInt();
        		int targetInstanceLen = wrapper.readInt();
        		int methodNameLen = wrapper.readInt();
        		int argsCount = wrapper.readInt();
        		int argInfosLen = argsCount * 4 * 2;
        		int expectedLenInfoLen = argInfosLen + targetInstanceLen + methodNameLen;
        		if(wrapper.readableBytes() < expectedLenInfoLen){
        			wrapper.setReaderIndex(originPos);
        			return errorObject;
        		}
        		int expectedLen = 0;
        		int[] argsTypeLen = new int[argsCount];
        		for (int i = 0; i < argsCount; i++) {
					argsTypeLen[i] = wrapper.readInt();
					expectedLen += argsTypeLen[i]; 
				}
        		int[] argsLen = new int[argsCount];
        		for (int i = 0; i < argsCount; i++) {
        			argsLen[i] = wrapper.readInt();
        			expectedLen += argsLen[i];
				}
        		byte[] targetInstanceByte = new byte[targetInstanceLen];
        		wrapper.readBytes(targetInstanceByte);
        		byte[] methodNameByte = new byte[methodNameLen];
        		wrapper.readBytes(methodNameByte);
        		if(wrapper.readableBytes() < expectedLen){
        			wrapper.setReaderIndex(originPos);
        			return errorObject;
        		}
        		byte[][] argTypes = new byte[argsCount][];
        		for (int i = 0; i < argsCount; i++) {
					byte[] argTypeByte = new byte[argsTypeLen[i]];
					wrapper.readBytes(argTypeByte);
					argTypes[i] = argTypeByte;
				}
        		Object[] args = new Object[argsCount];
        		for (int i = 0; i < argsCount; i++) {
					byte[] argByte = new byte[argsLen[i]];
					wrapper.readBytes(argByte);
					args[i] = argByte;
				}
        		RequestWrapper requestWrapper = new RequestWrapper(targetInstanceByte, methodNameByte, 
        														   argTypes, args, timeout, requestId, codecType);
        		int messageLen = REQUEST_HEADER_LEN + expectedLenInfoLen + expectedLen;
        		requestWrapper.setMessageLen(messageLen);
        		return requestWrapper;
        	}
        	else if(type == RESPONSE){
        		if(wrapper.readableBytes() < RESPONSE_HEADER_LEN -2){
        			wrapper.setReaderIndex(originPos);
        			return errorObject;
        		}
        		int codecType = wrapper.readByte();
        		wrapper.readByte();
        		wrapper.readByte();
        		wrapper.readByte();
            	int requestId = wrapper.readInt();
            	int classNameLen = wrapper.readInt();
            	int bodyLen = wrapper.readInt();
            	if(wrapper.readableBytes() < classNameLen + bodyLen){
            		wrapper.setReaderIndex(originPos);
            		return errorObject;
            	}
            	
            	byte[] classNameBytes = null;
            	classNameBytes = new byte[classNameLen];
            	wrapper.readBytes(classNameBytes);

            	byte[] bodyBytes = new byte[bodyLen];
            	wrapper.readBytes(bodyBytes);
            	ResponseWrapper responseWrapper = new ResponseWrapper(requestId,codecType);
            	responseWrapper.setResponse(bodyBytes);
            	responseWrapper.setResponseClassName(classNameBytes);
	        	int messageLen = RESPONSE_HEADER_LEN + classNameLen + bodyLen;
	        	responseWrapper.setMessageLen(messageLen);
            	return responseWrapper;
        	}
        	else{
        		throw new UnsupportedOperationException("protocol type : "+type+" is not supported!");
        	}
        }
        else{
        	throw new UnsupportedOperationException("protocol version :"+version+" is not supported!");
        }
	}

}
