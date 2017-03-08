/**
 * Created by liuburu on 2017/3/2.
 */


function paginationInit(totalNum, perPageNum, pageNo) {

    $("#News-Pagination").pagination(totalNum, {
        items_per_page: perPageNum,
        next_text: "下一页",
        prev_text: "前一页",
        num_display_entries: 2,
        num_edge_entries: 2,
        current_page: pageNo - 1,
        callback: handlePaginationClick/*函数在seckill_function.js中定义*/
    });
}
/*分页回调函数*/
function handlePaginationClick(new_page_index, pagination_container) {
    // This selects 20 elements from a content array
  //  console.log("当前位置==>" + new_page_index);
    /*
     * 分页按钮点击后，立马发送ajax异步请求数据
     * */
    if (seckillConfig.firstTime == true) {
        seckillConfig.firstTime = false;
        return;
    }
    var pageNo = new_page_index + 1;
    pageRequestSeckills(pageNo);
    return false;
}


/*分类查询按钮事件监听*/
function registerSortBtn(willBegin, willEnd,defaultBtn,userBtn,loginOutBtn,hotIngBtn) {
    var pageNo = 1;//第一页
    var pageSize = seckillConfig.pageSize;
    willBegin.bind("click", function () {
        $.get(seckillConfig.URL.allTotal(),{},function (totals) {
            var totalNum = totals.data.willBegin;
            seckill.pagination.init(totalNum,pageSize,pageNo,1);
        });
    });
    hotIngBtn.bind("click",function () {
       // console.log("hot btn click....");
        $.get(seckillConfig.URL.allTotal(),{},function (totals) {
            var totalNum = totals.data.seckIng;
            seckill.pagination.init(totalNum,pageSize,pageNo,3);
        });
    });
    willEnd.bind("click", function () {
        $.get(seckillConfig.URL.allTotal(),{},function (totals) {
            var totalNum = totals.data.willEnd;
            seckill.pagination.init(totalNum,pageSize,pageNo,2);
        })
    });
    defaultBtn.bind("click",function () {
        //console.log("defaultBtn");
        //重定向到list页面
        window.location=seckillConfig.URL.list(1,5,0,"desc");
    });
    userBtn.bind("click",function () {
       // console.log("userBtn");
        var pluginBox = $("#userPageBox");
        seckill.userLoginCheck();
        var userPhone = $.cookie("userPhone");
        if(userPhone=="null"){
            return;
        }
        $.get(seckillConfig.URL.userResult(1,5,userPhone),{},function (data) {
            //1.初始化标题 2.初始化分页数据 3.显示模型层
            if(data.pageData.length!=0){
                $("#userModalSpan").html(data.pageData[0].userPhone).css({color:"red"});
            }
            var totalNum = data.totalNum;
            var perPageNum = data.pageSize;
            initUserResultPagePlugin(pluginBox,totalNum,perPageNum);
            $("#userResultModal").modal({
                show: true,  //显示弹出层
                backdrop: 'static', //禁止位置关闭
                keyboard: false //关闭键盘事件
            });
        });
    });
    var userPhone = $.cookie("userPhone");
    if(userPhone==undefined||userPhone=="null"){
    }else{
        loginOutBtn.html('退出<span class="glyphicon glyphicon-user"></span>');
    }
    loginOutBtn.bind("click",function () {
        var userPhone = $.cookie("userPhone");
        if(userPhone==undefined||userPhone=="null"){
           // $(this).html('退出<span class="glyphicon glyphicon-user"></span>');
            seckill.userLoginCheck();
        }else{
            var isExit = window.confirm("你确定要退出吗?");
            if(isExit){
                $.cookie("userPhone",null);
                $(this).html('登陆<span class="glyphicon glyphicon-log-in"></span>');
            }
        }
    });





}

/*获取随机bootstrap样式颜色*/
function getRandomClass(Min,Max)
{
    var Range = Max - Min;
    var num = Math.random()*10+4;//[0,4)
    if(num==0){
        return seckillConfig.warn;
    }else if(num==1){
        return seckillConfig.success;
    }else if(num==2){
        return seckillConfig.info;
    }else{
        return seckillConfig.error;
    }
}


/*日期格式化函数*/
function formatDate(strTime) {
    var date = new Date(strTime);
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " "
        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
}

/*状态翻译函数*/
function whatStatus(status) {
    if(status==0){
        return "未支付";
    }else{
        return "已支付";
    }
}


/*抽取出来的公共函数:请求产品分页数据，并且进行数据异步的进行显示*/
function pageRequestSeckills(pageNo) {
    var pageSize = seckillConfig.pageSize;
    var sortType = seckillConfig.sortType;
    var orderType = "";
    if(sortType==1){//即将开始
        orderType = seckillConfig.orderType_1;
        /*
         *改变排序图标样式:
         * 升序图标：glyphicon glyphicon-circle-arrow-up降序图标：glyphicon glyphicon-circle-arrow-down
         * */
        if(seckillConfig.orderType_1=="asc"){
            $("#willBeginBtn").children("span").removeClass("glyphicon glyphicon-circle-arrow-up").addClass("glyphicon glyphicon-circle-arrow-down");
        }else{
            $("#willBeginBtn").children("span").removeClass("glyphicon glyphicon-circle-arrow-down").addClass("glyphicon glyphicon-circle-arrow-up");
        }
        seckillConfig.orderType_1 = seckillConfig.orderType_1=="asc"?"desc":"asc";


    }else if(sortType==2){//即将结束
        orderType = seckillConfig.orderType_2;
        if(seckillConfig.orderType_2=="asc"){
            $("#willEndBtn").children("span").removeClass("glyphicon glyphicon-circle-arrow-up").addClass("glyphicon glyphicon-circle-arrow-down");
        }else{
            $("#willEndBtn").children("span").removeClass("glyphicon glyphicon-circle-arrow-down").addClass("glyphicon glyphicon-circle-arrow-up");
        }
        seckillConfig.orderType_2 = seckillConfig.orderType_2=="asc"?"desc":"asc";
    }else{
        orderType="desc";
    }
    $.get(seckillConfig.URL.query(pageNo, pageSize, sortType, orderType), function (data) {
        var tableBody = $("#seckListBody");
        tableBody.children("tr").remove();
        for (var i = 0; i < data.pageData.length; i++) {
            tableBody.append(
                "<tr>" +
                "<td>" + data.pageData[i].name + "</td>" +
                "<td>" + data.pageData[i].number + "</td>" +
                "<td>" +
                formatDate(data.pageData[i].startTime) +
                "</td>" +
                "<td>" +
                formatDate(data.pageData[i].endTime) +
                "</td>" +
                "<td>" +
                formatDate(data.pageData[i].createTime) +
                "</td>" +
                "<td><a href='" + seckillConfig.URL.detail(data.pageData[i].seckillId) + "' class='btn btn-info' target='_blank'>详情</a></td>" +
                "  </tr>");
        }
       // console.log(data);
        /*文本高亮显示*/
        textDecoration(sortType);
    });
}


/*文本高亮显示*/
function textDecoration(sortType) {
    $("#listTable tr:gt(0)").each(function () {
            var t;
            if(sortType==0){
                t = 4;
            }else if(sortType==1){
                t = 2;
            }else if(sortType==2){
                t = 3;
            }
            $(this).children().eq(t).css({color:"red"});
    });
}


/*初始化用户列表分页插件*/
function initUserResultPagePlugin(pluginBox,totalNum,perPageNum) {
    if(totalNum==0){
        $("#seckResultTable").html('<tr><td ><h2 class="col-md-6 col-md-push-3">空空如也</h2></td></tr>');
    }
    pluginBox.pagination(totalNum, {
        items_per_page: perPageNum,
        next_text: "下一页",
        prev_text: "前一页",
        num_display_entries: 2,
        num_edge_entries: 2,
        callback: handleUserSKList/*函数在seckill_function.js中定义*/
    });
}

function handleUserSKList(new_page_index, pagination_container) {
    var userPhone = $.cookie("userPhone");
    var pageNo = new_page_index+1;
    $("#seckResultTable tr:gt(0)").remove();
    $.get(seckillConfig.URL.userResult(pageNo,5,userPhone),{},function (data) {
        for(var i=0;i<data.pageData.length;i++){
            $("#seckResultTable").append(
                '<tr><td>'+data.pageData[i].skName+'</td> <td>'+whatStatus(data.pageData[i].status)+'</td><td>'+formatDate(data.pageData[i].skTime)+'</td> </tr> '
            );
        }
    })
    var userPhone = $.cookie("userPhone");

}