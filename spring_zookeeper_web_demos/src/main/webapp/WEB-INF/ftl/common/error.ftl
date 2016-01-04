<!DOCTYPE html>
<html>
	<head>
		<title>Zookeeper-Web</title>
		<script src="${host}/js/jquery.min.js" type="text/javascript"></script>
		<link href="${host}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="${host}/css/zk-web.css" rel="stylesheet" type="text/css">
	</head>
	<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>哇哦!</h1>
                <h2>竟然报错了</h2>
                <div class="error-details">
	                <p><#if (exception.retMsg)??>${exception.retMsg}<#else>${(exception.message)!''}</#if></p>
					<p style="display:none">${stackTrace!}</p>
                </div>
                <div class="error-actions">
                    <a href="${host}" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span> 回主页 </a>
                </div>
            </div>
        </div>
    </div>
</div>

	</body>
</html>