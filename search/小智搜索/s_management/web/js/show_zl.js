var id;
$(document).ready(function () {
    console.log(localStorage.getItem("id"));
    $.ajax({
        // 请求方式
        type:"post",
        url:"/SQServlet",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {
            "method":"searchById",
            "type":"ziliao",
            // "id":localStorage.getItem("id")
            "id":localStorage.getItem("id")
        },
        // 同步异步
        async:true,
        dataType:"json",
        success:function (data) {
            console.log(data);
            var bumenleixing="";
            var yewuleixing="";
            var time_answer="";
            var answerer="";
            var question="";
            var answer="";
            var ls=data;
            id = ls['id'];
            var answers="这个负责人很懒,啥也没写";
            if(ls['answer']!==''){
                answers = ls['answer'];
            }

            bumenleixing ="<input id='mainType' type=\"text\" name=\"bumenleixing\" autocomplete=\"off\" class=\"layui-input\" value='"+ls['mainType']+"' readonly=\"readonly\">";
            yewuleixing ="<input id='subType' type=\"text\" name=\"yewuleixing\" autocomplete=\"off\" class=\"layui-input\" value='"+ls['subType']+"' readonly=\"readonly\">";
            time_answer ="<input id='tijiaoshijian' type=\"text\" name=\"time_answer\"  autocomplete=\"off\" class=\"layui-input\" value='"+ls['tijiaoshijian']+"'  readonly=\"readonly\">";
            answerer ="<input id='tijaoren' type=\"text\" name=\"answerer\"  autocomplete=\"off\" class=\"layui-input\" value='"+ls['tijaoren']+"' readonly=\"readonly\">";
            question ="<input id='title' type=\"text\" name=\"question\" autocomplete=\"off\" class=\"layui-input\" value='"+ls['question']+"' readonly=\"readonly\">";
            answer ="<textarea id='answers' class=\"layui-textarea\" readonly=\"readonly\" name=\"answer\">"+answers+"</textarea>";

            $("#bumenleixing").html(bumenleixing);
            $("#yewuleixing").html(yewuleixing);
            $("#time_answer").html(time_answer);
            $("#answerer").html(answerer);
            $("#question").html(question);
            $("#answer").html(answer);

        }
    });

});


$("#pass").click(function pass(){
    var mainType = document.getElementById("mainType").value;
    var subType = document.getElementById("subType").value;
    var title = document.getElementById("title").value;
    var answer = document.getElementById("answers").value;
    var status="1";
    $.ajax({
        async: true,
        type: "post",
        url: "/InsertInfServlet",
        contentType: "application/json;charset=utf-8",
        data:JSON.stringify({method:'addInformation',type:'ziliao',id:localStorage.getItem("id"),mainType:mainType,
            subType:subType,answer:answer,id:id,status:status,title:title}),
        dataType: "json",
        success: function (data) {
            console.log(data);
            alert("已通过！");
        }
    });
});
$("#nopass").click(function nopass() {
    var status="-1";
    $.ajax({
        type:"post",
        url:"/SQServlet",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data:{
            "method":"updateStatusById",
            "type":"ziliao",
            "id":id,
            "result":status
        },
        // 同步异步
        async:true,
        dataType:"json",
        success:function () {
            alert("已驳回！");
        }
    });
});
