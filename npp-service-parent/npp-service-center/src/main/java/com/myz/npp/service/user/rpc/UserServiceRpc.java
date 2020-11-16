package com.myz.npp.service.user.rpc;

import com.myz.common.exception.MyzBizException;
import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.npp.api.user.dto.UserDto;
import com.myz.npp.api.user.dto.UserInsertParam;
import com.myz.npp.api.user.dto.UserUpdateParam;
import com.myz.npp.service.user.dao.model.User;
import com.myz.npp.service.user.service.UserService;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;

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

    /**
     * 根据分片键查询
     * @param userId
     * @return
     */
    @Override
    public ApiResult getByShardingColumn(String userId) {
        User user = userService.getByShardingColumn(userId);
        return ApiResult.OK(user);
    }

    /**
     *
     * @param platformId
     * @return
     */
    @Override
    public ApiResult<UserDto> getByPlatformId(String platformId) {
        User user = userService.getByPlatformId(platformId);
        if (user == null){
            return ApiResult.OK(null);
        }
        UserDto userDto = new UserDto();
        BeanCopier beanCopier = BeanCopier.create(User.class,UserDto.class,false);
        beanCopier.copy(user,userDto,null);

        return ApiResult.OK(userDto);
    }

    /**
     * // TODO 分布式锁，防止重试或者表单重复提交之类的并发
     * @param userInsertParam
     * @return
     */
    @Override
    public ApiResult<Integer> saveUser(UserInsertParam userInsertParam) {
        User user = new User();
        BeanCopier userBeanCopier = BeanCopier.create(UserInsertParam.class, User.class,false);
        userBeanCopier.copy(userInsertParam,user,null);
        try {
            User queriedUser = userService.getByMobile(user.getMobile());
            if (queriedUser!=null){
                // TODO 跨进程了跟Controller不在同一层了，可以用切面了！！
//                throw new MyzBizException("mobile-registered","mobile registered",userInsertParam);
                return ApiResult.build("mobile-registered","mobile="+user.getMobile()+" has bean registered");
            }

            int row = userService.saveUser(user);
            return ApiResult.OK(row);
        }catch (Exception e){
            log.error("saveUser error userInsertParam={},e=", userInsertParam,e);
            return ApiResult.error(null);
        }
    }

    /**
     *
     * @param userUpdateParam
     * @return
     */
    @Override
    public ApiResult<Integer> updateByUserId(UserUpdateParam userUpdateParam) {
        User user = new User();
        BeanCopier beanCopier = BeanCopier.create(UserUpdateParam.class, User.class,false);
        beanCopier.copy(userUpdateParam,user,null);

        try {
            int row = userService.updateByUserId(user);
            return ApiResult.OK(row);
        }catch (Exception e){
            log.error("updateUser error userUpdateParam={},user={},e=", userUpdateParam,user,e);
            return ApiResult.error(null);
        }
    }

    @Override
    public ApiResult<UserDto> getByMobile(String mobile) {
        User user = userService.getByMobile(mobile);
        if (user == null){
            return ApiResult.OK();
        }
        UserDto userDto = new UserDto();
        BeanCopier beanCopier = BeanCopier.create(User.class, UserDto.class,false);
        beanCopier.copy(user,userDto,null);
        return ApiResult.OK(userDto);
    }

    /**
     * RPC传递的都是DTO  里边不会包含passwd的，因此在RPC一层判断用户名和密码
     *                 如果Controller中判断passwd的话，需要RPC接口返回passwd字段，还容易暴露到页面（如果controller层没有处理的话）
     * @param mobile
     * @param passwd
     * @return
     */
    @Override
    public ApiResult<UserDto> loginByPhone(String mobile, String passwd) throws MyzBizException {
        User user = userService.getByMobile(mobile);
        if (user == null){
            throw new MyzBizException("mobile-not-found","mobile not found",mobile,passwd);
        }
        String queriedPasswd = user.getPasswd();
        if (!StringUtils.equals(queriedPasswd,passwd)){
            throw new MyzBizException("passwd-not-match","passwd not match",mobile,passwd);
        }
        UserDto userDto = new UserDto();
        BeanCopier beanCopier = BeanCopier.create(User.class, UserDto.class,false);
        beanCopier.copy(user,userDto,null);
        return ApiResult.OK(userDto);
    }

}
