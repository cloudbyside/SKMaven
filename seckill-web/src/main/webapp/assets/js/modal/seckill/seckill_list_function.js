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
    console.log("当前位置==>" + new_page_index);
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
function registerSortBtn(willBegin, willEnd) {
    var pageNo = 1;//第一页
    var pageSize = seckillConfig.pageSize;
    willBegin.bind("click", function () {
        $.get(seckillConfig.URL.allTotal(),{},function (totals) {
            var totalNum = totals.data.willBegin;
            seckill.pagination.init(totalNum,pageSize,pageNo,1);
        });
    });
    willEnd.bind("click", function () {
        $.get(seckillConfig.URL.allTotal(),{},function (totals) {
            var totalNum = totals.data.willEnd;
            seckill.pagination.init(totalNum,pageSize,pageNo,2);
        })
    });
}


/*日期格式化函数*/
function formatDate(strTime) {
    var date = new Date(strTime);
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " "
        + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
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
                "<td><a href='" + seckillConfig.URL.detail(data.pageData[i].seckillId) + "' class='btn btn-info'>详情</a></td>" +
                "  </tr>");
        }
        console.log(data);
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
