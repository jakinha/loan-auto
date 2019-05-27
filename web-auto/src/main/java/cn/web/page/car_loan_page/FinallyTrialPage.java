package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class FinallyTrialPage extends PageBase{

	protected FinallyTrialPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	
	protected FinallyTrialPage(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super(selenium, basicTestDataBean);

	}
	
	//终审待处理审核按钮
	protected WebElement getFinalTrialProcessBut(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("final_trial_process").replace("订单号",orderNo));
	}
	
	//终审详情界面的上面终审table标签
	protected WebElement getFinallTrialTable(){
		return selenium.getWebElement(carElementProperty.getproperValue("finally_trial"));
	}
	
	
	/**同初审页面元素**/

	//开启获单
	protected WebElement getStartGetOrder(){
		return selenium.getWebElement(carElementProperty.getproperValue("start_getOrder"));
	}
	//确定
	protected WebElement getDetermine(){
		return selenium.getWebElement(carElementProperty.getproperValue("determine"));
	}
	//审批意见项
	protected WebElement getApprovalComments(){
		return selenium.getWebElement(carElementProperty.getproperValue("approval_comments"));
	}
	//审批意见
	protected WebElement getApprovalCommentsInputbox(){
		return selenium.getWebElement(carElementProperty.getproperValue("approval_comments_inputbox"));
	}
	//审批意见保存按钮
	protected WebElement getApprovalCommentsSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("approval_comments_save"));
	}

}
