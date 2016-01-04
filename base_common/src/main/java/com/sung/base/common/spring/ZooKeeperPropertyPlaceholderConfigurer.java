package com.sung.base.common.spring;

import com.sung.base.common.utils.LogUtils;
import com.sung.base.common.zk.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.List;
import java.util.Properties;

public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String zkPath;
    private String connectString;
    private String maxRetries;
    private String baseSleepTimes;
    private String nameSpace;
    private String schemen;
    private String passwd;
    private List<IZKListener> listenerList;
    @Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) throws BeansException {
    	//读取配置文件中的ZK基本配置
		if ("dev".equals(props.get("project.cycle"))){
			ZKConfigBean.connectString = props.getProperty("zk.dev.host");
			ZKConfigBean.maxRetries = props.getProperty("zk.dev.max.retries");
			ZKConfigBean.baseSleepTimes = props.getProperty("zk.dev.base.sleep.timems");
			ZKConfigBean.nameSpace = props.getProperty("zk.dev.name.space");
			ZKConfigBean.schemen = props.getProperty("zk.dev.auth.type");
			ZKConfigBean.passwd = props.getProperty("zk.dev.auth.passwd");
		}else {
			ZKConfigBean.connectString = props.getProperty("zk.host");
			ZKConfigBean.maxRetries = props.getProperty("zk.max.retries");
			ZKConfigBean.baseSleepTimes = props.getProperty("zk.base.sleep.timems");
			ZKConfigBean.nameSpace = props.getProperty("zk.name.space");
			ZKConfigBean.schemen = props.getProperty("zk.auth.type");
			ZKConfigBean.passwd = props.getProperty("zk.auth.passwd");
		}
		ZooKeeperFactory.CONNECT_STRING=ZKConfigBean.connectString;
		ZooKeeperFactory.MAX_RETRIES = Integer.parseInt(ZKConfigBean.maxRetries);
		ZooKeeperFactory.BASE_SLEEP_TIMEMS = Integer.parseInt(ZKConfigBean.baseSleepTimes);
		ZooKeeperFactory.NAME_SPACE = ZKConfigBean.nameSpace;
		ZooKeeperFactory.SCHEMEN = ZKConfigBean.schemen;
		ZooKeeperFactory.AUTH = ZKConfigBean.passwd;

		//将ZK中的配置加载到Spring的Properties
		fillCustomeProperties(props);
		//配置节点的侦听
		//setZKListener();
		super.processProperties(beanFactoryToProcess, props);
	}
	
	private void fillCustomeProperties(Properties props) {
		String[] paths = props.getProperty("evs.config.paths").split(";");
		getData(props,paths);
		//LogUtils.logInfo(path+"节点下的配置数据为："+data);
		//fillProperties(props,data);
	}
	
	private  void getData(Properties props,String[] paths)    {
		for (String path:paths){
			try{
				Config config = new ZooKeeperConfig();
				String nodeDatas = config.getConfig(path);
				fillProperties(props,nodeDatas);
			}catch(Exception e){
				LogUtils.logError("", e);
			}
		}
	}
	
	private void fillProperties(Properties props,String data) throws Exception{
		if(StringUtils.isNotBlank(data)){
			String[] values = data.split(";");
			for(String str: values){
				String[] cfgItem = StringUtils.split(str, "=");
				if(2 == cfgItem.length){
					props.put(cfgItem[0], cfgItem[1]);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void setZKListener()throws Exception{
		Config config = new ZooKeeperConfig();
		for(IZKListener listener : listenerList){
			config.setConfigListener(listener);
		}
	}
	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public String getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(String maxRetries) {
		this.maxRetries = maxRetries;
	}

	public String getBaseSleepTimes() {
		return baseSleepTimes;
	}

	public void setBaseSleepTimes(String baseSleepTimes) {
		this.baseSleepTimes = baseSleepTimes;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getSchemen() {
		return schemen;
	}

	public void setSchemen(String schemen) {
		this.schemen = schemen;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getZkPath() {
		return zkPath;
	}

	public void setZkPath(String zkPath) {
		this.zkPath = zkPath;
	}

	public List<IZKListener> getListenerList() {
		return listenerList;
	}

	public void setListenerList(List<IZKListener> listenerList) {
		this.listenerList = listenerList;
	}
}
