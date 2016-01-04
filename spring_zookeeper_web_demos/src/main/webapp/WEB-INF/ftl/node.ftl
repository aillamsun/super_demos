<!DOCTYPE html>
<html>
	<head>
		<title>Zookeeper-Web</title>
		<script src="${host}/js/jquery.min.js" type="text/javascript"></script>
		<script src="${host}/js/bootstrap.min.js" type="text/javascript"></script>
		<link href="${host}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="${host}/css/zk-web.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="container-full1">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
						<div>
						<#if isLogin()>
						<div class="navbar-header" style="margin-left: 35%;">
							 <span class="navbar-brand">管理工具</span>
						</div>
						</#if>
						<div class="collapse navbar-collapse  style="margin-left: 35%;"" id="bs-example-navbar-collapse-1">
							<#if isLogin()>
							<ul class="nav navbar-nav">
								<li>
									<a data-toggle="modal" data-target="#createModal" href="#createModal">创建</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#editModal" href="#editModal">编辑</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#deleteModal" href="#deleteModal">删除</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#rmrModal" href="#rmrModal">级联删除</a>
								</li>
							</ul>
							</#if>
							<ul class="nav nav-pills navbar-right" style="margin-right: 8%;">
								<li>
									 <a><#if isLogin()>${zk_user!""}<#else>游客</#if></a>
								</li>
								<li class="active">
									 <#if isLogin()>
									 <a href="${host}/logout">注销</a>
									 <#else>
									 <a data-toggle="modal" data-target="#loginModal" href="#loginModal">登录</a>
									 </#if>
								</li>
							</ul>
						</div>
						</div>
					</nav>
					<div class="page-header text-center">
						<a href="${host}" style="text-decoration : none"><h1>
							Zookeeper Web <#--<small>简单一点 方便一点</small>-->
						</h1></a>
					</div>
					<div class="text-center">
						<ul class="breadcrumb span12">
							<i class="icon-chevron-right"></i>
							<li><a href="${host}/read/addr?cxnstr=${cxnstr!''}">${cxnstr!''}</a></li>
							<#assign pathAppend = "/">
							<#if pathList??>
							<#list pathList as p>
							<#assign pathAppend=pathAppend+p+"/">
							<i class="icon-chevron-right"></i>
							<li><a href="${host}/read/node?path=${pathAppend?substring(0,pathAppend?length-1)}">${p!''}</a></li>
							</#list>
							</#if>
							<#--<#if pathAppend==""><#assign pathAppend="/"></#if>-->
						<#--<span>This is a template for a simple marketing or .</span>-->
						</ul>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-4 column">
					<h3>子节点<span class="label label-info fontsize11">${(children?size)!'0'}</span></h3>
					<table class="table table-bordered">
						<#if children?size gt 0>
						<#list children as c>
						<tr><td><a href="${host}/read/node?path=${pathAppend?substring(0,pathAppend?length-1)}/${c}">${c}</a></td></tr>
						</#list>
						<#else>
						<div class="alert"><h3 class="text-danger text-center">木有子节点</h3></div>
						</#if>
					</table>
				</div>
				<div class="col-md-4 column">
					<h3>节点状态</h3>
					<table class="table table-bordered">
						<#if stat??>
						<#list stat?keys as key>
						<tr>
							<td>${key}</td>
							<td>${stat[key]}</td>
						</tr>
						</#list>
						</#if>
					</table>
				</div>
				<div class="col-md-4 column">
					<h3>节点数据<span class="label label-info fontsize11">${dataSize} byte(s)</span></h3>
					<div class="well marginright50">
						<p style="word-break:break-all;">${data?replace("\n", "<br/>")}</p>
					</div>
				</div>
			</div>
			
		</div>

<div class="modal fade" id="createModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">创建一个子节点</h4>
      </div>
      <form action="${host}/op/create" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-info">从这个节点创建子节点: <strong>${pathAppend}</strong></div>
    	<div class="form-group">
	      <label for="inputName" class="col-lg-2 control-label">节点名称:</label>
	      <div class="col-lg-10">
	        <input class="form-control" required name="name" id="inputName" placeholder="Name of new node" type="text" />
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="textAreaData" class="col-lg-2 control-label">数据:</label>
	      <div class="col-lg-10">
	        <textarea class="form-control" rows="10" name="data" id="textAreaData" placeholder="Data of new node" rows="6"></textarea>
	      </div>
	    </div>
		<input class="span8" name="parent" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary">创建</button>
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="editModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">编辑节点数据</h4>
      </div>
      <form action="${host}/op/edit" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-info">编辑节点: <strong>${pathAppend}</strong></div>
	    <div class="form-group">
	      <label for="textAreaData" class="col-lg-2 control-label">数据:</label>
	      <div class="col-lg-10">
	        <textarea class="form-control" required rows="10" name="data" id="textAreaData" placeholder="Data of new node" rows="6">${data!''}</textarea>
	      </div>
	    </div>
		<input class="span8" name="path" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary">保存</button>
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="deleteModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">删除这个节点</h4>
      </div>
      <form action="${host}/op/delete" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-info">确认删除节点: <strong>${pathAppend}</strong></div>
		<input class="span8" name="path" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" id="del-btn" class="btn btn-primary">删除</button>
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="rmrModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">删除这个节点及子节点</h4>
      </div>
      <form action="${host}/op/rmrdel" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-danger">
    		<h4>Danger!!</h4>
    		确定级联删除: <strong>${pathAppend}</strong>和所有的子节点???
    	</div>
		<input class="span8" name="path" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" id="del-btn" class="btn btn-primary">级联删除</button>
      </div>
      </form>
    </div>
  </div>
</div>

<!--login modal-->
<div id="loginModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
  <div class="modal-content">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
          <h1 class="text-center">登录</h1>
      </div>
      <div class="modal-body">
          <form class="form col-md-12 center-block" action="${host}/login" method="POST">
            <div class="form-group">
              <input required name="username" type="text" class="form-control input-lg" placeholder="用户名">
            </div>
            <div class="form-group">
              <input required name="password" type="password" class="form-control input-lg" placeholder="密码">
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-lg btn-block">登录</button>
              <!--<span class="pull-left">帐号的配置在/zookeeper-web/src/main/resources/conf/user.properties</span>--><#--<span><a href="#">Need help?</a></span>-->
            </div>
          </form>
      </div>
      <div class="modal-footer-login modal-footer">
          <div class="col-md-12">
          <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		  </div>
      </div>
  </div>
  </div>
</div>


	</body>
</html>