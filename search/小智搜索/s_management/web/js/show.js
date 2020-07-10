$(document).ready(function () {
    console.log(localStorage.getItem("id"));

    $.ajax({
        // 请求方式
        type: "post",
        url: "/SQServlet",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {
            "method":"searchById",
            "type":"fankui",
            "id": localStorage.getItem("id")
        },
        // 同步异步
        async: true,
        dataType: "json",
        success: function (data) {
            console.log(data);
            var yewuleixing = "";
            var fankuishijian = "";
            var email = "";
            var huizhitime = "";
            var asker = "";
            var question = "";
            var answer = "";
            var shenpitime = "";
            var bumenleixing ="";
            // for (var i=0;i<data.length;i++){
            var ls = data;
            bumenleixing = "<input type=\"text\" name=\"yewuleixing\" autocomplete=\"off\" class=\"layui-input\" value='" + ls['mainType'] + "' readonly=\"readonly\">";
            yewuleixing = "<input type=\"text\" name=\"yewuleixing\" autocomplete=\"off\" class=\"layui-input\" value='" + ls['subType'] + "' readonly=\"readonly\">";
            fankuishijian = "<input type=\"text\" name=\"fankuishijian\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['date'] + "\">";
            email = "<input type=\"text\" name=\"email\"  autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['email'] + "\">";
            huizhitime = "<input type=\"text\" name=\"huizhitime\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['tijiaoshijian'] + "\">";
            asker = "<input type=\"text\" name=\"question\"  autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['tijaoren'] + "\">";
            question = "<input type=\"text\" name=\"question\"  autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['title'] + "\">";
            answer = "<textarea class=\"layui-textarea\" readonly=\"readonly\" name=\"answer\">" + ls['answer'] + "</textarea>";
            shenpitime = "<input type=\"text\" name=\"shenpitime\" autocomplete=\"off\" class=\"layui-input\" readonly=\"readonly\" value=\"" + ls['shenpishijian'] + "\">";
            // }
            $("#bumenleixing").html(bumenleixing);
            $("#yewuleixing").html(yewuleixing);
            $("#fankuishijian").html(fankuishijian);
            $("#email").html(email);
            $("#huizhitime").html(huizhitime);
            $("#asker").html(asker);
            $("#question").html(question);
            $("#answer").html(answer);
            $("#shenpitime").html(shenpitime);
        }
    });
});
