package com.sung.zookeeper.config;


import com.sung.base.common.utils.PropertyUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class LoadConfiguration {

	public LoadConfiguration() {
		try {
			this.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() throws IOException {
		Properties props = PropertyUtils.getProperties("conf.properties");
		Set<Object> keyValue = props.keySet();
		if (null != keyValue) {
			for (Iterator<Object> it = keyValue.iterator(); it.hasNext();) {
				String key = (String) it.next();
				switch (key) {
				case "zk.root.name":
					ServerConfig.zk_root_name = (String) props.get(key);
					break;
				case "zk.host":
					ServerConfig.zk_host = (String) props.get(key);
					break;
				case "zk.port":
					ServerConfig.zk_port = Integer.parseInt((String) props
							.get(key));
					break;
				case "zk.auth.type":
					ServerConfig.zk_auth_type = (String) props.get(key);
					break;
				case "zk.auth.passwd":
					ServerConfig.zk_auth_passwd = (String) props.get(key);
					break;
				case "zk.session.Timeout":
					ServerConfig.zk_session_timeout = Integer.parseInt((String) props.get(key));
					break;
				}
			}
		}
	}
}
