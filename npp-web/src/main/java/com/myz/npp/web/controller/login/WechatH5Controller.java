package com.myz.npp.web.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.common.exception.MyzBizException;
import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.UserServiceApi;
import com.myz.npp.api.user.dto.UserDto;
import com.myz.npp.api.user.dto.UserInsertParam;
import com.myz.npp.api.user.dto.UserUpdateParam;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.login.annotation.LoginRequired;
import com.myz.starters.login.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzMa
 * @desc
 * 微信开放平台文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
 *
 * @date 2020/6/14 11:28 AM
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/wechat-h5")
@ParamRetValPrinter
@LoginRequired(required = false)
public class WechatH5Controller {

    @Resource(name = "nppRestTemplate")
    private RestTemplate nppRestTemplate;

    @Reference
    private UserServiceApi userServiceApi;

    private final static String GZH_H5_APPID = "wx164ec2b8f110f3a9";
    private final static String GZH_H5_SECRET = "b3229556ca789addf6d1fe568149e641";

    private ObjectMapper objectMapper = new ObjectMapper();

    private static Map<String,String> appMap = new HashMap<>();

    static {
        appMap.put("wx164ec2b8f110f3a9","b3229556ca789addf6d1fe568149e641");
    }

    /**
     * curl http://localhost/wechat-h5/mock/oyXF_0_JEwhT0JqkW-SyAri53jAg
     * curl http://test1.zaijiaxue.tech/wechat-h5/mock/oyXF_0_JEwhT0JqkW-SyAri53jAg
     * @param openId oyXF_0_JEwhT0JqkW-SyAri53jAg
     * @param resp
     * @return
     */
    @GetMapping("/mock/{openId}")
    public ApiResult mock(@PathVariable String openId, @RequestParam String security,
                          HttpServletRequest request, HttpServletResponse resp) throws JsonProcessingException {
        if (!StringUtils.equals(security,"qazwsx")){
            return ApiResult.build("no right","无权限");
        }

        ApiResult<UserDto> userDataApiResult = userServiceApi.getByPlatformId(openId);
        UserDto reQueriedUserData = userDataApiResult.getData();// 重新赋值
        if (reQueriedUserData == null) {
            return ApiResult.build("user-not-found", "未找到用户openId="+openId);
        }
        String userJson = objectMapper.writeValueAsString(reQueriedUserData);
        CookieUtil.set(userJson,request,resp);
        userDataApiResult.setUserJson(userJson);
        return userDataApiResult;
    }

    /**
     * @description
     * 前端发起 https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd3a7029dbd8abba1
     *         &redirect_uri=【编码后的http://test1.zaijiaxue.tech/wechat-h5/auth?appId=wx164ec2b8f110f3a9&successUrl=/index.html】
     *          &response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
     *
     * 在微信中打开：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx164ec2b8f110f3a9&redirect_uri=http%3A%2F%2Ftest.zaijiaxue.tech%2Fwechat%2Fauth%3FappId%3Dwx164ec2b8f110f3a9%26successUrl%3D%2Findex.html&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
     * @author yzMa
     * @date 2019/4/28 2:12 PM
     * @param appId
     * @param code 前端获取code，之后直接追加到这个redirect_uri之后，授权成功跳转到的目标地址
     * @return
     */
    @GetMapping("/auth")
    public void auth(@RequestParam String appId,
                          @RequestParam String code,
                          @RequestParam String state,
                          HttpServletRequest request,
                          HttpServletResponse resp) throws IOException {

        String secret = appMap.get(appId);
        if(StringUtils.isBlank(secret)){
            log.info("appId not support={}",appId);
            return;
        }
        String accessCodeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+GZH_H5_SECRET+"&code="+code+"&grant_type=authorization_code";
        ResponseEntity<String> accessTokenEntity = nppRestTemplate.getForEntity(accessCodeUrl, String.class);
        log.info("base data={}",objectMapper.writeValueAsString(accessTokenEntity));

        String body = accessTokenEntity.getBody();
        Map map = objectMapper.readValue(body, Map.class);
        String scope = (String)map.get("scope");
        String accessToken = (String) map.get("access_token");
        String openId = (String)map.get("openid");

        log.info("openId={},scope={},accessToken={}",openId,scope,accessToken);
        ApiResult<UserDto> apiResult = userServiceApi.getByPlatformId(openId);
        if(!apiResult.isSuccess()){
            log.info("queryByOpenId| fail code={},msg={}",apiResult.getCode(),apiResult.getMsg());
            throw new MyzBizException("auth-query-openId-fail","根据openId="+openId+"查询失败");
        }

        UserDto queriedUserData = apiResult.getData();
        if("snsapi_base".equals(scope)){
            if(queriedUserData != null){
                log.info("[snsapi_base] openId={} already exists");
                CookieUtil.set(objectMapper.writeValueAsString(queriedUserData),request,resp);
                resp.sendRedirect("/"+state);
                return;
            }

            log.info("[snsapi_base] openId={} not exists do insert",openId,scope);
            UserInsertParam userInsertParam = new UserInsertParam();
            userInsertParam.setPlatformId(openId);
            userInsertParam.setAppId(appId);
            userServiceApi.saveUser(userInsertParam);

            UserDto reQueriedUserData = userServiceApi.getByPlatformId(openId).getData();// 重新赋值
            CookieUtil.set(objectMapper.writeValueAsString(reQueriedUserData),request,resp);
            resp.sendRedirect("/"+state);
            return;
        }
        Assert.isTrue(("snsapi_userinfo".equals(scope)),"scope="+scope+ " is not known");

        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid=OPENID&lang=zh_CN";
        ResponseEntity<String> userInfoEntity = nppRestTemplate.getForEntity(userInfoUrl, String.class);
        log.info("userInfo data={}", objectMapper.writeValueAsString(userInfoEntity));

        String userInfoBody = userInfoEntity.getBody();
        Map userInfoJson = objectMapper.readValue(userInfoBody,Map.class);
        if(queriedUserData == null){
            log.info("[snsapi_userinfo] openId={} not exists do insert",openId);
            UserInsertParam userInsertParam = new UserInsertParam();
            userInsertParam.setPlatformId(openId);
            userInsertParam.setAppId(appId);
            userInsertParam.setNickName((String) userInfoJson.get("nickname"));
            Integer sex = (Integer) userInfoJson.get("sex");//1时是男性，值为2时是女性，值为0时是未知
            userInsertParam.setGender(sex==null?null:(sex == 1?true:false));
            userInsertParam.setAvatarUrl((String) userInfoJson.get("headimgurl"));
            userInsertParam.setProvince((String)userInfoJson.get("province"));
            userInsertParam.setCity((String)userInfoJson.get("city"));
            userInsertParam.setCountry((String)userInfoJson.get("country"));
            userServiceApi.saveUser(userInsertParam);
        }else{
            log.info("openId={} exists do update",openId,scope);
            UserUpdateParam userUpdateParam = new UserUpdateParam();
            userUpdateParam.setUserId(queriedUserData.getUserId());
            userUpdateParam.setPlatformId(openId);
            userUpdateParam.setNickName((String)userInfoJson.get("nickname"));
            Integer sex = (Integer)userInfoJson.get("sex");//1时是男性，值为2时是女性，值为0时是未知
            userUpdateParam.setGender(sex==null?null:(sex == 1?true:false));
            userUpdateParam.setAvatarUrl((String)userInfoJson.get("headimgurl"));
            userUpdateParam.setProvince((String)userInfoJson.get("province"));
            userUpdateParam.setCity((String)userInfoJson.get("city"));
            userUpdateParam.setCountry((String)userInfoJson.get("country"));
//            userUpdateParam.setUnionid((String)userInfoJson.get("unionid"));
            userServiceApi.updateByUserId(userUpdateParam);
        }

        UserDto reQueriedUserData = userServiceApi.getByPlatformId(openId).getData();// 重新赋值
        CookieUtil.set(objectMapper.writeValueAsString(reQueriedUserData),request,resp);
        resp.sendRedirect("/"+state);
        return;
    }
}
