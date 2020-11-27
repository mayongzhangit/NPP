package com.myz.npp.web.controller;

import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/27 11:33
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/")
@ParamRetValPrinter
@MyzControllerAdviceAnno
public class IndexController {

    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
