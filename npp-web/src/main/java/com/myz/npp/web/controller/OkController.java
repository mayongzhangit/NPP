package com.myz.npp.web.controller;

import com.myz.common.util.ApiResult;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import com.myz.starters.distribute.sample.web.controller.exception.MyzBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 11:17 PM
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/")
@ParamRetValPrinter
@MyzControllerAdviceAnno
public class OkController {

    /**
     *
     * @return
     */
    @GetMapping("/ok")
    public ApiResult ok(){

        return ApiResult.OK("OK");
    }

    /**
     * 需要由xml的依赖比如 jackson-dataformat-xml 并且设置produces ，然而加上后原先的接口也返回了xml 怎么破？ 内容协商后的结果xml排在了json前
     * @return
     */
//    @GetMapping(value = "/okXml",produces = MediaType.APPLICATION_XML_VALUE)
//    public ApiResult okXml(){
//
//        return ApiResult.OK("OK");
//    }
}
