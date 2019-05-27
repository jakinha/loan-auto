package cn.web.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cn.web.common.constant.GetElemFilePropertyUtil;


public class MyAssert {
	
	final static Logger logger = Logger.getLogger(MyAssert.class);
	
	/**
	 * 精确比较
	 * @param actual	实际值
	 * @param expected	期望值
	 */
	public static <T> void myAssertEquals(T actual, T expected) {
	
		if(actual.equals(expected)){
			
			logger.info("断言成功:" + "| 实际值:" + actual + "| 预期值:" + expected);
		}
		else{
			
			logger.error("断言失败:" + "| 实际值:" + actual + "| 预期值:" + expected);
		}
		Assert.assertEquals(actual,expected,"断言结果:" + "| 实际值:" + actual + "| 预期值:" + expected );

	  }

	
	/**
	 * 断言界面包含指定文本
	 * @param driver
	 * @param assertText
	 */
	public static boolean myAssertTrue(WebDriver driver,String assertText) {
		
		int count = 0;
		String pageSource ;

		do{
			pageSource = driver.getPageSource();
			SeleniumUtil.sleep(0.5);
			
			if(false == exceptionHint(pageSource)){
				
				logger.error("断言失败:页面没有显示要断言的文本信息："+assertText);
				
				Assert.assertTrue(pageSource.contains(assertText),"断言失败，么有出现期望值：["+assertText+"],若截图显示正常 ,可能是正确提示将错误提示遮住了");
			}
			
			if(count>30){
				
				return false;
			}
			count ++;
			
			SeleniumUtil.sleep();
		}
		
		while(!driver.getPageSource().contains(assertText));
		
		logger.info("断言成功:页面显示了要断言的文本信息："+assertText);
		
		return true;
	  }
	
	/**
	 * 断言界面不存在指定文本
	 * @param bigText	指定的文本被包含在的文本
	 * @param assertText	指定的文本
	 * @param time
	 */
	public static void myAssertFalse(String bigText,String assertText) {
		
		if(bigText.contains(assertText)==true) {
			
			logger.error("断言失败:页面居然显示了不期望的的文本信息："+assertText);
		}
		else{
	
			logger.info("断言成功:页面没有显示要断言的文本信息："+assertText);
		}
		Assert.assertFalse(bigText.contains(assertText),"断言失败，么有出现期望值：["+assertText+"]");

	  }
	
	/**
	 * 断言界面不存在指定文本	默认等待1.5秒
	 * @param driver
	 * @param assertText
	 */
	public static void myAssertFalse(WebDriver driver,String assertText) {
		
		SeleniumUtil.sleep();
		
		String pageSource = driver.getPageSource();
		
		if(pageSource.contains(assertText)==true) {
			
			logger.error("断言失败:页面居然显示了不期望的的文本信息："+assertText);
		}
		else{
	
			logger.info("断言成功:页面没有显示要断言的文本信息："+assertText);
		}
		Assert.assertFalse(pageSource.contains(assertText),"断言失败:页面居然显示了不期望的的文本信息：["+assertText+"]");

	  }
	
	/**用例失败**/
	public static void myAssertFalse(String message){
		
		logger.error(message);
		Assert.fail(message);
	}

	
	/**
	 * 判断页面是否存在异常提示语
	 * @param pageSource
	 * @return true：无异常的提示语，false：有异常的提示语不需要继续等待正确的提示语出现了
	 */
	public static boolean exceptionHint(String pageSource){
		
		PropertyUtil propertyUtil = GetElemFilePropertyUtil.checkPointPro;
		
		if(pageSource.contains(propertyUtil.getproperValue("back_faile"))
				|| pageSource.contains(propertyUtil.getproperValue("net_exception"))
				|| pageSource.contains(propertyUtil.getproperValue("system_faile"))
				|| pageSource.contains(propertyUtil.getproperValue("next_node_not_exists"))
				|| pageSource.contains(propertyUtil.getproperValue("flow_not_started"))
				|| pageSource.contains(propertyUtil.getproperValue("survey_error"))
				|| pageSource.contains(propertyUtil.getproperValue("workflow_error"))
				|| pageSource.contains(propertyUtil.getproperValue("car_frame_number_transit"))
				|| pageSource.contains(propertyUtil.getproperValue("read_card_save"))
				|| pageSource.contains(propertyUtil.getproperValue("code_404"))
				|| pageSource.contains(propertyUtil.getproperValue("get_valuation_fail"))){
			
			
			logger.info("页面出现异常提示鸟！！");
			return false;
		}
		else{
			
			logger.info("页面没有出现异常提示！！");
			
			return true;
		}
	}
	
	public static void cyclingClickAssert(SeleniumUtil selenium,WebElement webElement,String elementName,String assertText,double sleepTime){
		
		int count = 1;
 
		do{
			
			SeleniumUtil.sleep(sleepTime);
			
			//使用js单击和双击保证其中一种单击方式不生效
			if(count % 2 != 0){
				
				selenium.jsClick(webElement,elementName);
				
			}
			else{
				selenium.click(webElement,elementName);
			}

			SeleniumUtil.sleep();
			
			if(exceptionHint(selenium.getDriver().getPageSource()) == false){
				
				MyAssert.myAssertFalse("用例失败，页面出现异常提示");
			}
			
			if(count>61){
				
				myAssertFalse("断言失败:页面没有显示要断言的文本信息："+assertText);
			}
			count ++;
			SeleniumUtil.sleep();
		}
		
		while(!selenium.getDriver().getPageSource().contains(assertText));
		
		logger.info("断言成功:页面显示了要断言的文本信息："+assertText);

	}
	
	/**判断页面是否有异常提示**/
	public static void pageIsHaveExceptionHint(WebDriver driver){

		SeleniumUtil.sleep(2);
		
		if(false == exceptionHint(driver.getPageSource())){

			MyAssert.myAssertFalse("界面弹出-系统内部错误  或  其他系统错误的提示！");
		}
	}
}
