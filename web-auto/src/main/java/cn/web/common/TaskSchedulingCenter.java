package cn.web.common;

import org.apache.commons.lang3.StringUtils;

import cn.web.common.constant.WebPlatformConstant;
import cn.web.util.KeyBoard;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;

/**
 * 任务调度中心
 * @author huangjun
 *
 */
public class TaskSchedulingCenter {

	SeleniumUtil selenium;
	
	String testEvn;

	PropertyUtil propertyUtil = new PropertyUtil(WebPlatformConstant.SCHEDU_CENTER_PATH); 

	
	/**
	 * 车贷资金任务调度
	 * @param testEvn 测试环境key
	 */
	public void carFundsTaskScheduling(String testEvn){
		
		this.testEvn = testEvn;
		
		taskSchedulingCenter();
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("carloan_funds")), "车贷资金");
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("repayment_notice")), "执行按钮");
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("determine")), "执行成功确定按钮");
		
		selenium.getDriver().quit();
	}
	
	/**
	 * 车贷贷后任务调度
	 * @param testEvn 测试环境key
	 */
	public void carLaonAfterTaskScheduling(String testEvn){
		
		this.testEvn = testEvn;
		
		taskSchedulingCenter();
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("carloan_after")), "车贷贷后");
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("carloan_after_contract_change")), "执行按钮");
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("determine")), "执行成功确定按钮");
		
		selenium.getDriver().quit();
	}
		
	
	private void taskSchedulingCenter(){
		
		selenium=new SeleniumUtil();
		
		if(StringUtils.isBlank(testEvn)){
			
			selenium.openBrower("http://192.168.11.246:8081/");
		}
		else if(testEvn.equals("sit-01")){
			
			selenium.openBrower("http://192.168.11.246:8081/");
		}
		else if(testEvn.equals("uat-02")){
			
			selenium.openBrower("http://192.168.11.142:8081/");
		}
		
		
//		selenium.input(selenium.getWebElement(propertyUtil.getproperValue("login_name")), "admin", "登陆账号");
//		
//		selenium.input(selenium.getWebElement(propertyUtil.getproperValue("login_pwd")), "admin", "登陆账号");
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("login_btn")),"登陆按钮");

		KeyBoard.enter();
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("task_manage")), "左侧任务管理器菜单");
		
		selenium.click(selenium.getWebElement(propertyUtil.getproperValue("actuator")), "执行器选项下拉");

		
	}
}
