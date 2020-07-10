//得到tab值并存在第二页
$(document).ready(function () {
    console.log(localStorage.getItem("userEmail"));
    var html = '';
    $.ajax({
        type: "post",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        // url: "http://47.107.64.157:9999/searchType",
        url: "http://localhost:9999/searchType",
        dataType: "json",
        // data:str,
        async: true,
        success: function (data) {
            console.log(data);
            for (var i = 0; i < 3; i++) {
                var ls = eval('(' + data[i] + ')');
                console.log(ls);
                html += "<a  class=\"effect effect-4\" title=\"Learn More\" id='"+ls["type"]+"' onclick='saveFirstVal(this.id)'>" + ls["type"] + "</a>"
            }
            $("#tabs").html(html);
        }
    })
});

function saveFirstVal(value) {
    console.log(value);
    localStorage.setItem("firstType",value);
    // location.href = "http://47.107.64.157:9999/web/html/second.html";
    location.href = "http://localhost:9999/web/html/second.html";
}