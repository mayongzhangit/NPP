package com.myz.npp.api.user;

import com.myz.common.util.ApiResult;

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
}
