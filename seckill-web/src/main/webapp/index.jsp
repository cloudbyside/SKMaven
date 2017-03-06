<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <jsp:include page="include/page_head.jsp"></jsp:include>
    <title>秒杀首页</title>
</head>
<body>
<div class="container">
    <div class="row">
            <a class="btn btn-info col-md-4 col-md-offset-4" style="margin-top: 100px">
                <h1 id="loginBtn">点击进入</h1>
            </a>
    </div>
</div>
</body>
<jsp:include page="include/page_js.jsp"></jsp:include>
<script src="assets/js/modal/seckill/seckill_index.js"></script>
<script>
        $(function () {
            /*注册按钮事件*/
            var loginBtn = $("#loginBtn");
            registerLoginEvent(loginBtn);
        })    
</script>

</html>