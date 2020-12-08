package com.myz.npp.service.accountbook.service;

import com.myz.npp.service.NppServiceCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.text.ParseException;

/**
 * @author yzMa
 * @desc
 * @date 2020/12/4 16:13
 * @email 2641007740@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NppServiceCenterApp.class)
public class AccountBookServiceTest {

    @Autowired
    private AccountBookService accountBookService;

    @Test
    public void bookkeepingDaily() throws ParseException {
        String userId = "7366348057481216";
        int typeId = 1;
        int amount = 10;

        int save = accountBookService.bookkeepingDaily(userId, typeId, amount);
        Assert.isTrue(save == 1,"影响行数不是1");
    }
}
