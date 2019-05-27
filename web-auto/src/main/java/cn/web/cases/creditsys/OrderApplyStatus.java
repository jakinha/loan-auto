package cn.web.cases.creditsys;

import org.testng.annotations.Test;

import cn.web.base.CreditSysBasicData;
import cn.web.data_provider.credit_sys.ApplyOrderDataprovider;
import cn.web.dto.TestDataModel;
import cn.web.service.credit_service.ApplyLoanStatusCanceService;
import cn.web.service.credit_service.ApplyOrderWriteInfoService;

/**
 *  借款申请订单状态操作
 * @author Administrator
 *
 */
public class OrderApplyStatus extends CreditSysBasicData{

	@Test(description="新增借款申请",dataProvider="applyData",dataProviderClass=ApplyOrderDataprovider.class)
	public void ploanApplication(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		
		ApplyOrderWriteInfoService.addApplyLoan(selenium, testDataBean, basic);
	}
	
	@Test(description="取消贷款申请",dependsOnMethods="ploanApplication",dataProvider="orderApplyCanceData",dataProviderClass=ApplyOrderDataprovider.class)
	public void orderApplyCance(TestDataModel testDataBean){
		
		loginBase.creditSysLogin(selenium,testDataBean);
		
		ApplyLoanStatusCanceService.applyLoanCance(testDataBean,selenium,basic);
	}

}
