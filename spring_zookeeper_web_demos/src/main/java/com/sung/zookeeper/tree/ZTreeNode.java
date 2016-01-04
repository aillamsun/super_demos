package com.sung.zookeeper.tree;

public class ZTreeNode {

	private String name;
	private String id;
	private String pId;
	private String icon;

	// 控制是否显示 增删改按钮 默认都是不显示
	private boolean isShowDeleteBut = false;
	private boolean isShowUpdateBut = false;
	private boolean isShowInsertBut = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return pId;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isShowDeleteBut() {
		return isShowDeleteBut;
	}

	public void setShowDeleteBut(boolean isShowDeleteBut) {
		this.isShowDeleteBut = isShowDeleteBut;
	}
	public boolean isShowUpdateBut() {
		return isShowUpdateBut;
	}
	public void setShowUpdateBut(boolean isShowUpdateBut) {
		this.isShowUpdateBut = isShowUpdateBut;
	}
	public boolean isShowInsertBut() {
		return isShowInsertBut;
	}

	public void setShowInsertBut(boolean isShowInsertBut) {
		this.isShowInsertBut = isShowInsertBut;
	}

	@Override
	public String toString() {
		return "ZTreeNode [name=" + name + ", id=" + id + ", pId=" + pId
				+ ", isShowInsertBut=" + isShowInsertBut + "]";
	}
}
