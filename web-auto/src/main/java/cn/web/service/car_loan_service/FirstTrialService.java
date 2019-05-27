package cn.web.service.car_loan_service;

import org.apache.commons.lang3.StringUtils;

import cn.web.base.ReadJsonBase;
import cn.web.common.GetOrdeProcess;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.FirstTrialPage;
import cn.web.util.Constant;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;


public class FirstTrialService extends FirstTrialPage{
	
	PropertyUtil propertyUtil = new PropertyUtil(Constant.PUBLIC_CONFIG);

	public FirstTrialService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	
	public FirstTrialService( SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super( selenium, basicTestDataBean);

	}
	
	/**初审队列界面**/
	public void carSysFirstTrialPendingQueue(){
		
		LoanFrontPubOperat.leftNavigation(selenium, "first_trial", "car_iframe_112107", testDataModel.getDescription());

		SeleniumUtil.sleep();
		
		//判断获单状态开关是否关闭和开启获单成功与否
		if(!selenium.getDriver().getPageSource().contains("申请编号") && !selenium.getDriver().getPageSource().contains("开启")){
			
			selenium.click(getStartGetOrder(), "开启获单");
			selenium.click(getDetermine(), "确定");
			SeleniumUtil.sleep();
			
			MyAssert.pageIsHaveExceptionHint(selenium.getDriver());
			
			if(selenium.getDriver().getPageSource().contains("确定")){//判断开启获单是否失败
				
				selenium.click(getDetermine(), "确定");
				SeleniumUtil.sleep(3);
				
				carSysFirstTrialPendingQueue();
			}
		}
		
		GetOrdeProcess.getOrder(selenium,getOrderBtn(),basicTestDataBean.getApplyNum(),getWaitQueueProcessNum(),getTaskPoolNum(),getTodayProcessNum_4());

		selenium.dclick(getEvaluateProcess(), "初审 审核处理");
		
	}
	
	/**被分配的角色的初审队列界面**/
	public void carSysAssignFirstTrialPendingQueue(){
		
		LoanFrontPubOperat.leftNavigation(selenium, "first_trial", "car_iframe_112107", testDataModel.getDescription());

		selenium.jsClick(getEvaluateProcess(), "审核处理");
		
	}
	
	/**初审意见**/
	public void carSysFirstTrial(){
		
		try {
			selenium.jsClick(getApprovalComments(), "审批意见项");
		} catch (Exception e) {
			selenium.click(getApprovalComments(), "审批意见项");
		}
		String readJson = ReadJsonBase.readJson(testDataModel.getTestData(), "$.data.trialMoney");

		if(!StringUtils.isNotBlank(readJson)){
			MyAssert.myAssertFalse("初审审批金融为空了，请检查测试用例的数据");
		}
		
		selenium.input(getTrialMoney(),readJson, "审批金额");

		int count=1;
		do{
			selenium.jsClick(getTermSelect(), "审批期数下拉");
			SeleniumUtil.sleep(1);
			if(false == getTermByEleStatus()){
				selenium.jsClick(getTermSelect(), "审批期数再次下拉");
				count++;
				System.out.println("count："+count);
			}
			else{
				System.out.println("期数元素出现了！！");
				break;
			}
		}
		while(count<5);
		if(count>=5){
			MyAssert.myAssertFalse("初审意见的产品期数没选择好,脚本待优化");
		}
		
		selenium.jsClick(getTerm(), "审批期数");
		
		selenium.input(getApprovalCommentsInputbox(), ReadJsonBase.readJson(testDataModel.getTestData(), "$.data.first_approval_comments"), "审批意见");
	
		selenium.jsClick(getApprovalCommentsSave(),GetElemFilePropertyUtil.checkPointPro.getproperValue("save_approval_comments"),"初审意见保存按钮");
	}
	
	

}
