$(document).ready(function () {

    $('input').iCheck({
        checkboxClass: 'icheckbox_minimal-green',
        radioClass: 'iradio_minimal-green',
        increaseArea: '20%'
    });

    var $formPanelTwo = $('.form-panel.two');

    var panelOne = $formPanelTwo.height();
    var panelTwo = $formPanelTwo[0].scrollHeight;

    $formPanelTwo.not('.form-panel.two.active').on('click', function (e) {
        e.preventDefault();

        $('.form-toggle').addClass('visible');
        $('.form-panel.one').addClass('hidden');
        $('.form-panel.two').addClass('active');
        $('.form').animate({
            'height': panelTwo
        }, 200);
    });

    $('.form-toggle').on('click', function (e) {
        e.preventDefault();
        $(this).removeClass('visible');
        $('.form-panel.one').removeClass('hidden');
        $('.form-panel.two').removeClass('active');
        $('.form').animate({
            'height': panelOne + 92
        }, 200);
    });

});


function reloadCode() {
    $("#validateCodeImg").attr("src", ctx + "gifCode?data=" + new Date() + "");
}

function login() {
    var $loginButton = $("#loginButton");
    var username = $(".one input[name='username']").val().trim();
    var password = $(".one input[name='password']").val().trim();
    var code = $(".one input[name='code']").val().trim();
    var rememberMe = $(".one input[name='rememberme']").is(':checked');
    if (username === "") {
        $MB.n_warning("请输入用户名！");
        return;
    }
    if (password === "") {
        $MB.n_warning("请输入密码！");
        return;
    }
    if (code === "") {
        $MB.n_warning("请向右滑动完成验证！");
        return;
    }
    if (code === "error") {
        $MB.n_warning("验证码错误！");
        return;
    }
    $loginButton.html("").append("<div class='login-loder'><div class='line-scale'><div></div><div></div><div></div><div></div><div></div></div></div>");

    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            "username": username,
            "password": password,
            "code": code,
            "rememberMe": rememberMe
        },
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                location.href = ctx + 'index';
            } else {
                if (r.msg === '验证码错误！') reloadCode();
                $MB.n_warning(r.msg);
                $loginButton.html("登录");
            }
        }
    });
}

//这里的初始化是滑动&拼图的标志slideVerify
$('#mpanel1').slideVerify({

    type : 1,
    vOffset : 5,
    explain : '向右滑动完成验证',
    barSize : {
        width : '100%',
        height : '40px',
    },
    ready : function() {
    },
    success : function() {
        $(".one input[name='code']").val("ok");
        // alert('验证成功！');
    },
    error : function() {
        $(".one input[name='code']").val("error");
        // alert('验证码不匹配！');
    }

});


function regist() {
    var username = $(".two input[name='username']").val().trim();
    var password = $(".two input[name='password']").val().trim();
    var cpassword = $(".two input[name='cpassword']").val().trim();
    var vipType = $('#vipTypeDiv input[name="vipType"]:checked ').val();
    if(!(/^1\d{10}$/.test(username))){
        $MB.n_warning("用户名必须为手机号！");
        return ;
    }
    if (username === "") {
        $MB.n_warning("用户名不能为空！");
        return;
    } else if (username.length > 20) {
        $MB.n_warning("用户名长度不能超过20个字符！");
        return;
    } else if (username.length < 3) {
        $MB.n_warning("用户名长度不能少于3个字符！");
        return;
    }
    if (password === "") {
        $MB.n_warning("密码不能为空！");
        return;
    }
    if (cpassword === "") {
        $MB.n_warning("请再次输入密码！");
        return;
    }
    if (cpassword !== password) {
        $MB.n_warning("两次密码输入不一致！");
        return;
    }
    $.ajax({
        type: "post",
        url: ctx + "user/regist",
        data: {
            "username": username,
            "password": password,
            "vipType": vipType,
        },
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $MB.n_success("注册成功，请登录");
                $(".two input[name='username']").val("");
                $(".two input[name='password']").val("");
                $(".two input[name='cpassword']").val("");
                $('.form-toggle').trigger('click');
            } else {
                $MB.n_warning(r.msg);
            }
        }
    });
}

function showRegist() {
    var $formPanelTwo = $('.form-panel.two');
    var panelTwo = $formPanelTwo[0].scrollHeight;

    $('.form-toggle').addClass('visible');
    $('.form-panel.one').addClass('hidden');
    $('.form-panel.two').addClass('active');
    $('.form').animate({
        'height': panelTwo
    }, 200);
}

document.onkeyup = function (e) {
    if (window.event)
        e = window.event;
    var code = e.charCode || e.keyCode;
    if (code === 13) {
        login();
    }
};