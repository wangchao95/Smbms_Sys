var roleName = null;

$(function () {
    roleName = $("#roleName");
    addBtn = $("#add");
    backBtn = $("#back");
    //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    roleName.next().html("*");

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
        if (roleName.attr("validateStatus") != "true") {
            userName.blur();
        } else {
            if (confirm("是否确认提交数据")) {
                // 此处也可以转化为流的形式然后传过去，通过ajax
                $("#roleForm")
                    .ajaxSubmit(
                        {
                            type: 'post',
                            url: "../../sys/editRole",
                            contentType: "application/x-www-form-urlencoded; charset=utf-8",
                            success: function (data) {
                                if(data=="1"){
                                    alert("修改成功啦");
                                    location.href="../../sys/rolelist.html";
                                }else{
                                    alert("修改失败啦");
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