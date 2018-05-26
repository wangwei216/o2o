package com.imooc.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


	/*
	* 这个类是继承xml文件中property-placeholder这个类的
	*
	* */
public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
		//需要加密的配置数据
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

	/*对关键的属性进行转换*/
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			//对已经加密的字段进行解密，如果么有加密就直接返回最开始的数据
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	/*这个是判断属性是否被加密*/
	private boolean isEncryptProp(String propertyName) {
		//需要对传进来的数据所在的数组进行遍历
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}
