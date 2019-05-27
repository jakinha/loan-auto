package cn.web.service.car_loan_service;

import org.apache.commons.lang3.StringUtils;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.page.car_loan_page.PendingLoanCancePage;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;

/**
 * 待放款
 * @author huangjun
 *
 */
public class PendingLoanCanceService extends PendingLoanCancePage{
	
	public PendingLoanCanceService(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		super(selenium,basicTestDataBean);
	}
	
	public PendingLoanCanceService(){
		
	}
	
	/**
	 * 待放款-合同取消
	 */
	public void contractCance(){
		
		selenium.refreshPage();
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		
		selenium.jsClick(pendLoanCanceMenu(), "待放款取消菜单");
		
		LoanFrontPubOperat.processMuiltISameIframe(selenium,"重置","待放款取消");
		
		if(StringUtils.isBlank(basicTestDataBean.getApplyNum())){
			
			MyAssert.myAssertFalse("申请录入-填写基本信息用例失败,订单号未生产(或订单未流转到目标节点),故后续的用例都将失败");
		}
		
		selenium.input(selenium.getWebElement(carElementProperty.getproperValue("search_order_no")), basicTestDataBean.getApplyNum(), "搜索订单号:");
		
		selenium.jsClick(getSearchBtn(), "待放款取的搜索按钮");
		
		String orderStatus = selenium.getText(currentStatus());
		
		MyAssert.myAssertEquals(orderStatus, "待放款");
		
		selenium.jsClick(contractCanceBtn(), "合同取消按钮");
		
		selenium.jsClick(contractCanceMainReasonSelect(), "合同取消-主原因下拉");
		
		selenium.jsClick(contractCanceMainReason(), "合同取消-主原因：客户要求取消");
		
		selenium.input(contractCanceRemark(), "个人原因", "合同取消-填写备注信息");
		
		selenium.jsClick(contractCanceConfirmText(),checkPointProperty.getproperValue("oprea_sucess"),"合同取消的确认按钮");
		

	}
	
}
