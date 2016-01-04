<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试 ZK...</title>
<script>
	function location_href() {
		window.location.href = GetBasePath() + "/list.jsp";
	}
</script>
</head>
<body>
	<a href="javascript:void(0);" onclick="location_href();">获取zk数据</a>
</body>
</html>