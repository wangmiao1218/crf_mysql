<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keyword" content="">
    <title>处理后文件列表</title>
    <!-- 日期控件CSS -->
	<link rel="stylesheet" type="text/css" href="<%=rootPath %>/res/cui/app/datepicker/My97DatePicker/skinex/skinex.css" />
	<!-- 引入jQueryValidation -->
	<script type="text/javascript" src="<%=rootPath %>/res/jquery/plugins/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=rootPath %>/res/jquery/plugins/jquery.validate.message_cn.js"></script>
	<script type="text/javascript">
			
	</script>

</head>
<body>
<h>test</h>
    <div class="asideR-cont">
        <div class="add-cnt">
	        <!-- 遍历Map集合 -->  
		   <c:forEach var="fileNameMap" items="${fileNameMap}">  
		       <c:url value="/file/downFile" var="downurl">  
		           <c:param name="filename" value="${fileNameMap.key}"></c:param>  
		       </c:url>  
		      ${fileNameMap.value}<a href="${downurl}">下载</a>
		       <br/>  
		   </c:forEach>   
	        <!-- <div class="form-aciton" style="margin-left:40px">
	        	<button class="upload-btn" id="upload-btn" onclick="upload()" >上传文件</button>
	            <button class="submit-btn" onclick="saveUser()">开始处理</button>
	        </div> -->
        </div>
    </div>
	<!--上传  -->
	<script type="text/javascript" src="<%=rootPath %>/res/jquery/plugins/jquery.ajaxfileupload.js"></script>
    <script src="<%=rootPath %>/page/tools/js/linkagePath.js"></script>
    
</body>

</html>