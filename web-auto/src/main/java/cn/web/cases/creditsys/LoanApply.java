package cn.web.cases.creditsys;

import org.testng.annotations.Test;

import cn.web.base.CreditSysBasicData;
import cn.web.common.constant.WebPlatformConstant;
import cn.web.dao.sql.SqlUtil;
import cn.web.data_provider.credit_sys.ApplyOrderDataprovider;
import cn.web.dto.TestDataModel;
import cn.web.service.credit_service.ApplyOrderWriteInfoService;
import cn.web.service.credit_service.ApprovalProcessService;
import hxph.core.ExcuteTieCard;
/**
 * 信 贷 系 统 用 例
 * @author Administrator
 *
 */
public class LoanApply extends CreditSysBasicData{
	
	@Test(description="借款申请",dataProvider="applyData",dataProviderClass=ApplyOrderDataprovider.class)
	public void ploanApplication(TestDataModel testDataBean){

		loginBase.creditSysLogin(selenium,testDataBean);
		ApplyOrderWriteInfoService.addApplyLoan(selenium, testDataBean, basic);
		
	}
	
	@Test(description="申请详情",dataProvider="applyFullInfoWriteData",dependsOnMethods="ploanApplication",dataProviderClass=ApplyOrderDataprovider.class)
	public void applyFullInfoWrite(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		
		ApplyOrderWriteInfoService.applyFullInfoWrite(selenium, testDataBean, basic);
	}
	

	@Test(description="录入复核",dataProvider="reviewData",dependsOnMethods="applyFullInfoWrite",dataProviderClass=ApplyOrderDataprovider.class)
	public void entryReview(TestDataModel testDataBean){
	
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.entryReviewAction(selenium, testDataBean, basic);
	}
	
	@Test(description="信贷初审",dataProvider="firstTrialData",dependsOnMethods ="entryReview",dataProviderClass=ApplyOrderDataprovider.class)
	public void firstTrial(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		
		if(testDataBean.getDescription().equals("初审")){

			if(!System.getProperty("productType").contains("信托"))
				
				new SqlUtil().firstViewGetOrder(ApprovalProcessService.orderNoResource(basic));
			
			approvalFunction.firstReviewAction(selenium, testDataBean, basic);
		}
		else if(testDataBean.getDescription().equals("初审提交")){
			approvalFunction.reviewSubmit(selenium, testDataBean,basic,"firstReview","信贷初审");
		}
	}

	@Test(description="信贷终审",dataProvider="finalTrialData",dependsOnMethods = "firstTrial",dataProviderClass=ApplyOrderDataprovider.class)
	public void endTrial(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		
		if(testDataBean.getDescription().equals("终审")){
			approvalFunction.finalReviewAction(selenium, testDataBean, basic);
		}
		else if(testDataBean.getDescription().equals("终审提交")){
			approvalFunction.reviewSubmit(selenium, testDataBean,basic,"finalApproval","信贷终审");
			approvalFunction.finalReviewProductTypeProcess(basic);
		}
	}
	
	@Test(description="电话预约",dataProvider="telReserveData",dependsOnMethods = "endTrial",dataProviderClass=ApplyOrderDataprovider.class)
	public void telReserve(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.phoneReservation(selenium, testDataBean, basic);
		
		if(System.getProperty("productType").contains("信托")){
			System.out.println("开始绑卡了。。。。。。。。");
			ExcuteTieCard.excuteTieCard(System.getProperty("testEnv"),  WebPlatformConstant.trustProductTieCardApiUrl,basic.getClientName(),basic.getIdCard());
			System.out.println("绑卡结束。。。。。。。。");
		}
		
	}
	
	@Test(description="门店签约",dataProvider="storeSignedData",dependsOnMethods="telReserve",dataProviderClass=ApplyOrderDataprovider.class)
	public void storeSigned(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.storeSign(selenium, testDataBean, basic);
	}
	
	@Test(description="门店签约提交",dataProvider="storeSignedSubmitData",dependsOnMethods ="storeSigned",dataProviderClass=ApplyOrderDataprovider.class)
	public void storeSignedSubmit(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.storeSignSubmit(selenium, testDataBean, basic);
	}

	@Test(description="合同审核提交",dataProvider="checkSubmitData",dependsOnMethods="storeSignedSubmit",dataProviderClass=ApplyOrderDataprovider.class)
	public void contractTrialSubmit(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.contractReviewSubmit(selenium, testDataBean, basic);
	}
	
	@Test(description="信托计划匹配/p2p订单放款",dataProvider="trustPlanMatchData",dependsOnMethods="contractTrialSubmit",dataProviderClass=ApplyOrderDataprovider.class)
	public void trustPlanMatch(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		approvalFunction.contractTrialAfterProcess(selenium , basic);
	}

}
