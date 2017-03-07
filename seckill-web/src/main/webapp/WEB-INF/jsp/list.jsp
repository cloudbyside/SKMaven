<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--加载jstl标签库--%>
<jsp:include page="../../include/page_tag.jsp"></jsp:include>
<html lang="zh-CN">

<head>
    <jsp:include page="../../include/page_head.jsp"></jsp:include>
    <%--加载分页样式--%>
    <link href="/libs/plugins/pagination/css/pagination.css" rel="stylesheet">
    <title>秒杀首页</title>
</head>


<body>
<div class="container">
    <%--详情列表开始begin--%>
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h1>秒杀列表</h1>
                <div>
                    <button id="defaultBtn" class="btn btn-default">默认列表<span class="glyphicon glyphicon-align-justify"></span></button>
                    <button id="willBeginBtn" class="btn btn-default">即将开始<span class="glyphicon glyphicon-circle-arrow-down"></span></button>
                    <button id="hotIngBtn" class="btn btn-default">火热进行<span><img src="/imges/0x1f4a5.png"/></span></button>
                    <button id="willEndBtn" class="btn btn-default">即将结束<span class="glyphicon glyphicon-circle-arrow-down"></span></button>
                    <button id="userBtn" class="btn btn-default">我的秒杀<span class="glyphicon glyphicon-cloud"></span></button>
                    <button id="loginOutBtn" class="btn btn-default">登陆<span class="glyphicon glyphicon-log-in"></span></button>
                </div>
            </div>
            <div class="panel-body">
                <table class="table table-hover" id="listTable">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>库存</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>创建时间</th>
                        <th>详情页</th>
                    </tr>
                    </thead>
                    <tbody id="seckListBody">
                    <c:forEach var="seckill" items="${seckillPageData.pageData}" varStatus="status">
                        <tr>
                            <td>${seckill.name}</td>
                            <td>${seckill.number}</td>
                            <td>
                                <fmt:formatDate value="${seckill.startTime}"
                                                pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                            </td>
                            <td>
                                <fmt:formatDate value="${seckill.endTime}"
                                                pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                            </td>
                            <td style="color: red">
                                <fmt:formatDate value="${seckill.createTime}"
                                                pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                            </td>
                            <td><a href="/seckill/${seckill.seckillId}/detail" class="btn btn-info" target="_blank">详情</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--详情列表开始end--%>
    <%--分页组件Begin--%>
    <div id="News-Pagination" class="row" style="text-align: center;margin-top: -20px"></div>
    <%--分页组件End--%>

    <%--電話彈出框begin--%>
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

    <%--電話彈出框end--%>


    <%--用户秒杀列表begin--%>

        <div id="userResultModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 class="modal-title text-center">
                            <span><img src="/imges/my_chart.png" style="width: 50px;height: 50px"></span>用户秒杀列表：<span id="userModalSpan"></span>
                        </h3>
                    </div>
                    <div class="modal-body">
                            <table class="table" id="seckResultTable">
                                <tr>
                                <thread><th>商品名称</th> <th>支付状态</th><th>秒杀时间</th></thread>
                                </tr>
                                <tbody>
                                <%--class:error success warning info--%>
                                <tr>
                                    <td> 苹果666</td>
                                    <td> 未支付</td>
                                    <td> 01/04/2012</td>
                                </tr>
                                </tbody>
                            </table>
                            <%--用户秒杀列表分页组件--%>
                            <div id="userPageBox" class="col-md-8 col-md-push-3"></div>
                    </div>
                    <div class="modal-footer">
                      <h1>
                          <button type="button" class="btn btn-primary" data-dismiss="modal">知道啦</button>                      </h1>
                    </div>
                </div>
            </div>
        </div>

    <%--用户秒杀列表end--%>

</div>
</body>
<jsp:include page="../../include/page_js.jsp"></jsp:include>
<%--引入分页js--%>
<script src="/libs/plugins/pagination/js/jquery.pagination.js"></script>
<%--引入本页面相关js代码--%>
<script src="/assets/js/modal/seckill/seckill.js"></script>
<script src="/assets/js/modal/seckill/seckill_list_function.js"></script>
<script src="/assets/js/modal/seckill/secki_detail_function.js"></script>

<%--引入操作sessionStoreage的工具类--%>
<script src="/assets/js/common/seckill_util.js"></script>


<script>
    $(function () {
        //1.初始化分页插件
        seckill.pagination.init(${seckillPageData.totalNum}, ${seckillPageData.pageSize},${seckillPageData.curNo},0);
        //2.初始化排序按钮事件
        seckill.initSortBtn();

    })

</script>

</html>