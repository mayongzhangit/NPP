package com.myz.npp.operation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 11:15 PM
 * @email 2641007740@qq.com
 */
@Slf4j
@SpringBootConfiguration
public class NppOperationStartApp implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(NppOperationStartApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("welcome to Npp Operation. app args={}",args);
    }
}
