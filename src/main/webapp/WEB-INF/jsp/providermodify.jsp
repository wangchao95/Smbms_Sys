<%@ page import="cn.jbit.smbms.entity.Provider" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

  <div class="right">
      <div class="location">
          <strong>你现在所在的位置是:</strong>
          <span>供应商管理页面 >> 供应商修改页</span>
      </div>
      <div class="providerAdd">
          <form id="providerForm" enctype="multipart/form-data" name="providerForm" method="post" action="">
              <!--div的class 为error是验证错误，ok是验证成功-->
              <input type="hidden" name="id" id="id" value="${proList.id }"/>
              <input type="hidden" name="modifyBy" id="userId" value="${user.id }"/>
              <div class="">
                  <label for="proCode">供应商编码：</label>
                  <input type="text" name="proCode" id="proCode" value="${proList.proCode }" readonly="readonly">
              </div>
              <div>
                  <label for="proName">供应商名称：</label>
                 <input type="text" name="proName" id="proName" value="${proList.proName }">
			<font color="red"></font>
              </div>
              
              <div>
                  <label for="proContact">联系人：</label>
                  <input type="text" name="proContact" id="proContact" value="${proList.proContact }">
			<font color="red"></font>
              </div>
              
              <div>
                  <label for="proPhone">联系电话：</label>
                  <input type="text" name="proPhone" id="proPhone" value="${proList.proPhone }">
			<font color="red"></font>
              </div>
              
              <div>
                  <label for="proAddress">联系地址：</label>
                  <input type="text" name="proAddress" id="proAddress" value="${proList.proAddress }">
              </div>
              
              <div>
                  <label for="proFax">传真：</label>
                  <input type="text" name="proFax" id="proFax" value="${proList.proFax }">
              </div>
              
              <div>
                  <label for="proDesc">描述：</label>
                  <input type="text" name="proDesc" id="proDesc" value="${proList.proDesc }">
              </div>
              <div style="margin-left: 200px">证件照:<img src="/static/uploadFiles/${proList.comptyLicPicPath}" width="200" height="200"/></div>
              <input type="hidden" name="oldPhoto" id="oldPhoto" value="${proList.comptyLicPicPath}">
              <div>
                  <label for="comptyLicPicPath">修改证件照：</label>
                  <input type="file" name="cLicPicPath" id="comptyLicPicPath" value="">
                  <font color="red">${uploadError}</font>
              </div>
              <div class="providerAddBtn">
                  <input type="button" name="save" id="save" value="保存">
				  <input type="button" id="back" name="back" value="返回" >
              </div>
          </form>
      </div>
  </div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/providermodify.js"></script>