package com.sung.rpc.netty4.serialize;
import com.sung.rpc.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * decode byte[]
 * 	change to pipeline receive requests or responses,let's IO thread do less thing
 * 
 * @author <a href="mailto:coderplay@gmail.com">Min Zhou</a>
 */
public class Netty4ProtocolDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		Netty4ByteBufferWrapper wrapper = new Netty4ByteBufferWrapper(in);
		Object msg = ProtocolUtils.decode(wrapper, null);
		if (msg != null)
			out.add(msg);
	}

}
