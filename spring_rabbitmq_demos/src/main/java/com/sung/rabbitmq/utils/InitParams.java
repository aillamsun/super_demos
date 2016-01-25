package com.sung.rabbitmq.utils;

public class InitParams {

	private String host = "localhost"; // host地址
	private String username = "guest";// 链接用户名
	private String password = "guest";// 链接密码
	private String virtualHost = "/";// 链接的 虚拟机名
	private int port = -1;// 端口号

	private int requestedChannelMax = 0;
	private int requestedFrameMax = 0;
	private int requestedHeartbeat = 0;
	private int connectionTimeout = 0;
	private int networkRecoveryInterval = 5000;

	private boolean automaticRecovery = false;
	private boolean topologyRecovery = true;

	public InitParams(BuilderParams builder) {
		this.host = builder.host;
		this.username = builder.username;
		this.password = builder.password;
		this.virtualHost = builder.virtualHost;
		this.port = builder.port;
		this.requestedChannelMax = builder.requestedChannelMax;
		this.requestedFrameMax = builder.requestedFrameMax;
		this.requestedHeartbeat = builder.requestedHeartbeat;
		this.connectionTimeout = builder.connectionTimeout;
		this.networkRecoveryInterval = builder.networkRecoveryInterval;
		this.automaticRecovery = builder.automaticRecovery;
		this.topologyRecovery = builder.topologyRecovery;
	}

	static class BuilderParams {

		private String host = "localhost"; // host地址
		private String username = "guest";// 链接用户名
		private String password = "guest";// 链接密码
		private String virtualHost = "/";// 链接的 虚拟机名
		private int port = -1;// 端口号

		private int requestedChannelMax = 0;
		private int requestedFrameMax = 0;
		private int requestedHeartbeat = 0;
		private int connectionTimeout = 0;
		private int networkRecoveryInterval = 5000;

		private boolean automaticRecovery = false;
		private boolean topologyRecovery = true;

		public BuilderParams(String host) {
			this.host = host;
		}

		public BuilderParams(String host, String username, String password) {
			this.host = host;
			this.username = username;
			this.password = password;
		}

		public BuilderParams(String host, String username, String password,
				String virtualHost, int port) {
			this.host = host;
			this.username = username;
			this.password = password;
			this.virtualHost = virtualHost;
			this.port = port;
		}

		public BuilderParams setRequestedChannelMax(int requestedChannelMax) {
			this.requestedChannelMax = requestedChannelMax;
			return this;
		}

		public BuilderParams setRequestedFrameMax(int requestedFrameMax) {
			this.requestedChannelMax = requestedFrameMax;
			return this;
		}

		public BuilderParams setRequestedHeartbeat(int requestedHeartbeat) {
			this.requestedHeartbeat = requestedHeartbeat;
			return this;
		}

		public BuilderParams setConnectionTimeout(int connectionTimeout) {
			this.connectionTimeout = connectionTimeout;
			return this;
		}

		public BuilderParams setNetworkRecoveryInterval(
				int networkRecoveryInterval) {
			this.networkRecoveryInterval = networkRecoveryInterval;
			return this;
		}

		public BuilderParams setAutomaticRecovery(boolean automaticRecovery) {
			this.automaticRecovery = automaticRecovery;
			return this;
		}

		public BuilderParams setTopologyRecovery(boolean topologyRecovery) {
			this.topologyRecovery = topologyRecovery;
			return this;
		}

		public InitParams build() {
			return new InitParams(this);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getRequestedChannelMax() {
		return requestedChannelMax;
	}

	public void setRequestedChannelMax(int requestedChannelMax) {
		this.requestedChannelMax = requestedChannelMax;
	}

	public int getRequestedFrameMax() {
		return requestedFrameMax;
	}

	public void setRequestedFrameMax(int requestedFrameMax) {
		this.requestedFrameMax = requestedFrameMax;
	}

	public int getRequestedHeartbeat() {
		return requestedHeartbeat;
	}

	public void setRequestedHeartbeat(int requestedHeartbeat) {
		this.requestedHeartbeat = requestedHeartbeat;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getNetworkRecoveryInterval() {
		return networkRecoveryInterval;
	}

	public void setNetworkRecoveryInterval(int networkRecoveryInterval) {
		this.networkRecoveryInterval = networkRecoveryInterval;
	}

	public boolean isAutomaticRecovery() {
		return automaticRecovery;
	}

	public void setAutomaticRecovery(boolean automaticRecovery) {
		this.automaticRecovery = automaticRecovery;
	}

	public boolean isTopologyRecovery() {
		return topologyRecovery;
	}

	public void setTopologyRecovery(boolean topologyRecovery) {
		this.topologyRecovery = topologyRecovery;
	}

	@Override
	public String toString() {
		return "InitConnectionFactoryParams [host=" + host + ", username="
				+ username + ", password=" + password + ", virtualHost="
				+ virtualHost + ", port=" + port + "]";
	}

	public static void main(String[] args) {
		InitParams params = new BuilderParams("aaa").build();
		System.out.println(params.toString());;
		
	}
}
