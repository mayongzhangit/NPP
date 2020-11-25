package com.myz.npp.service.test.rpc;

import com.myz.common.util.ApiResult;
import com.myz.npp.api.test.TestServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/24 17:21
 * @email 2641007740@qq.com
 */
@Slf4j
@Service(timeout = 4002)
public class TestServiceRpc implements TestServiceApi {
    @Override
    public ApiResult defaultTimeout(Long timeCostMs) {
        log.info("sleep time={}",timeCostMs);
        try {
            Thread.sleep(timeCostMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ApiResult.OK(timeCostMs);
    }

    @Override
    public ApiResult providerTimeout(Long timeCostMs) {
        try {
            Thread.sleep(timeCostMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ApiResult.OK(timeCostMs);
    }

    @Override
    public ApiResult consumerTimeout(Long timeCostMs) {
        try {
            Thread.sleep(timeCostMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ApiResult.OK(timeCostMs);
    }

    @Override
    public ApiResult timeout(Long timeCostMs) {
        try {
            Thread.sleep(timeCostMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ApiResult.OK(timeCostMs);
    }

    @Override
    public ApiResult throwInternal() {
        return ApiResult.OK(null);
    }
}
