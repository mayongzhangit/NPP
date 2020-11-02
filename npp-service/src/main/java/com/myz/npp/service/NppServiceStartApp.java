package com.myz.npp.service;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("com.myz.npp.service.dao.mapper")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class NppServiceStartApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NppServiceStartApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("************************************************");
        log.info("welcome to Npp Server. app args={}",args);
        log.info("①引入入参出参组件aspect-spring-boot-starter 【类或方法添加@ParamRetValPrinter注解】【logback-spring.xml配置log name为PARAM-RETVAL-PRINTER的logger和appender】");
        log.info("②遇到问题或想看入参出参，执行curl -XPOST -H \"Content-type:application/json;\"  http://localhost/api/actuator/loggers/PARAM-RETVAL-PRINTER?configuredLevel=DEBUG 修改日志级别");
        log.info("③dubbo provider scan");
        log.info("④DruidStarter跟ShardingJdbcStarter 都是AutoConfigurationBefore于DataSourceAutoConfiguration但是，DruidStarter创建数据源更早，但是没有spring.datasource的配置，所以druid报错，我们直接将DruidStarterexclude");
        log.info("************************************************");
    }
}
