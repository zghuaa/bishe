function register() {
    $("#login_div").hide();
    $("#register_div").show();
}

function login() {
    $("#register_div").hide();
    $("#login_div").show();
}


//登陆页面
var log_btn=document.getElementById("login-button");
log_btn.onclick=function tologin(){
    var email = document.getElementById("email");
    var pass = document.getElementById("password");
        if (email.value===''  || pass.value===''){
        alert("邮箱或密码为空！");
        $("#login_div .login").val('');
    } else {
        console.log("true");
        $.ajax({
            async:true,
            type:"POST",
            // url:"http://47.107.64.157:9999/loginUser",
            url:"http://localhost:9999/loginUser",
            data:"pass="+pass.value+"&email="+email.value,
            dataType:"json",
            success:function (response) {
                if (response['status']==='ok'){
                    localStorage.setItem("userEmail", email.value);
                    //跳到写一个页面
                    // location.href='http://47.107.64.157:9999/web/html/first.html';
                    location.href = "../../web/html/first.html"
                } else {
                    alert(response['status']);
                    $("#login_div .login").val('');
                }
            },
            error:function(){
                alert("网络错误请重试！！");
            }
        })
    }
};


//注册页面
var reg_btn=document.getElementById("register-button");
reg_btn.onclick=function toregister(){
    var email = document.getElementById("r_email");
    var password = document.getElementById("r_password");
    if (document.getElementById('r_email').value === '') {
        alert("邮箱为空！");
        $("#register_div .register").val('');
    } else if (document.getElementById('r_password').value === '') {
        alert("设置密码为空！");
        $("#register_div .register").val('');
    } else if (document.getElementById('r_r_password').value === '') {
        alert("请再次输入密码！");
        $("#register_div .register").val('');
    }else if(document.getElementById('r_password').value !==document.getElementById('r_r_password').value){
        alert("两次密码输入不一样!")
    }
    else {
        $.ajax({
            async: true,
            type: "POST",
            url: "http://localhost:9999/registerUser",
            // url: "http://47.107.64.157:9999/registerUser",
            data: "pass=" + password.value + "&email=" + email.value,
            dataType: "json",
            success: function (response) {
                // console.log(response);
                if (response['status'] === 'ok') {
                    alert("请前往邮箱进行验证！");
                    window.open("https://mail.163.com/");
                } else if (response['status'] === 'fail') {
                    alert("邮件发送失败，请重试！");
                } else {
                    alert(response['status'])
                }
            },
            error:function(){
                alert("网络错误请重试！！");
            }
        })
    }
};

