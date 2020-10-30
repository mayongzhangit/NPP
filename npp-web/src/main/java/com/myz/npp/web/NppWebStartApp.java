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
    }
}
