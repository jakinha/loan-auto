package cn.web.common.carloan;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import cn.web.common.constant.WebPlatformConstant;
import cn.web.dto.BasicTestDataBean;
import cn.web.util.PropertyUtil;

public class FileProcessUtils {
	
	static Logger logger = Logger.getLogger(FileProcessUtils.class);
	
	/**制定的文件拷贝处理--供车贷展期使用**/
	public static void copyFile(){
		
		logger.info("准备进行复制操作.................");
		
		String sourceCarLoanApplyFile = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"carLoanApply.properties";
		
		String sourceFieldSurveyFile = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"fieldSurvey.properties";
	
		String goalCarLoanApplyFile = "d:"+File.separator+"extension_need_file"+File.separator+"carLoanApply.properties";
		
		String goalFieldSurveyFile = "d:"+File.separator+"extension_need_file"+File.separator+"fieldSurvey.properties";
		
		File goalCarLoanApplyFile1 = new File(goalCarLoanApplyFile);
		File goalCarLoanApplyFile2 = new File(goalFieldSurveyFile);
		
		File sourceFile1 = new File(sourceCarLoanApplyFile);
		File sourceFile2 = new File(sourceFieldSurveyFile);
		
		try {
			
			if(goalCarLoanApplyFile1.exists()){
				
				FileUtils.deleteQuietly(goalCarLoanApplyFile1);
				
				FileUtils.copyFile(sourceFile1, goalCarLoanApplyFile1);
			}
			else{
				
				FileUtils.copyFile(sourceFile1, goalCarLoanApplyFile1);
			}
			
			if(goalCarLoanApplyFile2.exists()){
				
				FileUtils.deleteQuietly(goalCarLoanApplyFile2);
				
				FileUtils.copyFile(sourceFile2, goalCarLoanApplyFile2);
			}
			else{
				
				FileUtils.copyFile(sourceFile2, goalCarLoanApplyFile2);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		logger.info("复制操作 完成！！");
	}
	
	/**拷贝对象**/
	public static <T> void copyBean(T t,T k){
		
		try {
			
			BeanUtils.copyProperties(t, k);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**创建展期需要用的客户信息的properties文件**/
	public static void createPropertiesFile(BasicTestDataBean basic,String fileNme){
		
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		
		map.put("customerName", basic.getClientName());
		
		map.put("certNo", basic.getIdCard());
		
		map.put("mobileNum", basic.getPhoneNum());
		
		map.put("productName", System.getProperty("productType"));
		
		map.put("businessNo", basic.getApplyNum());
		
		map.put("carFrameNumber", basic.getCarFrameNumber());
		
		map.put("financingSum", "50000.0");
		
		new PropertyUtil(WebPlatformConstant.RESOURCES+fileNme,1).setPropertyValue(map);
	}
	
	/**处理test-output文件里有么有testng-result.xsl文件**/
	public static void processTestngResultFile() {

		logger.info("开始判断test-output文件里有么有testng-result.xsl文件");
		
		String goalFileName = "test-output"+File.separator+"testng-results.xsl";
		
		String source = "testng-xslt"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"testng-results.xsl";
		
		File goalFile = new File(goalFileName);
		
		File srcFile = new File(source);
		
		if(!goalFile.exists()) {
			
			try {
				FileUtils.copyFile(srcFile, goalFile);
				
				logger.info("已经复制了testng-result.xsl文件到test-output文件里");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
