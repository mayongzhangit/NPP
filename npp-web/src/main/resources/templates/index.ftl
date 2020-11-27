<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>NPP</title>
</head><style type="text/css">
    .body-background {background-size: 100% }
</style>
<body class="body-background">
    NPP首页 ftl
    <span id="userJsonSpan"></span>
</body>
<script type='text/javascript' src='../js/jquery-1.8.3.min.js' charset='utf-8'></script>
<script type="text/javascript">

    $.get("/user/get",function (result) {
        console.log(result);
        var code = result.code;
        if("000000" == code){
            $("#userJsonSpan").html("userJson="+result.data);
            $("#userJsonSpan").show();
            return;
        }
        if("12345" == code){
            window.location.href="/h5-login/login-page";
            return;
        }
    });
</script>
</html>