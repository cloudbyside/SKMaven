/**
 * Created by liuburu on 2017/3/2.
 */
var seckill = {
    /*分页插件初始化*/
    pagination: {
        init: function (totalNum, perPageNum, pageNo,sortType) {
           // console.log("开始初始化分页插件！");
            seckillConfig.sortType = sortType;

            paginationInit(totalNum, perPageNum, pageNo);//调用分页初始化函数
           // console.log("初始化分页插件完毕！");
        }
    },
    /*排序按钮事件监听*/
    initSortBtn:function () {
       // console.log("分类查询按钮事件注册开始");
        var willBeginBtn = $("#willBeginBtn");
        var willEndBtn = $("#willEndBtn");
        var defaultBtn = $("#defaultBtn");
        var userBtn = $("#userBtn");
        var loginOutBtn = $("#loginOutBtn");
        var hotIngBtn = $("#hotIngBtn");
        registerSortBtn(willBeginBtn,willEndBtn,defaultBtn,userBtn,loginOutBtn,hotIngBtn);
      //  console.log("分类查询按钮事件注册结束");
    },
    
    /*判断用户是否登录*/
    userLoginCheck: function () {
        if ($.cookie("userPhone") == undefined||$.cookie("userPhone")=="null") {
            //弹出模态框进行登陆
            $("#killPhoneModal").modal({
                show: true,  //显示弹出层
                backdrop: 'static', //禁止位置关闭
                keyboard: false //关闭键盘事件
            });
            //模态框相关事件绑定
            seckill.modal.registEvent();
        } else {
            //给出一条用户已经登录的提示
            var userPhone = $.cookie("userPhone");
            $("#tipInfoBox").hide().html(getTipDom("当前用户手机:" + userPhone)).fadeIn(400).fadeOut(3500);
        }
    },
    modal: {
        registEvent: function () {
            $("#killPhoneBtn").bind("click", function () {
                var userPhone = $("#killPhoneKey").val();
                if (userPhone.length == 0) {//不能为空
                    $("#killPhoneMessage").hide().html("手机号不能为空!").show(300);
                } else if (!seckillConfig.phoneReg.test(userPhone)) {//手机号格式不正确
                    $("#killPhoneMessage").hide().html("手机号格式不正确!").show(300);
                } else {//提示一条登录成功的信号,并且使用cookie保存用户信息
                    $("#tipInfoBox").hide().html(getTipDom("当前用户手机:" + userPhone)).fadeIn(400).fadeOut(3500);
                    $.cookie("userPhone", userPhone, {path: '/seckill', expires: 1});
                    window.location.reload();
                }
            });

        }
    },
    /*计时交互逻辑
     * 首先请求目标商品的暴露地址信息，
     * 如果没有暴露，则说明该商品秒杀活动未开始；
     * 如果有暴露，则判断该商品的秒杀时间
     *                如果时间在活动期间内，那么我们就可以执行秒杀
     *                （绑定按钮事件，执行秒杀，异步发送秒杀请求，返回秒杀结果，
     *                  如果为成功，则给出提示，如果失败，则返回失败信息【重复秒杀，库存为空】
     *                    ）
     *                如果时间在活动期间后，那么我们就判断该活动已经结束了
     * */
    seckTimeCheck: function (id) {

        $.get(seckillConfig.URL.exposer(id), {}, function (exposer) {
            var seckBox = $("#seckill-box");
            if (exposer.success == true && exposer.data.exposed) {//有暴露数据，则判断时间在活动中，则生成秒杀按钮
                $.get(seckillConfig.URL.now(), {}, function (serverTime) {//防止客户端页面停留导致时间超过活动期间
                    if (serverTime.data > exposer.data.seckill.endTime) {
                        seckBox.hide().html("失败信息:很遗憾，活动已经结束了！").show();
                    } else if (serverTime.data < exposer.data.seckill.endTime && serverTime.data > exposer.data.seckill.startTime) {//秒杀进行中==>1.生成秒杀按钮，注册事件
                        seckill.dealSeckBeginAction(exposer, seckBox);
                    } else {//秒杀未开始==>1.开始计时 2.生成秒杀按钮，注册事件
                        var killTime = new Date(exposer.data.seckill.startTime);//TODO
                        seckBox.countdown(killTime, function (event) {
                            //时间格式
                            var format = event.strftime('秒杀倒计时; %D天 %H时 %M分 %S秒');
                            seckBox.html(getCountDownDom(format));
                            /*时间完成后回调事件*/
                        }).on('finish.countdown', function () {
                            //获取秒杀地址，执行秒杀
                            seckill.dealSeckBeginAction(exposer, seckBox);
                        });
                    }
                });
            } else if (exposer.success == false && exposer.stateNum == 1005) {//没有暴露数据，【秒杀已经结束(state=1005)】，提示错误信息
                $("#seckill-box").hide().html("<h2>秒杀已经结束啦~</h2>").show();
            }
        })
    },
    dealSeckBeginAction: function (exposer, seckBox) {//生成秒杀按钮，注册事件监听（用服务器时间进行秒杀商品）
        seckBox.hide().html('<button class="btn btn-primary btn-lg col-md-2 col-md-push-5" id="killBtn">秒杀</button>');
        $("#killBtn").one("click", function () {
            //先禁用秒杀按钮
            $(this).addClass('disabled');
            $.get(seckillConfig.URL.now(), {}, function (time) {//防止客户端页面停留导致时间超过活动期间
                if (time.data > exposer.data.seckill.endTime) {
                    seckBox.hide().html("失败信息:很遗憾，活动已经结束了！").show();
                } else {//时间与服务器时间一直，则可以执行秒杀动作
                    var md5Str = exposer.data.md5;
                    var seckillId = exposer.data.seckillId;
                    $.post(seckillConfig.URL.excution(seckillId, md5Str), {}, function (exceteResult) {
                        if (exceteResult.success == true) {//秒杀成功
                            seckBox.hide().html("账号:" + exceteResult.data.successKilled.userPhone + "秒杀成功").show();
                        } else {//秒杀失败【重复秒杀、库存没了】
                            seckBox.hide().html("失败信息:" + exceteResult.stateMsg).show();
                        }
                    })
                }
            });
        });
        seckBox.show();
    }

}
