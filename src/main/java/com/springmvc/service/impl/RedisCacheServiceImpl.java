package com.springmvc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.redis.RedisCacheDao;
import com.springmvc.service.RedisCacheService;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    private static final Logger logger = Logger
            .getLogger(RedisCacheServiceImpl.class);
    @Autowired
    private RedisCacheDao redisCacheDao;

    @Override
    public boolean putSessionObject(String id, Object sessionObject) {
        boolean flag = false;
        if (sessionObject != null && id != null && id.trim().length() > 0) {
            flag = redisCacheDao.saveObject(id, sessionObject);
            logger.info("数据缓存标志:" + flag);
        } else {
            logger.info("参数含空值或null，缓存失败" + flag);
        }
        return flag;

    }

    @Override
    public boolean clearSessionObject(String id) {

        boolean flag = false;
        if (id != null && id.trim().length() > 0) {
            flag = redisCacheDao.deleteKey(id);
            logger.info("缓存清空标志:" + flag);
        } else {
            logger.info("参数含空值或null，清空缓存失败");
        }
        return flag;
    }

    @Override
    public Object getsessionObject(String id) {

        Object sessionObject = new Object();
        if (id != null && id.trim().length() > 0) {
            sessionObject = redisCacheDao.getObject(id);
        } else {
            logger.info("参数含空值或null，获取缓存失败");
        }

        return sessionObject;
    }
}
