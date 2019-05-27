package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.base.ReadJsonBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class DataReviewPage extends PageBase{

	protected DataReviewPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	
	//待处理资料审核按钮
	protected WebElement getDataReviewProcessBut(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("data_review_process").replace("订单号",orderNo));
	}
	
	//主原因
	protected WebElement getMainReason(){
		return selenium.getWebElement(carElementProperty.getproperValue(ReadJsonBase.readJson(testDataModel.getTestData(), "$.data.main_reason")));
	}
	
	//子原因
	protected WebElement getSubReason(){
		return selenium.getWebElement(carElementProperty.getproperValue(ReadJsonBase.readJson(testDataModel.getTestData(), "$.data.sub_reason")));
	}
	//回退弹框的确认按钮
	protected WebElement getConfirmBtn(){
		return selenium.getWebElement(carElementProperty.getproperValue("confirm_contains_text"));
	}
	

}
