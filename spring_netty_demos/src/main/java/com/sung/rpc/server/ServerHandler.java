package com.sung.rpc.server;

import com.sung.rpc.RequestWrapper;
import com.sung.rpc.ResponseWrapper;

public interface ServerHandler {

    /**
     * register business handler,provide for Server
     */
    public void registerProcessor(String instanceName, Object instance);

    /**
     * handle the request
     */
    public ResponseWrapper handleRequest(final RequestWrapper request);

}