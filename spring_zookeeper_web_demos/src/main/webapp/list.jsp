<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ZK Node List</title>
<script type="text/javascript">

$(document).ready(function() {
	//加载树结构数据
	$.fn.zTree.init($("#treeDemo"), setting);
});

	var setting = {

		async : {
			enable : true,
			url : GetBasePath() + "/LoadZKNodeList",
			dataFilter : null
		},

		view : {
			addHoverDom : addHoverDom,//当用户鼠标移动到节点上时,显示用户自定义控件,显示隐藏状态同zTree内部的编辑，删除按钮
			removeHoverDom : removeHoverDom
		//移出节点时,显示隐藏状态同zTree内部的编辑、删除按钮
		},
		data : {
			simpleData : {
				enable : true
			}
		}
	};

	//鼠标在节点获取焦点显示添加按钮
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) {
			return;
		}
		if (treeNode.editNameFlag || $("#delBtn_" + treeNode.tId).length > 0) {
			return;
		}
		if (treeNode.editNameFlag || $("#updBtn_" + treeNode.tId).length > 0) {
			return;
		}
		//删除
		if (treeNode.showDeleteBut) {
			var parentPath = getParentNode(treeNode, 0);
			var delStr = "<span class='' id='delBtn_" + treeNode.tId
					+ "' title='删除' style='color:red;' onclick='deleteNode(\""
					+ parentPath + "\")'>删除</span>";
			sObj.after(delStr);
		}
		//修改
		if (treeNode.showUpdateBut) {
			var parentPath = getParentNode(treeNode, 0);
			var updStr = "<span class='' id='updBtn_" + treeNode.tId
					+ "' title='修改' style='color:red;' onclick='updateNode(\""
					+ parentPath + "\")'>修改</span>";
			sObj.after(updStr);
		}
		//修改
		if (treeNode.showInsertBut) {
			var parentPath = getParentNode(treeNode, 0);
			var updStr = "<span class='' id='addBtn_" + treeNode.tId
					+ "' title='添加' style='color:red;' onclick='addNode(\""
					+ parentPath + "\")'>添加</span>";
			sObj.after(updStr);
		}
	}
//隐藏按钮
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
		$("#updBtn_" + treeNode.tId).unbind().remove();
		$("#delBtn_" + treeNode.tId).unbind().remove();
	}
//添加
	function addNode(currentPath) {
		$("#insert_current_path").val("");
		$("#insert_current_path_data").val("");
		$("#insert_old_current_path").val(currentPath);
		//隐藏修改
		document.getElementById("update_div").style.display = "none";
		document.getElementById("insert_div").style.display = "block";
	}
	//修改
	function updateNode(currentPath) {
		$("#update_path_data").val("");
		$.post(
				GetBasePath() + "/LoadZkNodeDataForPath",
				{"currentPath":currentPath},
				function(data){
					//alert(data);
					var arr = data.split("-");
					$("#update_current_path").val(arr[0]);
					$("#update_old_path_data").val(arr[1]);
					//隐藏添加
					document.getElementById("insert_div").style.display = "none";
					document.getElementById("update_div").style.display = "block";
				}
		);
	}
	//删除
	function deleteNode(currentPath) {
		var falg = confirm("你确定要删除 ：" + currentPath + " 吗?");
		if(true == falg){
			var param = {"oper_type":"delete","delete_current_path":currentPath};
			$.post(GetBasePath() + "/ZkCUDOper",param,function(data){
				if(data == 'success'){
					alert("删除成功.");
					var f = confirm("是否自动刷新页面.");
					if(f){
						location.reload() ;
					}else{

					}
				}else{
					alert("删除失败.");
				}
			});			
		}else{

		}
	}

	var parentPath = "";
	var currentNodeName = "";
	function getParentNode(treeNode, i) {
		if (i == 0) {
			parentPath = "";
			currentNodeName = "";
			currentNodeName = treeNode.name;
		}
		var pNode = treeNode.getParentNode();
		var parentPathTemp = "";
		if (null != pNode) {
			i++;
			parentPathTemp = pNode.name;
			parentPath = parentPathTemp + "/" + parentPath;
			getParentNode(pNode);
		}
		//根节点
		if (parentPath == "") {
			return currentNodeName;
		}
		return parentPath + currentNodeName;
	}
	
	//添加
	function insert(){
		var url = GetBasePath() + "/ZkCUDOper";
		var insert_old_current_path = $("#insert_old_current_path").val();
		var insert_current_path = $("#insert_current_path").val();
		var insert_current_path_data = $("#insert_current_path_data").val();
		if('' == insert_current_path){
			alert("添加节点名称不能为 空 .");
			return ;
		}
		//if('' == insert_current_path_data){
		//	alert("添加新节点必须填写对应节点的值 .");
		//	return;
		//}
		
		var params = {
				"insert_old_current_path":insert_old_current_path,
				"insert_current_path":insert_current_path,
				"insert_current_path_data":insert_current_path_data,
				"oper_type":"insert"
		};
		
		$.ajax({
			url:url,
			type: "POST",
			data:params,
			success:function(data){
				if(data == 'success'){
					alert("添加成功.");
					var f = confirm("是否自动刷新页面.");
					if(f){
						location.reload() ;
					}else{

					}
				}else{
					alert("添加失败.");
				}
			}
		});
	}
	//修改
	function update(){
		var url = GetBasePath() + "/ZkCUDOper";
		var update_current_path = $("#update_current_path").val();
		var update_old_path_data = $("#update_old_path_data").val();
		var update_path_data = $("#update_path_data").val();
		
		if('' == update_path_data){
			alert("修改节点值不能为空 .");
			return;
		}
		
		var params = {
				"update_current_path":update_current_path,
				"update_old_path_data":update_old_path_data,
				"update_path_data":update_path_data,
				"oper_type":"update"
		};
		$.ajax({
			url:url,
			type: "POST",
			data:params,
			success:function(data){
				if(data == 'success'){
					alert("修改成功.");
					var f = confirm("是否自动刷新页面.");
					if(f){
						location.reload() ;
					}else{

					}
				}else{
					alert("修改失败.");
				}
			}
		});
	}
	
</script>
</head>
<body>
	<div
		style="background-color: gray; padding-left: 100px; padding-top: 10px; color: red; text-align:center; font-size:3em; text-indent: 10px;">
		Zookeeper Nodes List Data CURD...</div>
	<div class="zTreeDemoBackground left"
		style="background-color: ; float: left; margin-left: 100px; margin-top: 20px;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>

	<div style="float: left; background-color:; margin-left: 200px; margin-top: 100px;">
		
		<!-- 添加   -->
		<div style="display: none;" id="insert_div">
			<div><span style="font-family: cursive;color: red;">添加 ZK Node </span></div>
			<br>
			<div>
				<table>
					<tr>
						<td>当 前 PATH :</td>
						<td><input type="text" name="insert_old_current_path" id="insert_old_current_path" style="width:400px;height:25px;"></td>
					</tr>
					<tr>
						<td>添加节点名称 ：</td>
						<td><input type="text" name="insert_current_path" id="insert_current_path" style="width:300px;height:25px;"></td>
					</tr>
					<tr>
						<td>此节点数据值 ：</td>
						<td><textarea rows="3" cols="45" name="insert_current_path_data" id="insert_current_path_data"></textarea></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" value="提交" onclick="insert();"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		
		<!-- 修改   -->
		<div style="display: none;" id="update_div">
			<div><span style="font-family: cursive;color: red;">修改 ZK Node </span></div>
			<br>
			<div>
				<table>
					<tr>
						<td>当   前  PATH :</td>
						<td><input type="text" name="update_current_path" id="update_current_path" style="width:400px;height:25px;"></td>
					</tr>
					<tr>
						<td>当前节点数据值 ：</td>
						<td><textarea rows="3" cols="45" name="update_old_path_data" id="update_old_path_data"></textarea></td>
					</tr>
					<tr>
						<td>修  改  数  据  值 ：</td>
						<td><textarea rows="3" cols="45" name="update_path_data" id="update_path_data"></textarea></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" value="更新" onclick="update();"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>