package com.myz.npp.web.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.*;
import com.myz.common.exception.MyzBizException;
import com.myz.common.util.ApiResult;
import com.myz.npp.api.user.dto.UserDto;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.login.annotation.LoginRequired;
import com.myz.starters.login.context.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/17 13:37
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/wechat-order")
@ParamRetValPrinter
public class WechatOrderController {

    @Value("${npp.domain}")
    private String nppDomain;

    private String ORDER_GEN_RESULT_SUCCESS_CODE = "SUCCESS";

    // 商户秘钥
    private String MC_KEY = "zaijiaxuezaijiaxuezaijiaxue12345";

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param goodsId
     * @param moneyFen
     * @return
     */
    @PostMapping("/gen")
    public ApiResult<Map<String,String>> gen(@RequestParam String goodsId,
                                             @RequestParam(required = false) Long moneyFen){
        try {
            String userJson = LoginContext.get();// "oyXF_0_JEwhT0JqkW-SyAri53jAg";
            UserDto userData = objectMapper.readValue(userJson, UserDto.class);
            String openId = userData.getPlatformId();

            moneyFen = moneyFen == null?1L:moneyFen;

            Map<String, String> resultMap = prePay(""+new Date().getTime(),openId,moneyFen);
            String result_code = resultMap.get("result_code");
            String return_msg = resultMap.get("return_msg");
            if (!StringUtils.equals(ORDER_GEN_RESULT_SUCCESS_CODE,result_code)){
                throw new MyzBizException("wechat-order-prePay","prePay fail result_code="+result_code+"，msg="+return_msg);
            }
            // 本来直接返回给前端一个prepay_id就行了，但是为了方便前端构造唤起支付页面的参数，这里帮前端构建了
            log.info("build wake up pay page for front start");
            Map<String,String> wakeupPayPageMap = new HashMap<>();
            wakeupPayPageMap.put("appId",resultMap.get("appid"));
            wakeupPayPageMap.put("timeStamp",new Date().getTime()+"");
            wakeupPayPageMap.put("nonceStr",resultMap.get("nonce_str"));
            wakeupPayPageMap.put("package","prepay_id="+resultMap.get("prepay_id"));
            wakeupPayPageMap.put("signType", WXPayConstants.SignType.MD5.name()); // 注意此处需与统一下单的签名类型一致

            String sign = WXPayUtil.generateSignature(wakeupPayPageMap, MC_KEY);
            wakeupPayPageMap.put("paySign",sign);
            log.info("return data={}",wakeupPayPageMap);

            return ApiResult.OK(wakeupPayPageMap);
        }catch (Exception e){
            log.error("gen order fail,e=",e);
            return ApiResult.error();
        }
    }

    /**
     * 微信是按照post方式调用的
     * 没有参数？？？？？ 那怎么办知道是哪个订单？
     * @param request
     * @return
     */
    @LoginRequired(required = false)
    @RequestMapping(value = "/callback",produces = MediaType.APPLICATION_XML_VALUE)
    public String callback(HttpServletRequest request){

        log.info("callback invoked!!!");
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            log.info("callback name:{} value={}",name,value);
        }
        Map<String,String> retMap = new HashMap<>();
        retMap.put("return_code","SUCCESS");
        retMap.put("return_msg","OK");
        try {
            //TODO 更新订单状态

            return WXPayUtil.mapToXml(retMap);
        } catch (Exception e) {
            log.error("callback error,e=",e);
            return "";
        }
    }

    /**
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    private Map<String,String> prePay(String orderId,String openId,long moneyFen) throws Exception {

        WXPayConfig wxPayConfig = new WXPayConfig() {
            @Override
            public String getAppID() {
                return "wx164ec2b8f110f3a9";
            }
            @Override
            public String getMchID() {
                return "1598431781";
            }
            @Override
            public String getKey() {
                return null;
            }
            @Override
            public InputStream getCertStream() {
                return null;
            }
            @Override
            public IWXPayDomain getWXPayDomain() {
                return new IWXPayDomain() {
                    public void report(String domain, long elapsedTimeMillis, Exception ex) {

                    }

                    public DomainInfo getDomain(WXPayConfig config) {
                        return new DomainInfo("api.mch.weixin.qq.com",true);
                    }
                };
            }
        };

        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        String urlSuffix = "/pay/unifiedorder";

        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("appid","wx164ec2b8f110f3a9");
        dataMap.put("attach","支付测试"); // none
        dataMap.put("body","JSAPI支付测试");
        dataMap.put("mch_id","1598431781");
        dataMap.put("nonce_str", WXPayUtil.generateNonceStr());
        dataMap.put("notify_url", nppDomain +"/wechat-order/callback");
        dataMap.put("openid",openId);
        dataMap.put("out_trade_no",orderId);
        dataMap.put("total_fee",moneyFen+"");
        dataMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
        dataMap.put("trade_type","JSAPI");

        String sign = WXPayUtil.generateSignature(dataMap, MC_KEY);
        dataMap.put("sign",sign);
        String data = WXPayUtil.mapToXml(dataMap);
        log.info("xml data={}",data);

        String result = wxPayRequest.requestWithoutCert(urlSuffix, null, data, false);
        log.info("result={}",result);
        return WXPayUtil.xmlToMap(result);
    }
}
