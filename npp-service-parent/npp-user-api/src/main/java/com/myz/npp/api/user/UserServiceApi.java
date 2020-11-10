package com.myz.npp.api.user;

import com.myz.common.exception.MyzBizException;
import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.dto.UserDto;
import com.myz.npp.api.user.dto.UserInsertParam;
import com.myz.npp.api.user.dto.UserUpdateParam;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/2 10:26
 * @email 2641007740@qq.com
 */
public interface UserServiceApi {

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    ApiResult getUserById(Long id);

    /**
     * 根据分片键查询
     * @param userId
     * @return
     */
    ApiResult getByShardingColumn(String userId);

    /**
     * 根据平台Id查询用户信息
     * @param platformId
     * @return
     */
    ApiResult<UserDto> getByPlatformId(String platformId);


    /**
     *
     * @param userInsertParam
     * @return
     */
    ApiResult<Integer> saveUser(UserInsertParam userInsertParam);


    /**
     *
     * @param userUpdateParam
     * @return
     */
    ApiResult<Integer> updateByUserId(UserUpdateParam userUpdateParam);


    /**
     * 更具手机号查询，
     * 不按照分片键查询，需要查询映射表，查不到走多个库查询（因为映射表的数据会过期会删除一部分）
     * @param mobile
     * @return
     */
    ApiResult<UserDto> getByMobile(String mobile);

    /**
     * 根据手机号登录
     * @param mobile
     * @param passwd
     * @return
     */
    ApiResult<UserDto> loginByPhone(String mobile,String passwd) throws MyzBizException;
}
