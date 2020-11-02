package com.myz.npp.service.service;

import com.myz.npp.service.NppServiceStartApp;
import com.myz.npp.service.dao.model.User;
import com.myz.npp.service.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/1 1:08 下午
 * @email 2641007740@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NppServiceStartApp.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getById(){
        Long id = 1L;
        User user = userService.getById(id);

    }
}
