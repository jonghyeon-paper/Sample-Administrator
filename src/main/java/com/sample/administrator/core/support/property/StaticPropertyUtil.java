package com.sample.administrator.core.support.property;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class StaticPropertyUtil extends PropertyPlaceholderConfigurer {

	private static Map<String, String> propertiesMap;

	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);

		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			propertiesMap.put(keyStr, props.getProperty(keyStr));
		}

		String log4j = propertiesMap.get(SystemConstant.LOG4J_LOCATION);
		if (log4j != null)
			PropertyConfigurator.configure(log4j);

	}

	public static String getProperty(String name) {
		
		return propertiesMap.get(name);
	}

}
