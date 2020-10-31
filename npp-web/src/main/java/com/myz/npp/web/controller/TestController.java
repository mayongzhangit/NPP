package com.myz.npp.web.controller;

import com.myz.common.util.ApiResult;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import com.myz.starters.distribute.sample.web.controller.exception.MyzBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/31 11:54
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/")
@ParamRetValPrinter
@MyzControllerAdviceAnno
public class TestController {

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     *
     * @param exFlag
     * @param sleepTime
     * @return
     */
    @GetMapping("/test")
    public ApiResult test(@RequestParam(required = false,defaultValue = "false") boolean exFlag,
                          @RequestParam(name = "sleepTime",required = false,defaultValue = "0") long sleepTime) throws InterruptedException {

        if (exFlag){
            throw new MyzBizException("manualEx","手工抛出异常");
        }
        if ("prod".equals(profile)){
            return ApiResult.OK("OK");
        }
        Thread.sleep(sleepTime);
        return ApiResult.OK("OK");
    }

}
