package cc.mrbird.common.util.redis;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis池管理器
 */
public class RedisPoolManager {


    private static final String DEFAULT_MAX_TOTAL = "512";
    private static final String DEFAULT_MAX_IDLE = "512";
    private static final String DEFAULT_MIN_IDLE = "8";
    private static final String DEFAULT_MAX_WAIT_MILLIS = "1000";
    private static final String DEFAULT_TEST_ON_BORROW = "false";
    private static final String DEFAULT_TEST_ON_RETURN = "false";
    private static final String DEFAULT_TIMEOUT = "2000";
    private static final String DEFAULT_DATABASE = "0";

    private static JedisPool jedisPool = null;
    private static final Logger logger = LoggerFactory.getLogger(RedisPoolManager.class);
    //private static final Logger logger = Logger.getLogger(RedisPoolManager.class);

    public RedisPoolManager() {
        if (jedisPool == null) {
            poolInit();
        }
    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }

    /**
     * 初始化RedisPool
     */
    private static void initialPool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(RedisConfigByQiweb.Pool.maxTotal);
        poolConfig.setMaxIdle(RedisConfigByQiweb.Pool.maxIdle);
        poolConfig.setMinIdle(RedisConfigByQiweb.Pool.minIdle);
        poolConfig.setMaxWaitMillis(RedisConfigByQiweb.Pool.maxWaitMillis);
        poolConfig.setTestOnBorrow(RedisConfigByQiweb.Pool.testOnBorrow);
        poolConfig.setTestOnReturn(RedisConfigByQiweb.Pool.testOnReturn);
        jedisPool = new JedisPool(poolConfig, RedisConfigByQiweb.host, RedisConfigByQiweb.port, RedisConfigByQiweb.timeout
                , RedisConfigByQiweb.password, RedisConfigByQiweb.database, null);
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis createInstance() {

        try{
            if (jedisPool == null) {
                poolInit();
            }
            return jedisPool.getResource();
        }catch(Exception e){
            logger.error("创建redis实例异常："+e.getMessage());
        }
        return null;
    }

    /**
     * 释放Jedis实例
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {

        try{
            if (jedisPool == null) {
                poolInit();
            }
            jedisPool.returnResource(jedis);
        }catch(Exception e){
            logger.error("释放Jedis实例异常:"+e.getMessage());
        }

    }

    /**
     * 释放Jedis实例
     *
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {

        try{
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
        }catch(Exception e){
            logger.error("释放Jedis实例异常:"+e.getMessage());
        }

    }


}
