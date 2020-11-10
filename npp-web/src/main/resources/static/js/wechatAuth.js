$(function () {


    // var ua = navigator.userAgent.toLowerCase();
    // var isWeixin = ua.indexOf('micromessenger') != -1;
    // var isAndroid = ua.indexOf('android') != -1;
    // var isIos = (ua.indexOf('iphone') != -1) || (ua.indexOf('ipad') != -1);
    // if (!isWeixin) {
    //     document.head.innerHTML = '<title>抱歉，出错了</title><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0"><link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/open/libs/weui/0.4.1/weui.css">';
    //     document.body.innerHTML = '<div class="weui_msg"><div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div><div class="weui_text_area"><h4 class="weui_msg_title">请在微信客户端打开链接</h4></div></div>';
    // }
    //针对于微信和支付宝类似的平台基于OAuth2授权
    $.extend({
        wechatPublicAuth: function(scopeTag){
            var appId = "wx164ec2b8f110f3a9";//公众号h5授权
            var scope = "snsapi_base";//静默授权类型
            if(scopeTag === 1){
                scope = "snsapi_base";//静默授权类型
            }else if(scopeTag === 2){
                scope = "snsapi_userinfo";//主动授权类型
            }
            alert("appId="+appId+",scope="+scope);

            var redirectUrl = "http://"+document.domain+"/wechat-h5/auth?appId="+appId;
            var encodedRedirectUrl  = encodeURIComponent(redirectUrl);
            alert("redirectUrl="+redirectUrl+"--->encodedRedirectUrl="+encodedRedirectUrl);

            var getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+encodedRedirectUrl+"&response_type=code&scope="+scope+"&state=index.html#wechat_redirect";
            alert("wechat open api url="+getCodeUrl);
            window.location.href=getCodeUrl;
        },
    });

});