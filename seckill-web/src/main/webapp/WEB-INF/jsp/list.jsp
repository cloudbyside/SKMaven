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
                    <button id="willBeginBtn" class="btn btn-default">即将开始<span class="glyphicon glyphicon-circle-arrow-down"></span></button>
                    <button id="willEndBtn" class="btn btn-default">即将结束<span class="glyphicon glyphicon-circle-arrow-down"></button>
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