<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/include/page_tag.jsp"></jsp:include>
<html lang="zh-CN">
<head>
    <jsp:include page="/include/page_head.jsp"></jsp:include>
    <title>商品详情页面</title>
</head>
<body>
<%--秒杀详情begin--%>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${resultData.data.name}</h1>
        </div>
        <div class="panel-body">
            <%--秒杀状态提示Begin--%>
            <div>
                <h2 class="text-danger" id="seckill-box">
                </h2>
            </div>
            <%--秒杀状态提示End--%>


        </div>
    </div>
</div>

<%--用户登陆成功友好提示Begin--%>
<div id="tipInfoBox" class="alert alert-success col-md-4 col-md-push-4"></div>
<%--用户登陆成功友好提示End--%>

<%--秒杀详情end--%>

<!--登录弹出层，输入电话 begin-->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey" placeholder="填写手机号^o^"
                               class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <!-- 验证信息-->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    登录
                </button>
            </div>

        </div>
    </div>
</div>
<!--登录弹出层，输入电话 end-->
</body>
<jsp:include page="/include/page_js.jsp"></jsp:include>
<%--引入seck模块相关依赖js--%>
<script src="/assets/js/modal/seckill/seckill.js"></script>
<script src="/assets/js/modal/seckill/seckill_list_function.js"></script>
<script src="/assets/js/modal/seckill/secki_detail_function.js"></script>



<%--引入倒计时js--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>

<script>
    $(function () {
        /*
         * 1.用户登录状态判断
         * 2.秒杀状态判断,以便计时交互
         * */
        seckill.userLoginCheck();
        seckill.seckTimeCheck(${resultData.data.seckillId});
    })
</script>
</html>