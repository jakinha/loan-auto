package cn.web.service.car_loan_service;

import cn.web.base.ReadJsonBase;
import cn.web.common.GetOrdeProcess;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.FinallyTrialPage;
import cn.web.util.Constant;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;

public class FinallyTrialService extends FinallyTrialPage{

	PropertyUtil propertyUtil = new PropertyUtil(Constant.PUBLIC_CONFIG);
	
	public FinallyTrialService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	public FinallyTrialService(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
	
		super(selenium, basicTestDataBean);

	}
	
	/**终审队列界面**/
	public void carSysFinalTrialPendingQueue(){
		
		LoanFrontPubOperat.leftNavigation(selenium, "finally_trial", "car_iframe_112106", testDataModel.getDescription());
		
		SeleniumUtil.sleep();
		
		if(!selenium.getDriver().getPageSource().contains("申请编号") && !selenium.getDriver().getPageSource().contains("开启")){
			
			selenium.click(getStartGetOrder(), "开启获单");
			selenium.click(getDetermine(), "确定");
			SeleniumUtil.sleep();
			
			MyAssert.pageIsHaveExceptionHint(selenium.getDriver());
			
			if(selenium.getDriver().getPageSource().contains("确定")){
				selenium.click(getDetermine(), "确定");
			}
			SeleniumUtil.sleep(3);
			
			carSysFinalTrialPendingQueue();
		}
		
		GetOrdeProcess.getOrder(selenium, getOrderBtn(), basicTestDataBean.getApplyNum(),getWaitQueueProcessNum(),getTaskPoolNum(),getTodayProcessNum_4());

		selenium.dclick(getFinalTrialProcessBut(), "终审 审核  处理");
		
		MyAssert.pageIsHaveExceptionHint(selenium.getDriver());
		
	}
	
	/**终审意见**/
	public void carSysFinallyTrial(){
		
		try{

			selenium.jsClick(getApprovalComments(), "审批意见项");
		}catch(Exception e){
			selenium.click(getApprovalComments(), "审批意见项");
		}
		
		selenium.input(getApprovalCommentsInputbox(), ReadJsonBase.readJson(testDataModel.getTestData(), "$.data.final_approval_comments"), "审批意见");
	
		selenium.jsClick(getApprovalCommentsSave(),GetElemFilePropertyUtil.checkPointPro.getproperValue("save_approval_comments"),"终审意见保存按钮");
	}

}
