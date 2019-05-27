package cn.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;


public class PropertyUtil {

	private Properties properties = new Properties();
	private String filePath;


	public PropertyUtil(String filePath){
		
		this.filePath=filePath;
		
		this.properties=loadResourceDirFile();

	}
	
	public PropertyUtil(String filePath,int isRootDirFile){//isRootDirFile无实际意义,只为了区别构造器
		
		this.filePath=filePath;
		
		this.properties=loadRootDirFile();
	}
	

	/**
	 * 获取配置文件的某个键对应的值
	 * 
	 * @param key
	 * @return
	 */
	public String getproperValue(String key) {
		
		String value = null;
		
		if (properties.containsKey(key)) {
			try {
				value = properties.getProperty(key).trim();
			} catch (Exception e) {
				e.printStackTrace();
				MyAssert.myAssertFalse(e.getMessage());
			}
		} else {
			MyAssert.myAssertFalse("["+this.filePath+"]属性配置文件里没有找到这个键："+key);
		}
		return value;
	}


	public void setPropertyValue(Map<String, String> propertyMap) {

		FileOutputStream out = null;

		try {
			for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
				out = new FileOutputStream(this.filePath);
				setProValue(entry.getKey(), entry.getValue(), out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 自动生产属性文件及内容
	 *
	 * @param key
	 * @param value
	 * @param fos
	 */
	private void setProValue(String key, String value, OutputStream fos) {

		try {
			properties.setProperty(key, value);
			properties.store(new OutputStreamWriter(fos, "UTF-8"), "Update  value");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	
	/**
	 * 加载 resource 目录下得 配置文件
	 * @return
	 */
	private Properties loadResourceDirFile() {

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		InputStream resourceAsStream = contextClassLoader.getResourceAsStream(this.filePath);
		BufferedReader bf = null;

		try {
			bf = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
			properties.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bf) {

				try {
					bf.close();

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			if (null != resourceAsStream) {

				try {

					resourceAsStream.close();

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	/**
	 * 加载根目录下的文件
	 * 
	 * @return
	 */
	private Properties loadRootDirFile() {

		BufferedReader bf = null;

		File file = new File(this.filePath);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath), "UTF-8"));
			properties.load(bf);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bf) {

				try {
					bf.close();

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		return properties;
	}

}
