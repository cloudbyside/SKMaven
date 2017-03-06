/**
 * Created by liuburu on 2017/3/2.
 */

/*注册进入列表事件*/
function registerLoginEvent(loginBtn) {
    var pageNo = seckillConfig.curPageNo;
    var pageSize = seckillConfig.pageSize;
    $(loginBtn).parent().attr("href",seckillConfig.URL.list(pageNo,pageSize,seckillConfig.sortType,"desc"));
}
