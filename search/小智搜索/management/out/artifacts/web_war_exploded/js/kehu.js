$(document).ready(function () {
    $.ajax({
        // 请求方式
        type:"post",
        url:"/NSQServlet",
        data:{
            "method":"",
            "type":"fankui"
        },
        contentType:"application/json;charset=utf-8",
        // 同步异步
        async:true,
        dataType:"json",
        success:function (data) {
            var html="";
            for (var i=0;i<data.length;i++){
                var ls=data[i];
                html+="<tr>" +
                    "<td>"+ls['questionId']+"</td>" +
                    "<td>"+ls['question']+"</td>" +
                    "<td>"+ls['answer']+"</td>" +
                    "<td><button class='layui-btn' id='status' onclick='huida(ls['id'])'>"+ls['status']+"</button></td></tr>"
            }
            $("#kehu").html(html);
        }
    });

});



var shenpi_btn=document.getElementById("status");
function huida(questionId){
    localStorage.setItem("questionId", questionId);
    window.location.href="/Xz/views/huida.html";
};








