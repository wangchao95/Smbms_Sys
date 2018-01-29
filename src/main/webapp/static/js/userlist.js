var userObj;

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeUse').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	//通过jquery的class选择器（数组）
	//对每个class为viewUser的元素进行动作绑定（click）
	/**
	 * bind、live、delegate
	 * on
	 */
	//查询用户对象
	$(".providerTable").on("click",".viewUser",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"../../sys/viewUser/"+ obj.attr("userid");
	});

	//修改用户对象
    $(".providerTable").on("click",".modifyUser",function(){
		var obj = $(this);
		window.location.href=path+"../../sys/toUserEdit/"+ obj.attr("userid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteUser(userObj);
	});
	//删除用户对象
    $(".providerTable").on("click",".deleteUser",function(){
		userObj = $(this);
		changeDLGContent("你确定要删除用户【"+userObj.attr("username")+"】吗？");
		openYesOrNoDLG();
	});
    /**
     * 分页的方法
     */
    function page(pageNo) {
        var queryname=$("#queryname").val();
        var queryUserRole=$("#queryUserRole").val();
        var data="queryname="+queryname+"&queryUserRole="+queryUserRole+"&pageIndex="+pageNo;
        $.getJSON("../../sys/userListPage",data,function(data){
            var userList=data.newPage;  //获得页的集合
            $(".providerTable").html("");
            $(".page").remove();
            $(".providerTable").append("<tr class=\"firstTr\">\n" +
                "<th width=\"10%\">用户编码</th>\n" +
                "<th width=\"20%\">用户名称</th>\n" +
                "<th width=\"10%\">性别</th>\n" +
                "<th width=\"10%\">年龄</th>\n" +
                "<th width=\"10%\">电话</th>\n" +
                "<th width=\"10%\">用户角色</th>\n" +
                "<th width=\"30%\">操作</th>\n" +
                "</tr>");
            //循环遍历集合
            for(var i=0;i<userList.length;i++){
                var user=userList[i];
                var gender=user.gender==1?"女":"男";
                $(".providerTable").append("<tr><td><span>"+user.userCode+"</span></td><td><span>"+user.userName+"</span></td> <td><span>"+gender+"</span></td><td><span>"+user.age+"</span></td>" +
                    "<td><span>"+user.phone+"</span></td><td><span>"+user.role.roleName+"</span></td>" +
                    "<td><span><a class=\"viewUser\" href=\"javascript:;\" userid='"+user.id+"' username='"+user.userName+"'><img src='/static/images/read.png' alt=\"查看\" title=\"查看\"/></a></span>" +
                    "<span><a class=\"modifyUser\" href=\"javascript:;\" userid='"+user.id+"' username='"+user.userName+"'><img src='/static/images/xiugai.png' alt=\"修改\" title=\"修改\"/></a></span>" +
                    "<span><a class=\"deleteUser\" href=\"javascript:;\" userid='"+user.id+"' username='"+user.userName+"'><img src='/static/images/schu.png' alt=\"删除\" title=\"删除\"/></a></span></td></tr>");

            }
            var $div=$("<div class='page'></div>").appendTo($(".right"));
            var $operArea=$("<p align='center'>当前页数:["+data.currPageNo+"/"+data.totalPageCount+"]&nbsp;</p>").appendTo($div);
            if(data.currPageNo>1){
                var $first=$("<a href=\"javascript:;\" id='1' class=\"first\" >首页</a>");
                var $perv=$("<a href=\"javascript:;\" class='pre' id='"+(data.currPageNo-1)+"'>上一页</a>");
                $div.append($first).append("&nbsp;").append($perv);   //添加到元素里面
            }
            //如果当前页数小于总页数
            if(data.currPageNo<data.totalPageCount){
                var $thred=$("<a href=\"javascript:;\" class='next'  id='"+(data.currPageNo+1)+"'>下一页</a>");
                var $last=$("<a href=\"javascript:;\" class='last'  id='"+data.totalPageCount+"'>末页</a>");
                $div.append($thred).append("&nbsp;").append($last);   //添加到元素里面
            }
            var $go=$("<span style='float: right'><label>跳转至</label>\n" +
                "\t<input type=\"text\" name=\"inputPage\" totalPage='"+data.totalPageCount+"' id=\"inputPage\" value='' class=\"page-key\" />页\n" +
                "\t<button type=\"button\" class=\"page-btn\">GO</button>\n" +
                "\t\t</span>");
            $div.append($go);
        });
    }
    page(1);  //初始化页面的用户



    //用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
    function deleteUser(obj){
        $.ajax({
            type:"GET",
            url:path+"../../sys/deluser",
            data:{uid:obj.attr("userid")},
            dataType:"json",
            success:function(data){
                if(data.delResult == "true"){//删除成功：移除删除行
                    cancleBtn();
                    alert("删除成功啦");
                    page(1);
                }else if(data.delResult == "existAddress"){//删除失败，存在地址
                    //alert("对不起，删除用户【"+obj.attr("username")+"】失败");
                    changeDLGContent("对不起，用户【"+obj.attr("username")+"】下有"+data.count+"个地址");
                }else if(data.delResult == "notexist"){
                    changeDLGContent("对不起，用户【"+obj.attr("username")+"】不存在");
                }
            },
            error:function(data){
                //alert("对不起，删除失败");
                changeDLGContent("对不起，删除失败");
            }
        });
    }

    /**
     * 点击上下页来显示列表
     */
    $(".right").on("click",".last,.first,.pre,.next",function(){
        var pageNo=this.id;
        page(pageNo);
    })

    /**
     * 搜索的方法
     */
    $("#searchbutton").click(function(){
        page(1);
    })


    //跳转的页面
    function page_nav(num){
        page(num);
    }
    //点击go去的页面
    $(".right").on("click",".page-btn",function(){
        var inputPage=$("#inputPage").val();
        var tatalPage= $("#inputPage").attr("totalPage");
        jump_to(inputPage,tatalPage);
    });

    //验证输入的是否合法
    function jump_to(num,totalPage){
        //alert(num);
        //验证用户的输入
        var regexp=/^[1-9]\d*$/;
        if(!regexp.test(num)){
            alert("请输入大于0的正整数！");
            return false;
        }else if((num-totalPage) > 0){
            alert("请输入小于总页数的页码");
            return false;
        }else{
            page_nav(num);
        }
    }
});