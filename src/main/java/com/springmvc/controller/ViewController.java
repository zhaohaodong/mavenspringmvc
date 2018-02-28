package com.springmvc.controller;

/**
 * Created by dell on 2018/2/28.
 */
import com.springmvc.service.RedisCacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author : zw
 * @Date : 11:27 2017/5/5
 * @Remark :
 **/
@Controller
@RequestMapping("/view")
public class ViewController {

    private static final Logger logger = Logger.getLogger(ViewController.class);


    @Autowired
    private RedisCacheService redisCacheService;

    @RequestMapping("")
    public String view() {
        logger.info("你已通过springMVC进入controller方法");
        logger.info("-------存储数据---------");
        redisCacheService.putSessionObject("123","wwwwwww");
       /* logger.info("-------读取数据---------");
        redisCacheService.getsessionObject("123");
        logger.info("-------删除数据---------");
        redisCacheService.clearSessionObject("123");
        logger.info("-------删除后再次读取数据---------");
        redisCacheService.getsessionObject("123");*/
        return "index";
    }

}
