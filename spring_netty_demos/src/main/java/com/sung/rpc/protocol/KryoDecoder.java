package com.sung.rpc.protocol;

import com.esotericsoftware.kryo.io.Input;

public class KryoDecoder implements Decoder {
	/**
	 * @param className
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object decode(String className, byte[] bytes) throws Exception {
		Input input = new Input(bytes);
		return KryoUtils.getKryo().readClassAndObject(input);
	}
}
