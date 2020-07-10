var email = localStorage.getItem("userEmail");
var firstType = localStorage.getItem("firstType");
var secondTitle = localStorage.getItem("secondTitle");
var thirdId = localStorage.getItem("thirdId");


//将该title的点击数加一
$(document).ready(function () {
    var html = '';
    $.ajax({
        type: "post",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        // url: "http://47.107.64.157:9999/updateTitle",
        url: "http://localhost:9999/upDateScore",
        data: "type=" + firstType+"&id="+thirdId,
        dataType: "json",
        async: true,
        success: function (data) {

        }
    })
});