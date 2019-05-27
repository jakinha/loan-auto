package cn.web.cases.creditsys;

import org.testng.annotations.Test;

import cn.web.base.CreditSysBasicData;
import cn.web.dao.sql.SqlUtil;
import cn.web.data_provider.credit_sys.ApplyOrderDataprovider;
import cn.web.dto.TestDataModel;
import cn.web.service.credit_service.ApplyOrderWriteInfoService;
import cn.web.service.credit_service.ApprovalProcessService;


/**
 * 初审回退补件
 * @author Administrator
 *
 */
public class FirstTrialFallbacks extends CreditSysBasicData{

	@Test(description="借款申请",priority = 1,dataProvider="applyData",dataProviderClass=ApplyOrderDataprovider.class)
	public void ploanApplication(TestDataModel testDataBean){

		loginBase.creditSysLogin(selenium,testDataBean);
		
		ApplyOrderWriteInfoService.addApplyLoan(selenium, testDataBean, basic);
		
	}
	
	@Test(description="申请详情",dataProvider="applyFullInfoWriteData",dependsOnMethods="ploanApplication",dataProviderClass=ApplyOrderDataprovider.class)
	public void applyFullInfoWrite(TestDataModel testDataBean){

		loginBase.creditSysLogin(selenium,testDataBean);
		
		ApplyOrderWriteInfoService.applyFullInfoWrite(selenium, testDataBean, basic);
	}
	

	@Test(description="录入复核",dataProvider="reviewData",dependsOnMethods = "applyFullInfoWrite",dataProviderClass=ApplyOrderDataprovider.class)//, dependsOnMethods = {"ploanApplication"}
	public void entryReview(TestDataModel testDataBean){
	
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.entryReviewAction(selenium, testDataBean, basic);
	}
	
	@Test(description="信贷初审",dataProvider="firstTrialData",dependsOnMethods = "entryReview",dataProviderClass=ApplyOrderDataprovider.class)//, dependsOnMethods = {"entryReview"}
	public void firstTrial(TestDataModel testDataBean){

		if(testDataBean.getDescription().equals("初审")){
			loginBase.creditSysLogin(selenium,testDataBean);
			new SqlUtil().firstViewGetOrder(ApprovalProcessService.orderNoResource(basic));
			approvalFunction.firstReviewAction(selenium, testDataBean, basic);
		}
		else{
			System.out.println("初审提交不需要执行......");
		}
	}
	
	@Test(description="信贷初审回退补件",dataProvider="firstTrialFallbacksData",dependsOnMethods="firstTrial",dataProviderClass=ApplyOrderDataprovider.class)
	public void firstTrialFallbacks(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		
		approvalFunction.firstTrialFallbacksAction(selenium,testDataBean,basic);
	}
	
	@Test(description="信贷初审回退补件提交",dataProvider="firstTrialFallbacksSubmitData",dependsOnMethods="firstTrialFallbacks",dataProviderClass=ApplyOrderDataprovider.class)
	public void firstTrialFallbacksSubmit(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		ApplyOrderWriteInfoService.fallBackSubmit(selenium,basic,testDataBean);
	}
	
}
