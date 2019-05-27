package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


/**
 * 流程监控 页面
 * @author huangjun
 *
 */
public class ProcessMonitoringPage extends PageBase{

	public ProcessMonitoringPage(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		super(testDataModel,selenium,basicTestDataBean);
	}
	
	public ProcessMonitoringPage(){}
	
	//流程监控菜单
	protected WebElement getProcessListener(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("process_listener"));
	}
	
	//合同状态字段值
	protected WebElement contractStatus(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("process_listener_contract_status").replace("订单号", orderNo));
	}
	
	//待处理队列的订单号元素
	protected WebElement orderNoElement(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("order_element").replace("订单号", orderNo));
	}
}
