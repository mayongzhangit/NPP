package com.myz.npp.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 11:17 PM
 * @email 2641007740@qq.com
 */
@Slf4j
@SpringBootApplication
public class NppWebStartApp implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(NppWebStartApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("welcome to Npp Web. app args={}",args);
        log.info("************************************************");
        log.info("①引入入参出参组件aspect-spring-boot-starter 【类或方法添加@ParamRetValPrinter注解】【logback-spring.xml配置log name为PARAM-RETVAL-PRINTER的logger和appender】");
        log.info("②引入spring-boot-actuator组件，配置actuator management 暴露logger端点");
        log.info("③遇到问题或想看入参出参，执行curl -XPOST -H \"Content-type:application/json;\"  http://localhost/api/actuator/loggers/PARAM-RETVAL-PRINTER?configuredLevel=DEBUG 修改日志级别");
        log.info("④dubbo consumer check false");
        log.info("************************************************");
    }
}
