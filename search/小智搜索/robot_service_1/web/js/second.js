
$(document).ready(function () {
    var mainType = localStorage.getItem("firstType");
    console.log(localStorage.getItem("firstType"));
    var html = '';
    $.ajax({
        // 请求方式
        type: "post",
        // url: "http://47.107.64.157:9999/searchSubType",
        url: "http://localhost:9999/searchSubType",
        data: "type=myType"+ "&mainType="+mainType,
        dataType: "json",
        // 同步异步
        async: true,
        success: function (data) {
            console.log(data);
            if (data.length === 0) {
                html = "暂无数据"
            } else {
                for (var i = 0; i < data.length; i++) {
                    // var ls = eval('(' + data[i] + ')');
                    var ls = data[i];
                    console.log(ls);
                    html += "<a class='effect effect-4' title=\"Learn More\" id='" + ls['subType'] + "' onclick='saveSecondVal(this.id)'>" + ls['subType'] + "</a>"
                }
            }
            $(".button-effect").html(html);
        }
    });
});

function saveSecondVal(value) {
    console.log(value);
    localStorage.setItem("secondTitle",value);
    // location.href = "http://47.107.64.157:9999/web/html/third.html"
    location.href = "../../web/html/third.html"
}