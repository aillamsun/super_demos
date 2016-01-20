package com.sung.spring.cache.convert;

import com.google.gson.Gson;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Created by sungang on 2016/1/4.17:36
 */
public class JsonsmartSerializationRedisSerializer implements RedisSerializer<Object> {

    static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final Gson gson = new Gson();

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if ((bytes == null) || (bytes.length == 0)) {
            return null;
        }
        try {
            return new String(bytes, DEFAULT_CHARSET);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        {
            if (object == null)
                return new byte[0];
            try {
                if ((object instanceof String)) {
                    return ((String) object).getBytes(DEFAULT_CHARSET);
                }
                return gson.toJson(object).getBytes(DEFAULT_CHARSET);
            } catch (Exception ex) {
                throw new SerializationException("Cannot serialize", ex);
            }
        }
    }
}
