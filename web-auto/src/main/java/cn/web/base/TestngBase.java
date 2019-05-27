package cn.web.base;


import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import cn.web.dto.BasicTestDataBean;
import cn.web.util.IdCardGenerator;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;


public class TestngBase{
	
	public SeleniumUtil selenium;
	
	public LoginBase loginBase =new LoginBase();
	
	public BasicTestDataBean basic = new BasicTestDataBean();
	
	public Logger logger = Logger.getLogger(TestngBase.class);
	
	@BeforeMethod
	@Parameters({"modulesCase"})
	public void befor(String modulesCase,Method method){
		
		logger.info("【"+modulesCase+"】"+"功  能 之["+method.getName()+"] 用  例  开  始  执  行  ！！");
	}
	
	@AfterMethod
	@Parameters({"modulesCase"})
	public void after(String modulesCase,Method method){

		logger.info("【"+modulesCase+"】"+"功  能 之["+method.getName()+"] 用  例  执  行  结  束  ！！");
	}
	
	@BeforeTest
	public void installBasicData(){

		if(StringUtils.isNotBlank(System.getProperty("cname"))&&StringUtils.isNotBlank(System.getProperty("cphone"))&&StringUtils.isNotBlank(System.getProperty("cidcard"))){
			basic.setClientName(System.getProperty("name"));
			basic.setPhoneNum(System.getProperty("phone"));
			basic.setIdCard(System.getProperty("idcard"));
		}
		else{ 
			basic.setClientName(RandomUtil.randomStr(1) + RandomUtil.randomStr(2));
			basic.setPhoneNum("183"+ RandomUtil.randomNum(8));
			basic.setIdCard(new IdCardGenerator().generate());
		}
		setBrower();
	}
	
	/**默认使用谷歌浏览器**/
	public void setBrower(){
		System.setProperty("browerType", "chrome_browser");
	}
	
	public SeleniumUtil getSelenium(){
		return selenium;
	}
	
	public BasicTestDataBean getBasicTestDataBean(){
		return basic;
	}
}
