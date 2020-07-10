$(document).ready(function () {
    $.ajax({
        // 请求方式
        type: "post",
        url: "/SQServlet",
        // 同步异步
        async: true,
        dataType: "json",
        success: function (data) {
            console.log(data.length);
            var html = "";
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    var ls = eval('('+data[i]+')');
                    // console.log(ls['id']);
                    var status ="未解决";//0
                    //解决1
                    if(ls['status'] === "1"){
                        status = "解决";
                    }
                    html += "<tr>" +
                        "<td>" + ls['date'] + "</td>" +
                        "<td>" + ls['mainType'] + "</td>" +
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
    window.location.href="/views/show.html";
}







