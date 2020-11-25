package com.myz.npp.api.test;

import com.myz.npp.web.NppWebApp;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/24 17:34
 * @email 2641007740@qq.com
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NppWebApp.class)
public class TestServiceTest {

    @Reference
    private TestServiceApi testServiceApi;


}
