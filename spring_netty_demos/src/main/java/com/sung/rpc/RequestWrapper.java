package com.sung.rpc;


import com.sung.rpc.annotation.Codecs;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestWrapper {

    private static AtomicInteger incId = new AtomicInteger(0);

    private byte[] targetInstanceName;

    private byte[] methodName;

    private byte[][] argTypes;

    private Object[] requestObjects = null;

    private Object message = null;

    private int timeout = 0;

    private int id = 0;

    private int codecType = Codecs.JAVA_CODEC.ordinal();

    private int messageLen;

    public RequestWrapper(Object message, int timeout, int codecType) {
        this(message, timeout, incId.incrementAndGet(), codecType);
    }

    public RequestWrapper(Object message, int timeout, int id, int codecType) {
        this.message = message;
        this.id = id;
        this.timeout = timeout;
        this.codecType = codecType;
    }

    public RequestWrapper(byte[] targetInstanceName, byte[] methodName, byte[][] argTypes,
                          Object[] requestObjects, int timeout, int codecType) {
        this(targetInstanceName, methodName, argTypes, requestObjects, timeout, incId.incrementAndGet(), codecType);
    }

    public RequestWrapper(byte[] targetInstanceName, byte[] methodName, byte[][] argTypes,
                          Object[] requestObjects, int timeout, int id, int codecType) {
        this.requestObjects = requestObjects;
        this.id = id;
        this.timeout = timeout;
        this.targetInstanceName = targetInstanceName;
        this.methodName = methodName;
        this.argTypes = argTypes;
        this.codecType = codecType;
    }

    public int getMessageLen() {
        return messageLen;
    }

    public void setMessageLen(int messageLen) {
        this.messageLen = messageLen;
    }

    public void setArgTypes(byte[][] argTypes) {
        this.argTypes = argTypes;
    }

    public int getCodecType() {
        return codecType;
    }

    public Object getMessage() {
        return message;
    }

    public byte[] getTargetInstanceName() {
        return targetInstanceName;
    }

    public byte[] getMethodName() {
        return methodName;
    }

    public int getTimeout() {
        return timeout;
    }

    public Object[] getRequestObjects() {
        return requestObjects;
    }

    public int getId() {
        return id;
    }

    public byte[][] getArgTypes() {
        return argTypes;
    }

}
