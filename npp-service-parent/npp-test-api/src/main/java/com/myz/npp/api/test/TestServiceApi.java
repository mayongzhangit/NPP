package com.myz.npp.api.test;

import com.myz.common.util.ApiResult;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/24 17:08
 * @email 2641007740@qq.com
 */
public interface TestServiceApi {

    /**
     * 消费方和提供方都不设置
     * @param timeCostMs
     * @return
     */
    ApiResult defaultTimeout(Long timeCostMs);

    /**
     * 只有提供方设置超时
     * @param timeCostMs
     * @return
     */
    ApiResult providerTimeout(Long timeCostMs);

    /**
     * 只有消费方设置超时时间
     * @param timeCostMs
     * @return
     */
    ApiResult consumerTimeout(Long timeCostMs);

    /**
     * 消费方和提供能都设置超时
     * @param timeCostMs
     * @return
     */
    ApiResult timeout(Long timeCostMs);

    /**
     * 测试方法内部抛出自定义异常
     * @return
     */
    ApiResult throwInternal();

}
