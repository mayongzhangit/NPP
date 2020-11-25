package com.myz.npp.service.user.service;

import com.myz.npp.service.NppServiceCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/20 10:23
 * @email 2641007740@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NppServiceCenterApp.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getByShardingColumn(){
        String userId = "";
        userService.getByShardingColumn(userId);
    }

}
