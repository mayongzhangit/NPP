package com.myz.npp.web.controller.test;

import com.myz.common.util.ApiResult;
import com.myz.npp.api.test.TestServiceApi;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Argument;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/24 17:36
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/test")
@ParamRetValPrinter
@MyzControllerAdviceAnno
public class TestController implements TestServiceApi{

    @Reference(timeout = 3001,methods = {
            @Method(name = "defaultTimeout",arguments=@Argument(type = "java.lang.Lang"),timeout = 1002,retries = 1)
    }) // 我擦，方法级的超时和重试不生效
    private TestServiceApi testServiceApi;

    @GetMapping("/defaultTimeout/{timeCostMs}")
    @Override
    public ApiResult defaultTimeout(@PathVariable Long timeCostMs) {
        return testServiceApi.defaultTimeout(timeCostMs);
    }

    @GetMapping("/providerTimeout/{timeCostMs}")
    @Override
    public ApiResult providerTimeout(@PathVariable Long timeCostMs) {
        return testServiceApi.providerTimeout(timeCostMs);
    }

    @GetMapping("/consumerTimeout/{timeCostMs}")
    @Override
    public ApiResult consumerTimeout(@PathVariable Long timeCostMs) {
        return testServiceApi.consumerTimeout(timeCostMs);
    }

    @GetMapping("/timeout/{timeCostMs}")
    @Override
    public ApiResult timeout(@PathVariable Long timeCostMs) {
        return testServiceApi.timeout(timeCostMs);
    }

    @GetMapping("/throwInternal")
    @Override
    public ApiResult throwInternal() {
        return testServiceApi.throwInternal();
    }

}
