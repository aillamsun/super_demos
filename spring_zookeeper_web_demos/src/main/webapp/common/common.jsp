<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<c:set var="scheme" value="${pageContext.request.scheme}"></c:set>
<c:set var="serverName" value="${pageContext.request.serverName}"></c:set>
<c:set var="serverPort" value="${pageContext.request.serverPort}"></c:set>
<c:set var="basePath" value="${scheme}://${serverName}:${serverPort }${path}/"></c:set>


<script type="text/javascript">
 function GetBasePath(){
	var path = '${path}';
	return(path);
}
 </script>
 
<script src="${path}/js/jquery-1.9.0.min.js" type="text/javascript"></script>

<script type="text/javascript" 	src="${path }/js/ztree-3.5.15/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" href="${path }/js/ztree-3.5.15/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<!-- Jquery  Layer 模态弹出框插件 -->
<script type="text/javascript" src="${path }/js/layer/layer.min.js"></script>