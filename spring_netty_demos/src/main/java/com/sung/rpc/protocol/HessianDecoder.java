package com.sung.rpc.protocol;

import java.io.ByteArrayInputStream;

import com.caucho.hessian.io.Hessian2Input;

public class HessianDecoder implements Decoder {
	
	public Object decode(String className,byte[] bytes) throws Exception {
		Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
		Object resultObject = input.readObject();
		input.close();
		return resultObject;
	}

}
