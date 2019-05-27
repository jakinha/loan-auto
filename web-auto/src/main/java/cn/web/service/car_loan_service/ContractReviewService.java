package cn.web.service.car_loan_service;

import cn.web.common.GetOrdeProcess;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.ContractReviewPage;
import cn.web.util.SeleniumUtil;

/**
 * 合同审核
 * @author huangjun
 *
 */
public class ContractReviewService extends ContractReviewPage{

	public ContractReviewService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}

	
	/**合同审核待处理队列**/
	public void contractReviewPendingQueue(){
		
		LoanFrontPubOperat.leftNavigation(selenium, "contract_review", "car_iframe_112110", testDataModel.getDescription());
		
		GetOrdeProcess.getOrder(selenium, getOrderBtn(), basicTestDataBean.getApplyNum(), getWaitQueueProcessNum(),getTaskPoolNum(),getTodayProcessNum_3());

		selenium.dclick(getContractReviewProcessBut(), "合同审核  处理");
	}
	
	/**合同审核提交**/
	public void contractReviewSubmitOpera(SeleniumUtil selenium,String applyNum){
		
		selenium.click(getEvaluateSubmit(), "提交");
		
		selenium.dclick(getConfirm(),"确认");
		
		LoanFrontPubOperat.alreadProcessQueue(selenium,applyNum);
		
	}

}
