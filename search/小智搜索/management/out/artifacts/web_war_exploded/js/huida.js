$(document).ready(function () {
    $.ajax({
        // 请求方式
        type: "post",
        url: "/NSQServlet",
        // contentType: 'application/json;charset=utf-8',
        data:JSON.stringify({method:'searchById',type:'fankui',id:localStorage.getItem("id")}),
        // 同步异步
        async: true,
        dataType: "json",
        success: function (data) {
            console.log(data);
            var html = "";
            var time = "";
            var ls = data;
            html = "<input type=\"text\" name=\"title\"  autocomplete=\"off\" class=\"layui-input\" value='" + ls['question'] + "'  readonly=\"readonly\">";
            time = "<input type=\"text\" name=\"date\" autocomplete=\"off\" class=\"layui-input\" value='" + ls['date'] + "'  readonly=\"readonly\">";
            $("#question").html(html);
            $("#time").html(time);
        }
    });

});

$("#ok").click(function ok(){
      var questionId=localStorage.getItem("id");
      var answer=$("#answer").val();
      var tijiaoren=localStorage.getItem("userName");
      console.log(questionId);
      console.log(answer);
      console.log(tijiaoren);
      $.ajax({
          type:"post",
          url:"/FanKuiServlet",
          contentType: "application/json; charset=UTF-8",
          data:JSON.stringify({id:questionId,tijiaoren:tijiaoren,answer:answer}),
          // 同步异步
          async:true,
          dataType:"json",
          success:function (response) {
              if(response['status'] === '1') {
                  console.log(data);
                  alert("提交成功！");
              }
              },
          error:function(){
              alert("提交失败！");
              }
      });
   });

  $("#quxiao").click(function quxiao() {
      location.href = "huida_f.html";
  });
  $("#chongzhi").click(function chongzhi() {
      window.location.reload();
  });
