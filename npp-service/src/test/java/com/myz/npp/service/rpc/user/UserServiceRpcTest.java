package com.myz.npp.service.rpc.user;

import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.npp.service.NppServiceStartApp;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/2 10:22
 * @email 2641007740@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NppServiceStartApp.class)
public class UserServiceRpcTest {

    @Reference
    private UserServiceApi userServiceApi;

    @Test
    public void getById(){
        Long id =1L;
        ApiResult apiResult = userServiceApi.getUserById(id);
        Assert.notNull(apiResult,"apiResult is null");
    }

}
