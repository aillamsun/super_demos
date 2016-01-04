package com.sung.zookeeper.tree;

import com.sung.zookeeper.zk.ZkClient;

import java.util.ArrayList;
import java.util.List;

public class ZkTreeBuildUtils {

	
	public static void getZkTreeData(List<ZTreeNode> zTreeNodes,
			String nodeName, ZkClient zkClient, String pid) throws InterruptedException {
		
		List<String> zkNodes = new ArrayList<String>();
		ZTreeNode treeNode = null;
		
		
		
		//根节点
		if (pid.equals("0")) {
			treeNode =  new ZTreeNode();
			treeNode.setId("1");
			treeNode.setPId(pid);
			treeNode.setName(nodeName);
			treeNode.setShowInsertBut(true);
			
			zTreeNodes.add(treeNode);
			
			pid = "1";
		}
		try {
			zkNodes = zkClient.getChildren(nodeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (zkNodes.size() > 0) {
			
			for (int i = 0; i < zkNodes.size(); i++) {
				String node = zkNodes.get(i);
				treeNode = new ZTreeNode();
				
				treeNode.setId(pid+"-"+(i+1));
				treeNode.setPId(pid);
				treeNode.setName(node);
				treeNode.setShowDeleteBut(true);
				treeNode.setShowInsertBut(true);
				treeNode.setShowUpdateBut(true);
				
				zTreeNodes.add(treeNode);
				
				getZkTreeData(zTreeNodes, nodeName+"/"+node, zkClient, pid+"-"+(i+1));
			}
		}
	}
}
