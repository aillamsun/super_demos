package com.sung.spring.cache;

import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.ExceptionTranslationStrategy;
import org.springframework.data.redis.PassThroughExceptionTranslationStrategy;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
/**
 * Created by sungang on 2016/1/5.15:26
 */
public class ShardedJedisConnectionFactory implements InitializingBean, DisposableBean, RedisConnectionFactory {

    private static final Logger log = LoggerFactory.getLogger(ShardedJedisConnectionFactory.class);

    private static final ExceptionTranslationStrategy EXCEPTION_TRANSLATION = new PassThroughExceptionTranslationStrategy(
            JedisConverters.exceptionConverter());


    private String applicationName;
    private JedisShardInfo shardInfo;
    private GenericObjectPoolConfig poolConfig;
    private List<String> masters;
    private Set<String> sentinels;
    private String hostName = "localhost";
    private int port = 6379;
    private int timeout = 2000;
    private String password;
    private ShardedJedisSentinelPool pool = null;
    private int dbIndex = 0;
    private boolean convertPipelineAndTxResults = true;
    private ThreadLocal<Jedis> jedisLocal = new ThreadLocal();
    private ThreadLocal<byte[]> keyLocal = new ThreadLocal();


    public ShardedJedisConnectionFactory() {
    }


    public ShardedJedisConnectionFactory(JedisShardInfo shardInfo) {
        this.shardInfo = shardInfo;
    }


    protected Jedis fetchJedisConnector(){return null;}


    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.shardInfo == null) {
            this.shardInfo = new JedisShardInfo(this.hostName, this.port);
        }
        if (StringUtils.hasLength(this.password)) {
            this.shardInfo.setPassword(this.password);
        }
        if (this.timeout > 0) {
            this.shardInfo.setSoTimeout(timeout);
        }

        if (this.pool == null){
            this.pool = new ShardedJedisSentinelPool(this.masters,this.sentinels,this.shardInfo.getSoTimeout(),this.shardInfo.getPassword());

        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.pool != null) {
            try {
                this.pool.destroy();
            } catch (Exception ex) {
                log.warn("Cannot properly close Jedis pool", ex);
            }
            this.pool = null;
        }
    }


    @Override
    public RedisConnection getConnection() {
        return null;
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return false;
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return null;
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException e) {
        return EXCEPTION_TRANSLATION.translate(e);
    }


    public Set<String> getSentinels() {
        return sentinels;
    }

    public void setSentinels(Set<String> sentinels) {
        this.sentinels = sentinels;
    }

    public List<String> getMasters() {
        return masters;
    }

    public void setMasters(List<String> masters) {
        this.masters = masters;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public JedisShardInfo getShardInfo() {
        return shardInfo;
    }

    public void setShardInfo(JedisShardInfo shardInfo) {
        this.shardInfo = shardInfo;
    }

    public GenericObjectPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public ShardedJedisSentinelPool getPool() {
        return pool;
    }

    public void setPool(ShardedJedisSentinelPool pool) {
        this.pool = pool;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public boolean isConvertPipelineAndTxResults() {
        return convertPipelineAndTxResults;
    }

    public void setConvertPipelineAndTxResults(boolean convertPipelineAndTxResults) {
        this.convertPipelineAndTxResults = convertPipelineAndTxResults;
    }

    public ThreadLocal<Jedis> getJedisLocal() {
        return jedisLocal;
    }

    public void setJedisLocal(ThreadLocal<Jedis> jedisLocal) {
        this.jedisLocal = jedisLocal;
    }

    public ThreadLocal<byte[]> getKeyLocal() {
        return keyLocal;
    }

    public void setKeyLocal(ThreadLocal<byte[]> keyLocal) {
        this.keyLocal = keyLocal;
    }
}
