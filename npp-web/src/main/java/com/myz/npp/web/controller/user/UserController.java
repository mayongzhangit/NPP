package com.myz.npp.web.controller.user;

import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
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
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ApiResult get(@PathVariable("id") Long id){
        return userServiceApi.getUserById(id);
    }
}
