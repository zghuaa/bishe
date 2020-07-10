$(document).ready(function () {
    $.ajax({
        // 请求方式
        type:"post",
        url:"http://localhost:8080/IndexServlet",
        data:localStorage.getItem("userID"),
        // 同步异步
        async:true,
        dataType:"json",
        success:function (response) {
            //console.log(data);
            console.log(response);
            if(response['dataInsert']===1){
                document.getElementById('shujuluru').style.display='block';
            }
            if(response['fankui']===1){
                document.getElementById('kehuguanli').style.display='block';
            }
            // localStorage.setItem("userName",userName);
            // localStorage.setItem("system",system);
            var  userName=response['userName'];
            localStorage.setItem("userName",userName);

            var  system=response['system'];
            localStorage.setItem("system",system);


            var department=response['department'];

            if(department==="ysgj"){
                department="运输工具";
            }
            else if(department==="cdsb"){
                department="舱单申报";
            }
            else if(department==="hwsb"){
                department="货物申报";
            }
            else if(department==="sfzf"){
                department="税费支付";
            }
            else if(department==="qtyw"){
                department="其他问题";
            }
            else if(department==="kjds"){
                department="跨境电商";
            }
            localStorage.setItem("department",department);
            console.log(department);


            // var username="";
            // for (var i=0;i<data.length;i++){
            //     var ls=data[i];
            //     username+="<a href=\"javascript:;\">" +
            //         "<img src=\"image/a.png\" class=\"layui-nav-img\">"+ls["userName"]+"</a>" +
            //         "<dl class=\"layui-nav-child\">" +
            //         "<dd><a href=\"\">个人中心</a></dd>" +
            //         "<dd><a href=\"views/login.html\">退出登录</a></dd></dl>";
            //
            // }
            // $("#user").html(username);
        }
    });
});