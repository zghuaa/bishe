$(document).ready(function () {
    $.ajax({
        // 请求方式
        type:"post",
        url:"http://127.0.0.1:8080",
        data:"questionId="+questionId,
        // 同步异步
        async:true,
        dataType:"json",
        success:function (data) {
            var time_fankui="";
            var time_answer="";
            var answerer="";
            var question="";
            var answer="";
            for (var i=0;i<data.length;i++){
                var ls=data[i];
                time_fankui +="<input type=\"text\" name=\"time_fankui\" autocomplete=\"off\" class=\"layui-input\" value="+ls['time_fankui']+" readonly=\"readonly\">";
                time_answer +="<input type=\"text\" name=\"time_answer\" lay-verify=\"required\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\">";
                answerer +="<input type=\"text\" name=\"answerer\" lay-verify=\"required\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\">";
                question +="<input type=\"text\" name=\"question\" lay-verify=\"required\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\">";
                answer +="<textarea class=\"layui-textarea\" readonly=\"readonly\" name=\"answer\">"+['']+"</textarea>";
            }

            $("#time_fankui").html(time_fankui);
            $("#time_answer").html(time_answer);
            $("#answerer").html(answerer);
            $("#question").html(question);
            $("#answer").html(answer);

        }
    });

});

var btn1=document.getElementById("pass");
btn1.onclick=function ok(){
    var questionId=$("#questionId").val();
    var answer=$("#answer").val();
    $.ajax({
        type:"post",
        url:"http://127.0.0.1:8080",
        data:{"questionId":questionId,
            "answer":answer
        },
        // 同步异步
        async:true,
        dataType:"json",
        success:function () {
            alert("提交成功！");
        },
        error:function(){
            alert("提交失败！");
        }
    });
};
var btn2=document.getElementById("nopass");
btn2.onclick=function quxiao() {
    window.location.href = "kehu.html";
};
