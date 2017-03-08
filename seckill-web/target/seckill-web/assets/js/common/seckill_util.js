/**
 * Created by liuburu on 2017/3/4.
 */
/*工具类：sessionStorage存取工具类*/

function saveJSONToStore(key,jsonData) {
    /*sessionStorage只能保存字符串数据，所以需要进行转换*/
    sessionStorage.setItem(key,JSON.stringify(jsonData));
}

function getJSONFromStore(key) {
    var jsonData = JSON.parse(sessionStorage.getItem(key));
    return jsonData;
}