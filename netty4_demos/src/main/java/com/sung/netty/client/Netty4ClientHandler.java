/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.sung.netty.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Create on @2013-8-24 @下午10:31:29 
 * @author bsli@ustcinfo.com
 */
@Sharable
public class Netty4ClientHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger logger = LoggerFactory.getLogger(Netty4ClientHandler.class);
    
    private final LinkedBlockingQueue<String> queue;

    public Netty4ClientHandler() {
    	queue = new LinkedBlockingQueue<String>();
	}

	/**
	 * 接收Server 响应数据
	 * @param ctx
	 * @param message
	 * @throws Exception
	 */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
    	queue.add(message);
    }

//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		while (true){
//			ctx.write("Send Msg to Server...\r\n");
//			ctx.flush();
//			TimeUnit.SECONDS.sleep(2);
//		}
//	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("Unexpected exception from downstream.", cause);
        ctx.close();
    }


    public String getMessage() {
		String message = null;
		try {
			message = queue.poll(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		
		return message;
	}
}