/**
 * Created by liuburu on 2017/3/2.
 */
/**
 * 用来记录分页相关参数,如下为默认状态；它会在页面初始化的时候设置到sessionStoreage中
 * @type {{}}
 */

var seckillConfig={
    /*存放ajax数据访问接口*/
    URL:{
        allTotal:function () {
          return "/seckill/query/totals";
        },
        list:function (pageNo, pageSize,sort,order) {
            return "/seckill/list?pageSize="+pageSize+"&pageNo="+pageNo+"&sort="+sort+"&order="+order;
        },
        query:function (pageNo, pageSize,sort,order) {
            return "/seckill/query?pageSize="+pageSize+"&pageNo="+pageNo+"&sort="+sort+"&order="+order;
        },
        now:function () {
            return "/seckill/time/now"
        },
        detail:function (id) {
            return "/seckill/"+id+"/detail";
        },
        exposer:function(id){
          return "/seckill/"+id+"/exposer"
        },
        excution:function (id, md5) {
            return "/seckill/"+id+"/"+md5+"/execution";
        }
    },
    curPageNo:1,
    pageSize:5,
    sortType:0,
    orderType_1:"desc",
    orderType_2:"desc",
    /*是否第一次进入列表页面，判断是用模型加载数据，还是用异步加载数据*/
    firstTime:true,
    phoneReg :/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/,
    /*所有的分页总数，在sessionStoreage中保存*/
    totalsKey:"total_records",

    /*分页页码总数*/
    createNums:0,
    willBeginNums:0,
    willEndNums:0

}