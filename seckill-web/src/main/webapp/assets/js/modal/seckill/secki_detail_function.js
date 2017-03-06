/**
 * Created by liuburu on 2017/3/2.
 */

/*创建友好提示Dom元素*/

function getTipDom(showMsg) {
    var tipDom = "" +
        "<h3>" +
        "<p>" + showMsg + "</p>" +
        "</h3>" ;
    return tipDom;
}

function getCountDownDom(timeFmt){
     var timeDom ='<span class="glyphicon glyphicon-time">'+timeFmt+'</span><span class="glyphicon"></span>';
     return timeDom;
}