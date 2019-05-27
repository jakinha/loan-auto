package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.util.SeleniumUtil;


public class PendingLoanCancePage extends PageBase{

	
	public PendingLoanCancePage(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		super(selenium,basicTestDataBean);
	}
	
	public PendingLoanCancePage(){
		
		
	}
	
	//待放款取消菜单
	protected WebElement pendLoanCanceMenu(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("pend_loan_cance_menu"));
	}
	
	//订单当前状态
	protected WebElement currentStatus(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("current_status").replace("订单号", orderNo));
	}
	
	//合同取消按钮
	protected WebElement contractCanceBtn(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("contract_cance_btn"));
	}
	
	
	//合同取消-主原因下拉
	protected WebElement contractCanceMainReasonSelect(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("contract_cance_main_reason_select"));
	}
	
	//合同取消-选择主原因
	protected WebElement contractCanceMainReason(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("contract_cance_main_reason"));
	}
	
	//合同取消-备注
	protected WebElement contractCanceRemark(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("contract_cance_remark"));
	}
	
	//合同取消 -确认按钮
	
	protected WebElement contractCanceConfirmText(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("confirm_text"));
	}
	
	
}
