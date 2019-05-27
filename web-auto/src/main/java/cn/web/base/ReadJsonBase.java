package cn.web.base;

import com.jayway.jsonpath.JsonPath;

/**
 * 封装这个方法，是防止json格式的测试数据写丢了键值捕获异常
 * @author Administrator
 *
 */
public class ReadJsonBase {
	
	public static String readJson(String testData, String jsonPath){
		try{
			return	JsonPath.read(testData,jsonPath).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;	
	}
}
