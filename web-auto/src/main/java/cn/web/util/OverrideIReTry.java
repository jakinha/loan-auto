package cn.web.util;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class OverrideIReTry implements IRetryAnalyzer{

	public static Logger logger = Logger.getLogger(OverrideIReTry.class);
	
	public int initRetryNum = 1;
	public int maxRetryNum = 3;
	
	@Override
	public boolean retry(ITestResult result) {

		if(initRetryNum<maxRetryNum){
			
			String message = "方法<"+result.getName()+">执行失败，重试第"+initRetryNum+"次";
			logger.info(message);

			initRetryNum++;
			return true;
		}

		return false;
	}
	
	// 每次跑完一条用例后，重置retryCount为0，这样dataProvider 数据驱动测试也支持
    public void reset() {
    	initRetryNum = 1;
    }

}
