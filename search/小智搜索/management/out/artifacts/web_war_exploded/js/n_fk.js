$(document).ready(function () {
    var depart=localStorage.getItem("department");
    $.ajax({
        // 请求方式
        type: "post",
        url: "/NSQServlet",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({method:'searchQByType',type:'fankui',mainType:depart}),
        // 同步异步
        async: true,
        dataType: "json",
        success: function (data) {
            var html = "";
            if (data === null || data.length === 0){
                $("#fankui").html("暂无数据");
            } else {
                for (var i = 0; i < data.length; i++) {
                    var ls = eval('('+data[i]+')');
                    var status ="未解决";//0
                    //解决1
                    if(ls['status'] === "1"){
                        status = "解决";
                    }
                    html += "<tr>" +
                        "<td>" + ls['date'] + "</td>" +
                        "<td>" + ls['subType'] + "</td>" +
                        "<td>" + ls['title'] + "</td>" +
                        "<td>" + ls['email'] + "</td>" +
                        "<td><button class='layui-btn' id='"+ls['id']+"' onclick='searchQ(this.id)'>" + status + "</button></td></tr>"
                }
                $("#fankui").html(html);
            }
        }
    });

});


function searchQ(id){
    localStorage.setItem("id", id);
    window.location.href="/views/n_fk_fk.html";
}







