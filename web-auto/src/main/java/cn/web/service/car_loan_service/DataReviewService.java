package cn.web.service.car_loan_service;

import org.openqa.selenium.WebDriver;

import cn.web.common.GetOrdeProcess;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.DataReviewPage;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;


public class DataReviewService extends DataReviewPage{

	public DataReviewService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}


	/**资料审核待处理队列**/
	public void carSysDataReviewPendingQueue(){
		
		LoanFrontPubOperat.leftNavigation(selenium, "data_review", "car_iframe_112108", testDataModel.getDescription());
		
		GetOrdeProcess.getOrder(selenium, getOrderBtn(), basicTestDataBean.getApplyNum(),getWaitQueueProcessNum(),getTaskPoolNum(),getTodayProcessNum_3());

		selenium.dclick(getDataReviewProcessBut(), "资料审核  处理");
		
	}
	
	/**资料审核提交**/
	public void dataViewSubmitOpera(SeleniumUtil selenium,String applyNum){
		
		String checkPoint = GetElemFilePropertyUtil.checkPointPro.getproperValue("check_submit_sucess");
		WebDriver driver = selenium.getDriver();
		
		selenium.dclick(getEvaluateSubmit(), "提交");
		
		SeleniumUtil.sleep(1);
		
		if(!selenium.getDriver().getPageSource().contains("操作确认")){
			
			selenium.jsClick(getEvaluateSubmit(), "提交");
			
			SeleniumUtil.sleep(1);
		}
		
		selenium.dclick(getBlurDetermine(),"确定按钮");
	
		if(MyAssert.myAssertTrue(driver,checkPoint) == false){

			selenium.jsClick(getBlurDetermine(),"确定按钮");
			
			if(MyAssert.myAssertTrue(driver,checkPoint) == false){
				
				MyAssert.myAssertFalse("断言失败，么有出现期望值：["+checkPoint+"]");
				
			}
		}
		
		LoanFrontPubOperat.alreadProcessQueue(selenium,applyNum);

	}
	
	/**资料审核回退**/
	public void dataViewBackOpera(SeleniumUtil selenium){
		
		selenium.click(getBackBtn(), "回退");
		
		selenium.click(getMainReasonSelect(), "主原因下拉");
		SeleniumUtil.sleep(0.5);
		selenium.click(getMainReason(), "影像件有误");
		SeleniumUtil.sleep(0.5);
		selenium.click(getSubReasonSelect(), "子原因下拉");
		SeleniumUtil.sleep(0.5);
		selenium.click(getSubReason(), "缺少影像件");
		
		selenium.dclick(getConfirmBtn(),GetElemFilePropertyUtil.checkPointPro.getproperValue("back_sucess"),"确认按钮");

	}
}
