package com.sung.spring.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisCachedImpl implements ICached {
    public RedisCachedImpl() {

    }

    // -1 - never expire
    private int expire = -1;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String deleteCached(final byte[] sessionId) throws Exception {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.del(sessionId);
                return null;
            }
        });
        return null;
    }

    @Override
    public String updateCached(final byte[] key, final byte[] session, final Long expireSec)
            throws Exception {
        return (String) redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(final RedisConnection connection)
                    throws DataAccessException {
                connection.set(key, session);
                if (expireSec != null) {
                    connection.expire(key, expireSec);
                } else {
                    connection.expire(key, expire);
                }
                return new String(key);
            }
        });

    }

    @Override
    public Object getCached(final byte[] sessionId) throws Exception {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] bs = connection.get(sessionId);
                return SerializeUtil.unserialize(bs);
            }
        });

    }


    @Override
    public Set getKeys(final byte[] keys) throws Exception {
        return redisTemplate.execute(new RedisCallback<Set>() {
            public Set doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Set<byte[]> setByte = connection.keys(keys);
                if (setByte == null || setByte.size() < 1) {
                    return null;
                }
                Set set = new HashSet();
                for (byte[] key : setByte) {
                    byte[] bs = connection.get(key);
                    set.add(SerializeUtil.unserialize(bs));
                }

                return set;

            }
        });
    }


    @Override
    public Set getHashKeys(final byte[] key) throws Exception {
        return (Set) redisTemplate.execute(new RedisCallback<Set>() {
            public Set doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Set<byte[]> hKeys = connection.hKeys(key);
                if (hKeys == null || hKeys.size() > 1) {
                    return null;
                }
                Set set = new HashSet();
                for (byte[] bs : hKeys) {
                    set.add(SerializeUtil.unserialize(bs));
                }
                return set;
            }
        });

    }

    @Override
    public Boolean updateHashCached(final byte[] key, final byte[] mapkey, final byte[] value, Long expire)
            throws Exception {

        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Boolean hSet = connection.hSet(key, mapkey, value);
                return hSet;
            }
        });
    }

    @Override
    public Object getHashCached(final byte[] key, final byte[] mapkey) throws Exception {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] hGet = connection.hGet(key, mapkey);
                return SerializeUtil.unserialize(hGet);

            }
        });
    }


    @Override
    public Long deleteHashCached(final byte[] key, final byte[] mapkey) throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Long hDel = connection.hDel(key, mapkey);
                return hDel;

            }
        });
    }


    @Override
    public Long getHashSize(final byte[] key) throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Long len = connection.hLen(key);

                return len;

            }
        });
    }


    @Override
    public Long getDBSize() throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Long len = connection.dbSize();

                return len;

            }
        });
    }

    @Override
    public void clearDB() throws Exception {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return null;

            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    @Override
    public List getHashValues(final byte[] key) throws Exception {
        return redisTemplate.execute(new RedisCallback<List>() {
            public List doInRedis(RedisConnection connection)
                    throws DataAccessException {
                List<byte[]> hVals = connection.hVals(key);

                if (hVals == null || hVals.size() < 1) {
                    return null;
                }
                List list = new ArrayList();

                for (byte[] bs : hVals) {
                    list.add(SerializeUtil.unserialize(bs));
                }
                return list;

            }
        });
    }


}
