package com.myz.npp.service.rpc.user;

import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.npp.service.dao.user.model.User;
import com.myz.npp.service.service.user.UserService;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/31 17:58
 * @email 2641007740@qq.com
 */
@Slf4j
@Service
@ParamRetValPrinter
public class UserServiceRpc implements UserServiceApi {

    @Autowired
    private UserService userService;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ApiResult getUserById(Long id){
        User user = userService.getById(id);
        return ApiResult.OK(user);
    }

}
