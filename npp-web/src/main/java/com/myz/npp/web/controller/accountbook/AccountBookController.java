package com.myz.npp.web.controller.accountbook;

import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.login.annotation.LoginRequired;
import com.myz.starters.login.context.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/13 16:08
 * @email 2641007740@qq.com
 */
@Slf4j
@Controller
@RequestMapping("/account-book")
@ParamRetValPrinter
public class AccountBookController {

    @LoginRequired
    @GetMapping("/my")
    public ModelAndView my(Model model){
        String userJson = LoginContext.get();
        model.addAttribute("userJson",userJson);
        return new ModelAndView("my-account-book");
    }
}
