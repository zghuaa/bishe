$(document).ready(function () {
    console.log(localStorage.getItem("id"));
    $.ajax({
        // 请求方式
        type: "post",
        url: "/NSQServlet",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({method:'searchById',type:'ziliao',id:localStorage.getItem("id")})
        ,
        // 同步异步
        async: true,
        dataType: "json",
        success: function (data) {
            console.log(data);
            var yewuleixing = "";
            var tijiaoshijian = "";
            var tijiao_person = "";
            var question = "";
            // var answer = "";

            var ls = data;
            var answers = "这个负责人很懒,啥也没写";
            if (ls['answer'] !== '') {
                answers = ls['answer'];
            }
            var ls = data;
            yewuleixing = "<input type=\"text\" name=\"yewuleixing\" autocomplete=\"off\" class=\"layui-input\" value='" + ls['subType'] + "' readonly=\"readonly\">";
            tijiaoshijian = "<input type=\"text\" name=\"tijiaoshijian\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['date'] + "\">";
            tijiao_person = "<input type=\"text\" name=\"tijiao_person\"  autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['tijaoren'] + "\">";
            question = "<input type=\"text\" name=\"question\"  autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['title'] + "\">";
            answer = "<textarea class=\"layui-textarea\" readonly=\"readonly\" name=\"answer\">" + answers + "</textarea>";

            $("#yewuleixing").html(yewuleixing);
            $("#tijiaoshijian").html(tijiaoshijian);
            $("#tijiao_person").html(tijiao_person);
            $("#question").html(question);
            $("#answer").html(answer);}
    });
});
