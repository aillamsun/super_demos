package com.sung.rpc.netty4;

import com.sung.rpc.RequestWrapper;
import com.sung.rpc.ResponseWrapper;
import com.sung.rpc.protocol.ProtocolUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Netty4ServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final Log LOGGER = LogFactory
			.getLog(Netty4ServerHandler.class);

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		if(!(cause instanceof IOException)){
			// only log
			LOGGER.error("catch some exception not IOException", cause);
		}
    }
  
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		RequestWrapper request = (RequestWrapper) msg;
		long beginTime = System.currentTimeMillis();
		ResponseWrapper responseWrapper = ProtocolUtils.getServerHandler().handleRequest(request);
		final int id = request.getId();
		// already timeout,so not return
		if ((System.currentTimeMillis() - beginTime) >= request.getTimeout()) {
			LOGGER.warn("timeout,so give up send response to client,requestId is:"
					+ id
					+ ",client is:"
					+ ctx.channel().remoteAddress()
					+ ",consumetime is:"
					+ (System.currentTimeMillis() - beginTime)
					+ ",timeout is:"
					+ request.getTimeout());
			return;
		}
		ChannelFuture wf = ctx.channel().writeAndFlush(responseWrapper);
		wf.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (!future.isSuccess()) {
					LOGGER.error("server write response error,request id is: " + id);
				}
			}
		});
	}

}
