package com.myz.npp.web.controller;

import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2021/1/4 21:28
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/config")
@ParamRetValPrinter
@MyzControllerAdviceAnno
public class ConfigController {

    @Value("${npp.web.data:default}")
    private String data;

    @GetMapping("/get-data")
    public String getData(){
        return data;
    }
}
