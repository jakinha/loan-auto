package cn.web.service.car_loan_service;

import org.apache.commons.lang3.StringUtils;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.ProcessMonitoringPage;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;


/**
 * 流程监控
 * @author huangjun
 *
 */
public class ProcessMonitoringService extends ProcessMonitoringPage{
	
	public ProcessMonitoringService(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		super(testDataModel, selenium, basicTestDataBean);
	}
	
	public ProcessMonitoringService(){}
	
	
	/**
	 * 待处理队列的合同状态、详情页头信息字段
	 * 						
	 * @param contractStatus
	 * 						   "null"：(放款之前)合同状态为空 ,
	 * 							0：正常,
	 * 							1：逾期,
	 * 							10：正常结清,
	 * 							20：提前结清,
	 * 							30：逾期结清,
	 * 							40：强制结清,
	 * 							50：对公提前结清
	 */
	
	public void processMonitoringContractStatusHeadInfo(String contractStatus){
		
		selenium.refreshPage();
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		
		selenium.jsClick(getProcessListener(), "流程监控菜单");
		
		LoanFrontPubOperat.processMuiltISameIframe(selenium,"重置","流程监控");
		
		if(StringUtils.isBlank(basicTestDataBean.getApplyNum())){
			
			MyAssert.myAssertFalse("申请录入-填写基本信息用例失败,订单号未生产(或订单未流转到目标节点),故后续的用例都将失败");
		}
		
		selenium.input(selenium.getWebElement(carElementProperty.getproperValue("search_order_no")), basicTestDataBean.getApplyNum(), "搜索订单号:");
		
		selenium.jsClick(getSearchBtn(), "流程监控的搜索按钮");
		
		String contractStatusText = selenium.getText(contractStatus());
		
		if(contractStatus.equalsIgnoreCase("null")){
			
			if(StringUtils.isBlank(contractStatusText)){
				logger.info("放款之前的合同状态是空的，正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之前的合同状态不是空的，合同状态不应该是："+contractStatusText);
			}
		}
		else if(contractStatus.equalsIgnoreCase("0")){
			
			if(StringUtils.isNotBlank(contractStatusText)){
				logger.info("放款之后的合同状态是[正常],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态显示不对，合同状态不应该是："+contractStatusText);
			}
		}
		else if(contractStatus.equalsIgnoreCase("1")){
			
			if(StringUtils.isNotBlank(contractStatusText) && contractStatusText.equals("逾期")){
				logger.info("放款之后的合同状态是[逾期],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后逾期的合同状态显示不对，合同状态不应该是：["+contractStatusText+"]");
			}
		}
		else if(contractStatus.equalsIgnoreCase("10")){
			
			if(StringUtils.isNotBlank(contractStatusText) && contractStatusText.equals("正常结清")){
				logger.info("放款之后的合同状态是[正常结清],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态显示不对，合同状态不应该是：["+contractStatusText+"]");
			}
		}
		else if(contractStatus.equalsIgnoreCase("20")){
	
			if(StringUtils.isNotBlank(contractStatusText) && contractStatusText.equals("提前结清")){
				logger.info("放款之后的合同状态是[提前结清],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态显示不对，合同状态不应该是：["+contractStatusText+"]");
			}
		}
		else if(contractStatus.equalsIgnoreCase("30")){
	
			if(StringUtils.isNotBlank(contractStatusText) && contractStatusText.equals("逾期结清")){
				logger.info("放款之后的合同状态是[逾期结清],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态显示不对，合同状态不应该是：["+contractStatusText+"]");
			}
		}
		else if(contractStatus.equalsIgnoreCase("40")){
	
			if(StringUtils.isNotBlank(contractStatusText) && contractStatusText.equals("强制结清")){
				logger.info("放款之后的合同状态是[强制结清],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态显示不对，合同状态不应该是：["+contractStatusText+"]");
			}
		}
		else if(contractStatus.equalsIgnoreCase("50")){
	
			if(StringUtils.isNotBlank(contractStatusText) && contractStatusText.equals("对公提前结清")){
				logger.info("放款之后的合同状态是[对公提前结清],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态显示不对，合同状态不应该是：["+contractStatusText+"]");
			}
		}
		
		
		selenium.jsClick(orderNoElement(), "单击流程监控待处理队列的-订单号");
		
		
		new DetailsPageServices().headInfo(selenium, testDataModel, basicTestDataBean);

	}
	
	/**
	 * 流程监控,进行订单的重新分配
	 * @param selenium
	 * @param orderNo	订单号	
	 * @param salesman	要分配到的业务员姓名代号
	 */
	public void processMonitoringReAssign(String orderNo,String salesmanCode){
		
		selenium.refreshPage();
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		
		selenium.jsClick(getProcessListener(), "流程监控");
		
		LoanFrontPubOperat.processMuiltISameIframe(selenium,"重置","流程监控");
		
		LoanFrontPubOperat.searchAssignOperat(selenium,orderNo, salesmanCode);
		
	}
}
