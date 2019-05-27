package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


/**
 * 复议
 * @author huangjun
 *
 */
public class ReconsiderationPage extends PageBase{

	protected ReconsiderationPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);
		
	}
	protected ReconsiderationPage(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super(selenium, basicTestDataBean);
		
	}
	
	//审核复议
	protected WebElement getReconsideration(){
		return selenium.getWebElement(carElementProperty.getproperValue("reconsideration"));
	}
	//申请复议
	protected WebElement getEvaluateProcess(){
		return selenium.getWebElement(carElementProperty.getproperValue("reconsideration_process").replace("订单号", orderNo));
	}
	//复议原因
	protected WebElement getReconsiderationWhy(){
		return selenium.getWebElement(carElementProperty.getproperValue("reconsideration_why"));
	}
	
	
}
