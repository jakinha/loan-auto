package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class ContractReviewPage extends PageBase{

	protected ContractReviewPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}

	//确认
	protected WebElement getConfirm(){
		return selenium.getWebElement(carElementProperty.getproperValue("confirm"));
	}
	
	//合同审核处理
	protected WebElement getContractReviewProcessBut(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("contract_review_process").replace("订单号", orderNo));
	}
	

}
