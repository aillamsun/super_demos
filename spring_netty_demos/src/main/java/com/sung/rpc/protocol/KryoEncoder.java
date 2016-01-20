package com.sung.rpc.protocol;

import com.esotericsoftware.kryo.io.Output;

public class KryoEncoder implements Encoder {
	/**
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] encode(Object object) throws Exception {
		Output output = new Output(256);
		KryoUtils.getKryo().writeClassAndObject(output, object);
		return output.toBytes();
	}

}
