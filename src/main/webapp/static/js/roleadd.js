var roleCode = null;
var roleName = null;

$(function () {
    roleCode = $("#roleCode");
    roleName = $("#roleName");
    addBtn = $("#add");
    backBtn = $("#back");
    //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    roleCode.next().html("*");
    roleName.next().html("*");

    /*
     * 验证
     * 失焦\获焦
     * jquery的方法传递
     */
    roleCode.bind("blur", function () {
        //ajax后台验证--userCode是否已存在
        if (roleCode.val() == "" || roleCode.val() == null) {
            validateTip(roleCode.next(), {"color": "red"}, imgNo + " 角色编码不能为空", false);
        } else {
            $.ajax({
                type: "GET",//请求类型
                url: path + "../../sys/verifyCode",//请求的url
                data: {roleCode: roleCode.val()},//请求参数
                dataType: "json",//ajax接口（请求url）返回的数据类型
                success: function (data) {//data：返回数据（json对象）
                    if (data == true) {//账号已存在，错误提示
                        validateTip(roleCode.next(), {"color": "red"}, imgNo + " 该角色编码已存在", false);
                    } else {//账号可用，正确提示
                        validateTip(roleCode.next(), {"color": "green"}, imgYes + " 该角色编码可以使用", true);
                    }
                },
                error: function (data) {//当访问时候，404，500 等非200的错误状态码
                    validateTip(roleCode.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
                }
            });
        }
    }).bind("focus", function () {
        //显示友情提示
        validateTip(roleCode.next(), {"color": "#666666"}, "* 角色编码是您必须唯一", false);
    }).focus();

    roleName.bind("focus", function () {
        validateTip(roleName.next(), {"color": "#666666"}, "* 角色名长度必须是大于1小于10的字符", false);
    }).bind("blur", function () {
        if (roleName.val() != null && roleName.val().length > 1
            && roleName.val().length < 10) {
            validateTip(roleName.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(roleName.next(), {"color": "red"}, imgNo + " 角色名输入的不符合规范，请重新输入", false);
        }

    });


    addBtn.bind("click", function () {
        if (roleCode.attr("validateStatus") != "true") {
            userCode.blur();
        } else if (roleName.attr("validateStatus") != "true") {
            userName.blur();
        } else {
            if (confirm("是否确认提交数据")) {
                // 此处也可以转化为流的形式然后传过去，通过ajax
                $("#roleForm")
                    .ajaxSubmit(
                        {
                            type: 'post',
                            url: "../../sys/addRole",
                            contentType: "application/x-www-form-urlencoded; charset=utf-8",
                            success: function (data) {
                                if(data=="1"){
                                    alert("上传成功啦");
                                    location.href="../../sys/rolelist.html";
                                }else{
                                    alert("上传失败啦");
                                    return;
                                }
                            }
                        });
            }
        }
    });

    backBtn.on("click", function () {
        if (referer != undefined
            && null != referer
            && "" != referer
            && "null" != referer
            && referer.length > 4) {
            window.location.href = referer;
        } else {
            history.back(-1);
        }
    });
});