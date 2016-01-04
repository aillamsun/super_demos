package com.sung.mongo.agg;

public class HostingCount {
	
	private String id;
	
	private String hosting;

	private long total;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHosting() {
		return hosting;
	}

	public void setHosting(String hosting) {
		this.hosting = hosting;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "HostingCount [id=" + id + ", hosting=" + hosting + ", total="
				+ total + "]";
	}
}

