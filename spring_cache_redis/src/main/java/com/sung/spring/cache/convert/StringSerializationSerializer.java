package com.sung.spring.cache.convert;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Created by sungang on 2016/1/4.17:40
 */
public class StringSerializationSerializer implements RedisSerializer<String> {

    public byte[] serialize(String t)
            throws SerializationException {
        return t.getBytes(JsonsmartSerializationRedisSerializer.DEFAULT_CHARSET);
    }

    public String deserialize(byte[] bytes) throws SerializationException {
        return new String(bytes, JsonsmartSerializationRedisSerializer.DEFAULT_CHARSET);
    }

}
