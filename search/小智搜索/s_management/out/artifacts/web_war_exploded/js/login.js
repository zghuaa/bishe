function check_login() {
    var userID = document.getElementById("userID");
    var passWord = document.getElementById("passWord");
    if (document.getElementById('userID').value===''){
        console.log("userID is null");
        $("#div .login").val('');
    } else {
        console.log("true");
        $.ajax({
            async: true,
            type: "POST",
            url: "http://localhost:8080/LoginServlet",
            contentType: "application/x-www-form-urlencoded",
            data:  userID.value + "," + passWord.value,
            dataType: "json",
            success: function (response) {
                // alert(response)
                if (response['status'] === '1') {
                    localStorage.setItem("userID", userID.value);
                    //跳到写一个页面
                    location.href = '/index.html';
                } else {
                    alert("登录失败，请重新登陆！");
                    $("#div .login").val('');
                }
            },
            error: function () {
                alert("您的账号和密码不匹配，请重新输入");
            }
        });
    }
}