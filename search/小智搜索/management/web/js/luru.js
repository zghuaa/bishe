var btn1=document.getElementById("submit");
btn1.onclick=function submit(){
    var question=$("#Que").val();
    var answer=$("#Ans").val();

    // var question=document.getElementById("question");
    // var answer=document.getElementById("answer");
    if (question===''||answer===''){
        console.log("question or answer is null");
        alert("空值！");
        // $("#div .login").val('');
    }else {
        console.log(true);
        $.ajax({
            type:"post",
            url:"http://localhost:8080/InsertDataServlet",
            contentType: "application/x-www-form-urlencoded",
            data:question+","+ answer+","+localStorage.getItem("department")+","+localStorage.getItem("system")+","+localStorage.getItem("userName"),
            // 同步异步
            async:true,
            dataType:"json",
            success:function (response) {

                if (response['status'] === '1') {
                    alert("提交成功！");
                } else {
                    alert("提交失败!");
                    // $("#div .login").val('');
                }
            },
            error:function(){
                alert("error！");
            }
        });
    }
    //var question=$("#Que").val();
    //var answer=$("#Ans").val();
    // console.log(question);
    // console.log(answer);

};