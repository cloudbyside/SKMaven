<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <jsp:include page="include/page_head.jsp"></jsp:include>
    <title>测试页面</title>
</head>
<body>
<h1 id="myTextH">你好，世界！</h1>
</body>
<jsp:include page="include/page_js.jsp"></jsp:include>
<script src="assets/js/modal/seckill/seckill_index.js"></script>
<script>
    $(function () {
        var count = 1;
       setInterval(function () {
           if(count==2){
               return false;
           }
           $("#myTextH").fadeOut(200).css({color:"red"}).fadeIn(1000);
           count++;
       },500)
    })
</script>
</html>