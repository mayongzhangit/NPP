package com.myz.npp.web.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.common.exception.MyzBizException;
import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.npp.api.user.dto.UserDto;
import com.myz.npp.api.user.dto.UserInsertParam;
import com.myz.npp.api.user.enums.UserTypeEnum;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import com.myz.starters.login.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/9 15:44
 * @email 2641007740@qq.com
 */
@Slf4j
@MyzControllerAdviceAnno
@ParamRetValPrinter
@RestController
@RequestMapping("/h5-login")
public class H5LoginController {

    @Reference(timeout = 5000,retries = 0,version = "${dubbo.service.version}",methods = {
            @Method(name = "saveUser", timeout = 6000,retries = -1)
    })
    private UserServiceApi userServiceApi;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * 用户名、密码登录
     * curl -XPOST http://localhost:8888/h5-login/login -d "mobile=15701311193&passwd=123456"
     * @param mobile
     * @param passwd
     * @param request
     * @param resp
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/login")
    public ApiResult login(String mobile,String passwd,
                           HttpServletRequest request, HttpServletResponse resp) throws JsonProcessingException {

        ApiResult<UserDto> userDataApiResult = userServiceApi.loginByPhone(mobile,passwd);
        if (!userDataApiResult.isSuccess()) {
            return userDataApiResult;
        }
        UserDto userDto = userDataApiResult.getData();
        String userJson = objectMapper.writeValueAsString(userDto);
        CookieUtil.set(userJson,request,resp);
        userDataApiResult.setUserJson(userJson);
        return userDataApiResult;
    }

    /**
     * 登录页
     * @return
     */
    @GetMapping("/login-page")
    public ModelAndView loginPage() {
        return new ModelAndView("login-page"); // ftl
    }

    /**
     * 登陆成功到首页
     * @param mobile
     * @param passwd
     * @param request
     * @param resp
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/login-2-index")
    public ModelAndView login2Index(String mobile, String passwd,
                                    HttpServletRequest request, HttpServletResponse resp) throws JsonProcessingException {

        ApiResult<UserDto> userDataApiResult = userServiceApi.loginByPhone(mobile,passwd);
        if (!userDataApiResult.isSuccess()) {
            return new ModelAndView("reg");
        }
        UserDto userDto = userDataApiResult.getData();
        String userJson = objectMapper.writeValueAsString(userDto);
        CookieUtil.set(userJson,request,resp);
        userDataApiResult.setUserJson(userJson);
        return new ModelAndView("index"); // ftl
    }
    /**
     * curl -XPOST http://localhost:8888/h5-login/reg -d "mobile=15701311193&passwd=123456&confirmPasswd=123456"
     * @param mobile
     * @param passwd
     * @param confirmPasswd
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/reg")
    public ApiResult reg(String mobile,String passwd,String confirmPasswd) throws JsonProcessingException {
        if (!StringUtils.equals(passwd,confirmPasswd)){
            throw new MyzBizException("passwd-not-equals-confirmPasswd","",mobile,passwd,confirmPasswd);
        }
        UserInsertParam userInsertParam = new UserInsertParam();
        userInsertParam.setMobile(mobile);
        userInsertParam.setPasswd(passwd);
        userInsertParam.setType(UserTypeEnum.PHONE_REG_TYPE.getType());

        return userServiceApi.saveUser(userInsertParam);
    }
}
