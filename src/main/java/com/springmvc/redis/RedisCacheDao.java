package com.springmvc.redis;

/**
 * redis的dao层接口
 *
 * @author Juveniless
 *
 * ***/
public interface RedisCacheDao{
    /**
     * 根据key存储object
     *
     * @param key 键
     * @param value 要存储的对象Object
     * @return boolean 存储成功或失败的标志
     * ***/
    public boolean saveObject(String key, Object value);

    /**
     * 根据key获取object
     *
     * @param key 键
     * @return Object 与key对应的object
     * ***/
    public Object getObject(String key);


    /**
     * 根据key删除数据
     *
     * @param key 键
     * @return boolean 删除成功或失败的标志
     * ***/
    public boolean deleteKey(String key);
}