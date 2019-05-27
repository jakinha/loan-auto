package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class ChangBankCardPage extends PageBase{
	
	protected ChangBankCardPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	
	//更换银行卡菜单
	protected WebElement getChangBankCardMenu(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("chang_bank_card_menu"));
	}
	
	//搜索订单号框
	protected WebElement getSearchOrderInput(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("chang_bank_search_order_no"));
		
	}
	
	//更换银行卡按钮
	protected WebElement getChangBankCardBtn(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("chang_bank_btn").replace("订单号", orderNo));
		
	}
	
	//更换
	protected WebElement getChang(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("chang"));
	}
	
	//x图标
	protected WebElement getXIcon(){
		return selenium.getWebElement(carElementProperty.getproperValue("chang_bank_card_close_icon"));
		
	}
	
	//更换银行卡按钮
	protected WebElement getChangBankCardAnnalBtn(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("chang_bank_anna_btn").replace("订单号", orderNo));
		
	}
	
	//添加银行卡的  提交  按钮:同门店签约
	protected WebElement getChangBankNoSubmit(){
		return selenium.getWebElement(carElementProperty.getproperValue("store_signing_bank_no_submit"));
	}
	
	//旧银行卡号
	protected WebElement getOldBankNoText(){
		return selenium.getWebElement(carElementProperty.getproperValue("old_bank_no"));
	}
	
	//新银行卡号
	protected WebElement getNewBankNoText(){
		return selenium.getWebElement(carElementProperty.getproperValue("new_bank_no"));
	}
	
	//执行人
	protected WebElement getChangBankNoRule(){
		return selenium.getWebElement(carElementProperty.getproperValue("chang_rule"));
	}
	
}
