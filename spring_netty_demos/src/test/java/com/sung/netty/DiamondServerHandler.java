package com.sung.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Sharable
public class DiamondServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = Logger.getLogger(DiamondServerHandler.class.getName());
    
    

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        while(true) {
        	ctx.write("hello world!\r\n");
            ctx.flush();
            
            TimeUnit.SECONDS.sleep(3);
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.", cause);
        ctx.close();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	super.channelInactive(ctx);
    }
}