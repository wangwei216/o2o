package com.imooc.o2o.test;

/*
* 这个是为了能够更好的进行测试Dao接口 的功能
* 也是为了配置spring和junit的整合
* */

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//这个是告诉Junit spring的配置文件的位置所在
@ContextConfiguration("classpath:applicationContext.xml")
public class BaseTest {

    //这个可以不用写，直接让测试的类直接去继承

}
