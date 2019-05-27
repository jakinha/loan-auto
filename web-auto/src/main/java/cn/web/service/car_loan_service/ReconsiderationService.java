package cn.web.service.car_loan_service;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.ReconsiderationPage;
import cn.web.util.SeleniumUtil;

/**
 * 复议功能
 * @author huangjun
 *
 */
public class ReconsiderationService extends ReconsiderationPage{

	public ReconsiderationService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}

	/**车贷系统门店录入-复议提交操作**/
	public void carSysReconsideration(){
		
		selenium.refreshPage();
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		
		selenium.jsClick(getReconsideration(),"审核复议");
		
		LoanFrontPubOperat.processMuiltISameIframe(selenium,"申请复议", "审核复议");
		
		selenium.jsClick(getEvaluateProcess(), "申请复议");
		
		selenium.input(getReconsiderationWhy(), "复议的原因就是....高兴", "复议的原因");
		
		selenium.jsClick(getStoreSubmit(),checkPointProperty.getproperValue("oprea_sucess"), "复议的提交按钮");
	
		LoanFrontPubOperat.alreadProcessQueue(selenium, basicTestDataBean.getApplyNum());
	}
}
