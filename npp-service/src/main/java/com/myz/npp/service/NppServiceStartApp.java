package com.myz.npp.service;

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
@SpringBootApplication
public class NppServiceStartApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NppServiceStartApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("welcome to Npp Server. app args={}",args);
    }
}
