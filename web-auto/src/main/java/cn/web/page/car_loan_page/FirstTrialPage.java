package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class FirstTrialPage extends PageBase{

	protected FirstTrialPage(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
			
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	protected FirstTrialPage(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
	
		super(selenium, basicTestDataBean);

	}
	
	//初审
	protected WebElement getFirstTrial(){
		return selenium.getWebElement(carElementProperty.getproperValue("first_trial"));
	}
	//初审待处理审核
	protected WebElement getEvaluateProcess(){
		return selenium.getWebElement(carElementProperty.getproperValue("first_trial_process").replace("订单号", orderNo));
	}
	//审批意见项
	protected WebElement getApprovalComments(){
		return selenium.getWebElement(carElementProperty.getproperValue("approval_comments"));
	}
	//审批金额
	protected WebElement getTrialMoney(){
		return selenium.getWebElement(carElementProperty.getproperValue("trial_money"));
	}
	//期数下拉
	protected WebElement getTermSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("term_select"));
	}
	//期数下拉
	protected WebElement getTerm(){
		return selenium.getWebElement(carElementProperty.getproperValue("term"));
	}
	//期数下拉判断期数元素在不在
	protected Boolean getTermByEleStatus(){
		return selenium.getByType(carElementProperty.getproperValue("term"));
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
