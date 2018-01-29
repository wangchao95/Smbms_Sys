var billObj;

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){

   	$(".providerTable").on("click",".viewBill",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href="../../sys/billView/"+ obj.attr("billid");
	});
	

	$(".providerTable").on("click",".modifyBill",function(){
		var obj = $(this);
		window.location.href="../../sys/modifyBill/"+ obj.attr("billid");
	});
	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteBill(billObj);
	});

	$(".providerTable").on("click",".deleteBill",function(){
		billObj = $(this);
		changeDLGContent("你确定要删除订单【"+billObj.attr("billcc")+"】吗？");
		openYesOrNoDLG();
	});

	/*自写代码。，分页,后面的条件是多个点击事件用的*/
    $(".right").on("click",".first,.pre,.next,.last",function(){
        var pageNo=this.id;
        page(pageNo);
    })
    // /**
    //  * 搜索的方法
    //  */
    $(".searchbutton").click(function(){
        var pageNo=this.id;
        page(pageNo);
    })
    /**
	 * 分页的方法
     */
	function page(pageNo) {
        var proName=$("#productName").val();
        var providerId=$("#providerId").val();
        var isPay=$("#isPay").val();
        var data="proName="+proName+"&providerId="+providerId+"&isPay="+isPay+"&currPageNo="+pageNo;
        $.getJSON("../../sys/billlistPageList",data,function(data){
			var billList=data.newPage;  //获得页的集合
			$(".providerTable").html("");
			$(".page").remove();
			$(".providerTable").append("<tr class=\"firstTr\">\n" +
                "<th width=\"10%\">订单编码</th>\n" +
                "<th width=\"20%\">商品名称</th>\n" +
                "<th width=\"10%\">供应商</th>\n" +
                "<th width=\"10%\">订单金额</th>\n" +
                "<th width=\"10%\">是否付款</th>\n" +
                "<th width=\"10%\">创建时间</th>\n" +
                "<th width=\"30%\">操作</th>\n" +
                "</tr>");
			//循环遍历集合
			for(var i=0;i<billList.length;i++){
				var bill=billList[i];
				var ispay=bill.isPayment==1?"未付款":"已付款";
                $(".providerTable").append("<tr><td><span>"+bill.billCode+"</span></td><td><span>"+bill.productName+"</span></td> <td><span>"+bill.provider.proName+"</span></td><td><span>"+bill.totalPrice+"</span></td>" +
					"<td><span>"+ispay+"</span></td><td><span>"+bill.creationDate+"</span></td>" +
					"<td><span><a class=\"viewBill\" href=\"javascript:;\" billid='"+bill.id+"' billcc='"+bill.billCode+"'><img src='/static/images/read.png' alt=\"查看\" title=\"查看\"/></a></span>" +
					"<span><a class=\"modifyBill\" href=\"javascript:;\" billid='"+bill.id+"' billcc='"+bill.billCode+"'><img src='/static/images/xiugai.png' alt=\"修改\" title=\"修改\"/></a></span>" +
					"<span><a class=\"deleteBill\" href=\"javascript:;\" billid='"+bill.id+"' billcc='"+bill.billCode+"'><img src='/static/images/schu.png' alt=\"删除\" title=\"删除\"/></a></span></td></tr>");

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

    //订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
    function deleteBill(obj){
        $.getJSON("../../sys/delBill","billid="+obj.attr("billid"),function(data){
            if(data == "1"){//删除成功：移除删除行
                cancleBtn();
                page(1);  //从新刷新页面
            }else if(data == "-1"){//删除失败
                changeDLGContent("对不起，删除订单【"+obj.attr("billcc")+"】失败");
            }else if(data == "0"){
                changeDLGContent("对不起，订单【"+obj.attr("billcc")+"】不存在");
            }
        });
    }

    /**
	 * 初始化方法
     */
    page(1);   //初始化方法


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

