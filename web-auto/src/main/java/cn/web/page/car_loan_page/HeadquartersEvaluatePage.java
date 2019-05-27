package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class HeadquartersEvaluatePage extends PageBase{

	protected HeadquartersEvaluatePage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	protected HeadquartersEvaluatePage(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super(selenium, basicTestDataBean);

	}
	
	//总部评估 待处理审核按钮
	protected WebElement getHeadquartersEvaluateProcessBut(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("headquarters_evaluate_process").replace("订单号",orderNo));
	}
	
	//开启获单
	protected WebElement getStartGetOrder(){
		return selenium.getWebElement(carElementProperty.getproperValue("start_getOrder"));
	}
	
	//确定
	protected WebElement getDetermine(){
		return selenium.getWebElement(carElementProperty.getproperValue("determine"));
	}
		
	//评估信息 
	protected WebElement getEvaluateInfo(){
		return selenium.getWebElement(carElementProperty.getproperValue("evaluate_info"));
	}
	
	/**以下和本地评估元素一样**/
	//获取车300估价
	protected WebElement getAppraisal(){
		return selenium.getWebElement(carElementProperty.getproperValue("get_appraisal"));
	}

	//估价结果值
	protected WebElement getAppraisalResult(){
		return selenium.getWebElement(carElementProperty.getproperValue("appraisal_result"));
	}
	//本地评估车辆信息保存按钮
	protected WebElement getCarSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_save"));
	}

	
	//总部评估的车300估计带出来的值
	protected WebElement getHeadquartersCar300RiskPrice(){
		return selenium.getWebElement(carElementProperty.getproperValue("headquarters_car300_risk_price"));
	}
	//总部评估价格
	protected WebElement getHeadquartersPrice(){
		return selenium.getWebElement(carElementProperty.getproperValue("headquarters_price"));
	}
	//总部评估保存按钮
	protected WebElement getHeadquartersEvaluateSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("headquarters_evaluate_save"));
	}
	
}
