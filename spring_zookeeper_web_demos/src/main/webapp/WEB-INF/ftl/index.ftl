<!DOCTYPE html>
<html>
	<head>
		<title>Zookeeper-Web</title>
		<script src="${host}/js/jquery.min.js" type="text/javascript"></script>
		<link href="${host}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="${host}/css/zk-web.css" rel="stylesheet" type="text/css">
	</head>
	<body>

<div class="container-full">
    <div class="row">
        <div class="col-lg-12 text-center v-center">
            <h1 class="" contenteditable="false">Zookeeper Web</h1>
            <p class="lead">简单一点 方便一点</p>
            <br class="">
            <form class="col-lg-12"  action="${host}/read/addr" method="get">
                <div class="input-group input-group-lg col-sm-offset-4 col-sm-4">
                   	<!--<input type="text" name="cxnstr" value="" required class="center-block form-control input-lg" title="输入zookeeper地址." placeholder="Connect String: host[:port][/namespace]">-->
                  	<span class="input-group-btn"><button class="btn btn-lg btn-primary" type="submit">GO</button></span>
                </div>
            </form>
        </div>
    </div>
    <!-- /row -->
    <div class="row">
        <div class="col-lg-12 text-center v-center" style="font-size:39pt;"> <a href="#" class=""><i class="icon-google-plus"></i></a>  <a href="#" class=""><i class="icon-facebook"></i></a> 
            <a href="#" class=""><i class="icon-twitter"></i>

                </a> <a href="#" class=""><i class="icon-github"></i></a>  <a href="#" class=""><i class="icon-pinterest"></i></a>

        </div>
    </div>
    <br class="">
    <br class="">
    <br class="">
    <br class="">
    <br class="">
</div>
<!-- /container full 
<div class="container">
    <hr class="">
    <div class="row">
    	<#if addrs??>
		<#list addrs?keys as key>
		<div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                     <a href="${host}/read/addr?cxnstr=${addrs[key]!''}"><h3 contenteditable="false">${key!''}</h3></a>
                </div>
                <div class="panel-body wrap" contenteditable="false">${addrs[key]!''}</div>
            </div>
        </div>
		</#list>
		</#if>
    
        
    </div>
    <div class="row">
        <div class="col-lg-12">
            <br class="">
            <br class="">
            <p class="pull-right"><a href="http://www.xianzai.me" class="">花花</a>
            <br class="">
            <br class="">
        </p></div>
    </div>
</div>-->

	</body>
</html>