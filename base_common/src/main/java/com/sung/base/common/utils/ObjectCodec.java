package com.sung.base.common.utils;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 通过Hessian进行序列化对象
 * @author xiaoshuang
 * @Date   2014-10-23
 *
 */
public class ObjectCodec {
	
	private static final Logger logger = Logger.getLogger(ObjectCodec.class);
	
	/**
	 * 将对象转换成byte数组
	 * @param obj
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream baos = null;
		HessianOutput output = null;
		try {
			baos = new ByteArrayOutputStream(1024);
			output = new HessianOutput(baos);
			output.startCall();
			output.writeObject(obj);
			output.completeCall();
		} catch (final IOException ex) {
			throw ex;
		} finally {
			if (output != null) {
				try {
					baos.close();
				} catch (final IOException ex) {
					logger.error("Failed to close stream.", ex);
				}
			}
		}
		return baos != null ? baos.toByteArray() : null;
	}

	/**
	 * 将byte数组转换为对象
	 * @param in
	 * @return
	 * @throws java.io.IOException
	 */
	public static Object deSerialize(byte[] in) throws IOException {
		Object obj = null;
		ByteArrayInputStream bais = null;
		HessianInput input = null;
		try {
			bais = new ByteArrayInputStream(in);
			input = new HessianInput(bais);
			input.startReply();
			obj = input.readObject();
			input.completeReply();
		} catch (final IOException ex) {
			throw ex;
		} catch (final Throwable e) {
			logger.error("Failed to decode object.", e);
		} finally {
			if (input != null) {
				try {
					bais.close();
				} catch (final IOException ex) {
					logger.error("Failed to close stream.", ex);
                }
            }
        }
        return obj;
	}
}
