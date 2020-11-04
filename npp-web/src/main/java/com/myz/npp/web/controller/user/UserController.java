package com.myz.npp.web.controller.user;

import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import com.myz.starters.login.annotation.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/2 11:00
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/user")
@ParamRetValPrinter
@MyzControllerAdviceAnno
public class UserController {

    @Reference(version = "${dubbo.service.version}")
    private UserServiceApi userServiceApi;

    /**
     * curl http://localhost:8081/user/get/1
     * @param id
     * @return
     */
    @LoginRequired(required = false)
    @GetMapping("/get/{id}")
    public ApiResult get(@PathVariable("id") Long id){
        return userServiceApi.getUserById(id);
    }

    /**
     *
     * @param userId
     * @return
     */
    @LoginRequired(required = false)
    @GetMapping("/get-by-shard-column/{userId}")
    public ApiResult getByShardingColumn(@PathVariable("userId") String userId){
        return userServiceApi.getByShardingColumn(userId);
    }
}
