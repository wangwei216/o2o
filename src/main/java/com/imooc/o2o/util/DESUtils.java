package com.imooc.o2o.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DESUtils {

	/*
	* 如何在SSM中对配置的数据库信息进行加密处理，这里用到的就是DES算法进行加密(DES算法也就是对称加密算法)
	*
	*
	*
	* */


	private static Key key;
//	这个是设置密匙
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			//这个是生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置密匙种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//初始化基于SHA1的算法对象
			generator.init(secureRandom);
			//生成密匙对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*这个是的得到加密之后的信息*/
	public static String getEncryptString(String str) {

		//这个是基于BASE64编码，接收byte[]并将其转化为String
		BASE64Encoder base64encoder = new BASE64Encoder();
		try {
			//按UTF-8进行编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			///这个是获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//使用doFInal方法进行加密
			byte[] doFinal = cipher.doFinal(bytes);
			//从二进制的byte有转化为String进行返回
			return base64encoder.encode(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}



	/*这个是获取解密之后的信息，同样原理*/
	public static String getDecryptString(String str) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			byte[] bytes = base64decoder.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	/*这个就是输入你想要加密的数据，然后输出自己能够看到*/
	public static void main(String[] args) {
		System.out.println(getEncryptString("wangwei"));
		System.out.println(getEncryptString("wang5872256"));

	}

}