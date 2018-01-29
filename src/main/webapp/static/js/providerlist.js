var providerObj;

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeProv').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeProv').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}
$(function(){
	$(".providerTable").on("click",".viewProvider",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"../../sys/providerView/"+ obj.attr("proid");
	});

	$(".providerTable").on("click",".modifyProvider",function(){
		var obj = $(this);
		window.location.href="../../sys/toProviderEdit/"+ obj.attr("proid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteProvider(providerObj);
	});

	$(".providerTable").on("click",".deleteProvider",function(){
		providerObj = $(this);
		changeDLGContent("你确定要删除供应商【"+providerObj.attr("proname")+"】吗？");
		openYesOrNoDLG();
	});

	/*供应商管理*/
    /*自写代码。，分页*/
    // $(".right").on("click",".first",function(){
    //     page(1);
    // })
    // $(".right").on("click",".pre",function(){
    //     var pageNo=this.id;
    //     page(pageNo);
    // })
    // $(".right").on("click",".next",function(){
    //     var pageNo=this.id;
    //     page(pageNo);
    // })
    // $(".right").on("click",".last",function(){
    //     var pageNo=this.id;
    //     page(pageNo);
    // })

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
    /**
     * 分页的方法
     */
    function page(pageNo) {
        var proCode=$("#proCode").val();
        var proName=$("#proName").val();
        var data="proCode="+proCode+"&proName="+proName+"&currPageNo="+pageNo;
        $.getJSON("../../sys/providerPageList",data,function(data){
            var providerList=data.newPage;  //获得页的集合
            $(".providerTable").html("");
            $(".page").remove();
            $(".providerTable").append("<tr class=\"firstTr\">\n" +
                "<th width=\"10%\">供应商编码</th>\n" +
                "<th width=\"20%\">供应商名称</th>\n" +
                "<th width=\"10%\">联系人</th>\n" +
                "<th width=\"10%\">联系电话</th>\n" +
                "<th width=\"10%\">传真</th>\n" +
                "<th width=\"10%\">创建时间</th>\n" +
                "<th width=\"30%\">操作</th>\n" +
                "</tr>");
            //循环遍历集合
            for(var i=0;i<providerList.length;i++){
                var provider=providerList[i];
                $(".providerTable").append("<tr><td><span>"+provider.proCode+"</span></td><td><span>"+provider.proName+"</span></td> <td><span>"+provider.proContact+"</span></td><td><span>"+provider.proPhone+"</span></td>" +
                    "<td><span>"+provider.proFax+"</span></td><td><span>"+provider.creationDate+"</span></td>" +
                    "<td><span><a class=\"viewProvider\" href=\"javascript:;\" proid='"+provider.id+"' proname='"+provider.proName +"'><img src='/static/images/read.png' alt=\"查看\" title=\"查看\"/></a></span>" +
                    "<span><a class=\"modifyProvider\" href='javascript:;' proid='"+provider.id+"' proname='"+provider.proName +"'><img src='/static/images/xiugai.png' alt=\"修改\" title=\"修改\"/></a></span>" +
                    "<span><a class=\"deleteProvider\" href=\"javascript:;\" proid='"+provider.id+"'proname='"+provider.proName +"'><img src='/static/images/schu.png' alt=\"删除\" title=\"删除\"/></a></span></td></tr>");

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

    //供应商管理页面上点击删除按钮弹出删除框(providerlist.jsp)
    function deleteProvider(obj){
        $.getJSON("../../sys/delprovider","proid="+obj.attr("proid"),function(data){
            if(data.delResult == "true"){//删除成功：移除删除行
                cancleBtn();
                page(1);  //从新刷新页面
                //obj.parents("tr").remove();
            }else if(data.delResult == "false"){//删除失败
                //alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
                changeDLGContent("对不起，删除供应商【"+obj.attr("proname")+"】失败");
            }else if(data.delResult == "notexist"){
                //alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
                changeDLGContent("对不起，供应商【"+obj.attr("proname")+"】不存在");
            }else if(data.delResult == "existBill"){
                //alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
                changeDLGContent("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.count+"】条订单，不能删除");
            }
        });
    }

    /**
     * 初始化方法
     */
    page(1);


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