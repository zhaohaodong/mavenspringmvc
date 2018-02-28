package com.springmvc.redis.impl;

import com.springmvc.redis.RedisCacheDao;
import com.springmvc.utils.SerializeUtil;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * redis的dao层实现
 *
 * @author Juveniless
 *
 ***/
@Repository("RedisCacheDao")
public class RedisCacheDaoImpl implements RedisCacheDao {
    private static final Logger logger = Logger
            .getLogger(RedisCacheDaoImpl.class.getName());
    static final byte[] ngHisByte = SerializeUtil.serialize("WANDA_NGHIS");
    @Resource
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    /**
     * 根据key删除数据
     *
     * @param key
     *            键
     * @return boolean 删除成功或失败的标志
     ***/
    @Override
    public boolean deleteKey(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = SerializeUtil.serialize(key);
                Boolean flag = false;
                if (connection.hExists(ngHisByte, keyByte)) {
                    logger.info("存在该键，执行删除");
                    flag = connection.hDel(ngHisByte, keyByte);
                }
                return flag;
            }
        });
        return result;
    }

    /**
     * 根据key存储object
     *
     * @param key
     *            键
     * @param value
     *            要存储的对象Object
     * @return boolean 存储成功或失败的标志
     ***/
    @Override
    public boolean saveObject(final String key, final Object value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyByte = SerializeUtil.serialize(key);
                byte[] valueByte = SerializeUtil.serialize(value);
                Boolean flag = false;
                if (connection.hExists(ngHisByte, keyByte)) {
                    logger.info("数据已存在，先删除旧数据");
                    connection.hDel(ngHisByte, keyByte);
                }
                flag = connection.hSet(ngHisByte, keyByte, valueByte);
                return flag;
            }
        });
        return result;
    }

    /**
     * 根据key获取object
     *
     * @param key
     *            键
     * @return Object 与key对应的object
     ***/
    @Override
    public Object getObject(final String key) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Object returnObject = null;

                byte[] keyByte = SerializeUtil.serialize(key);
                if (connection.hExists(ngHisByte, keyByte)) {
                    logger.info("数据存在--开始读取");
                    returnObject = SerializeUtil
                            .unserialize(connection.hGet(ngHisByte, keyByte));
                } else {
                    logger.info("数据不存在");
                }
                return returnObject;
            }
        });

        return result;
    }

}