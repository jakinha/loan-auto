package cn.web.service.car_loan_service;

import org.apache.commons.lang3.StringUtils;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.IntegratedQueryPage;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;


/**
 * 综合查询、厦金中心综合查询
 * @author huangjun
 *
 */
public class IntegratedQueryService extends IntegratedQueryPage{

	
	public IntegratedQueryService(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		super(testDataModel, selenium, basicTestDataBean);
	}
	
	public IntegratedQueryService(){}
	
	/**
	 * 综合查询-合同状态、详情页的头信息
	 * @param isHavecontractStatus\
	 * 
	 * 			待放款前：合同状态字段为空
	 * 
	 * 			已放款后：合同状态字段为"正常"
	 */
	public void integratedQueryContractStatus(String isHavecontractStatus){
		
		search("综合查询");
		
		String contractStatus = selenium.getText(contractStatus());
		
		if(isHavecontractStatus.equalsIgnoreCase("null")){
			
			if(StringUtils.isBlank(contractStatus)){
				logger.info("放款之前的合同状态是空的，正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之前的合同状态不是空的，合同状态不应该是："+contractStatus);
			}
		}
		else if(isHavecontractStatus.equalsIgnoreCase("0")){
			
			if(StringUtils.isNotBlank(contractStatus) && contractStatus.equals("正常")){
				logger.info("放款之后的合同状态是[正常],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的合同状态是空的，合同状态不应该是：["+contractStatus+"]");
			}
		}
		
		selenium.jsClick(orderNoElement(), "单击综合查询待处理队列的-订单号");
		
		
		new DetailsPageServices().headInfo(selenium, testDataModel, basicTestDataBean);
	}
	
	/**
	 * 厦金中心综合查询订单记录
	 * 
	 * @param isHaveOrderList
	 * 
	 * 			待放款前：无订单记录
	 * 
	 * 			已放款后：有订单记录，并且状态为已放款
	 */
	public void xiaJinCenterQuery(String isHaveOrderList){
		
		search("厦金中心");
		
		if(isHaveOrderList.equalsIgnoreCase("null")){
			
			if(selenium.getDriver().getPageSource().contains("没有匹配订单，请重新进行搜索...")){
				
				logger.info("待放款订单不在厦金中心查询的列表里,功能通过");
			}
			else{
				
				MyAssert.myAssertFalse("待放款订单在厦金中心查询的列表里,功能不通过");
			}
		}
		else if(isHaveOrderList.equalsIgnoreCase("0")){
			
			String xiaJinOrderStatus = selenium.getText(xiaJinOrderStatus());
			
			if(xiaJinOrderStatus.equals("已放款")){
				
				logger.info("放款之后的厦金中心订单状态是[已放款],正确的");
			}
			else{
				MyAssert.myAssertFalse("放款之后的厦金中心订单状态是空的，合同状态不应该是：["+xiaJinOrderStatus+"]");
			}
		}
		
	}
	
	private void search(String menuDescripe){
		
		selenium.refreshPage();
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		
		if(menuDescripe.equals("综合查询")){
			selenium.jsClick(integratedQueryMenu(), menuDescripe+"菜单");
		}
		
		else if(menuDescripe.equals("厦金中心")){
			selenium.jsClick(xiaJinCenterQueryMenu(), menuDescripe+"菜单");
		}
		
		else {
			MyAssert.myAssertFalse("没有传入一个有效的判断条件");
		}
		
		LoanFrontPubOperat.processMuiltISameIframe(selenium,"重置",menuDescripe);
		
		if(StringUtils.isBlank(basicTestDataBean.getApplyNum())){
			
			MyAssert.myAssertFalse("申请录入-填写基本信息用例失败,订单号未生产(或订单未流转到目标节点),故后续的用例都将失败");
		}
		
		selenium.input(selenium.getWebElement(carElementProperty.getproperValue("search_order_no")), basicTestDataBean.getApplyNum(), "搜索订单号:");
		
		selenium.jsClick(getSearchBtn(), menuDescripe+"的搜索按钮");
		
		SeleniumUtil.sleep();
	}

}
