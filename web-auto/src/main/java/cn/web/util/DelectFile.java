package cn.web.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import cn.web.common.constant.WebPlatformConstant;


public class DelectFile {
	
	/**删除截图文件及其所有子文件<默认固定路径>**/
	public static void delectFile(){
		
		String tomcatPath = WebPlatformConstant.TEST_NODE_TOMCAT_WEBAPP_PATH;
		
		String data =new SimpleDateFormat("MM").format(new Date());

		delectGoalFile(tomcatPath+"\\"+data);
	}
	
	/**删除文件及其所有子文件<动态文件路径>**/
	public static void delectGoalFile(String delectFilePath){
		
		try {
			FileUtils.deleteDirectory(new File(delectFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
