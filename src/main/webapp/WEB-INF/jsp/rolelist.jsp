<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>角色管理页面</span>
            </div>
            <div class="search">
           		<form method="post" action="${pageContext.request.contextPath }/RoleServlet">
					<input name="action" value="roleList" class="input-text" type="hidden">
					 <span>角色名：</span>
					 <input name="roleName" id="roleName" class="input-text"	type="text" value="">

					 <span>角色编码：</span>
					<input name="roleCode" id="roleCode" class="input-text"	type="text" value="">
					 
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="button" id="searchbutton">
					 <a href="${pageContext.request.contextPath}/sys/toAddRole" >添加角色</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">

			</table>
			<div class="page"></div>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该角色吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/rolelist.js"></script>
