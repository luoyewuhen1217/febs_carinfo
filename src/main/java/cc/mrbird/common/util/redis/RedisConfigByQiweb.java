package cc.mrbird.common.util.redis;
//package com.dbgo.framework.core.utils.redis;
/**
 * Created by husky on 2017/7/12.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis 配置文件，用于加载配置信息
 *
 * @author zhangkai
 * @create 2017-07-12 11:16
 **/
@ConfigurationProperties(prefix = "redis")
@Component
public class RedisConfigByQiweb {


    public static Pool pool;
    public static String host;
    public static int port;
    public static int database;
    public static String password;

    // 客户端闲置多长时间后关闭连接
    public static int timeout;
    public static int expireDatetime;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        RedisConfigByQiweb.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        RedisConfigByQiweb.password = password;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getExpireDatetime() {
        return expireDatetime;
    }

    public void setExpireDatetime(int expireDatetime) {
        this.expireDatetime = expireDatetime;
    }

    public static class Pool {

        //控制一个pool可分配多少个jedis实例
        public static int maxActive;
        public static int maxTotal;

        //控制一个pool最多有多少个状态为idle(空闲)的jedis实例
        public static int maxIdle;
        public static int minIdle; //最小

        //表示当borrow一个jedis实例时，最大的等待时间
        public static int maxWaitMillis;
        public static Boolean testOnBorrow;
        public static Boolean testOnReturn;


        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            Pool.minIdle = minIdle;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMaxWaitMillis() {
            return maxWaitMillis;
        }

        public void setMaxWaitMillis(int maxWaitMillis) {
            this.maxWaitMillis = maxWaitMillis;
        }

        public Boolean getTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(Boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public Boolean getTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(Boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }
    }


}
