package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class IntegratedQueryPage extends PageBase{

	public IntegratedQueryPage(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		super(testDataModel, selenium, basicTestDataBean);
	}
	
	public IntegratedQueryPage(){
		
	}
	
	//综合查询菜单
	protected WebElement integratedQueryMenu(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("integrated_query_menu"));
	}
	
	//厦金中心综合查询菜单
	protected WebElement xiaJinCenterQueryMenu(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("xiajin_center_menu"));
	}
	
	//合同状态字段值
	protected WebElement contractStatus(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("integrated_query_contract_status").replace("订单号", orderNo));
	}
	
	//厦金中心订单状态字段值
	protected WebElement xiaJinOrderStatus(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("xiajin_order_status").replace("订单号", orderNo));
	}
	
	
	//待处理队列的订单号元素
	protected WebElement orderNoElement(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("order_element").replace("订单号", orderNo));
	}
}
