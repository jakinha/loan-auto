package cn.web.page.car_loan_extension_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


/**
 * 申请展期页面
 * @author huangjun
 *
 */
public class ApplyExtensionPage extends PageBase{

	public ApplyExtensionPage(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
			
		super(testDataModel,selenium,basicTestDataBean);
	}
	
	//已处理标签按钮
	protected WebElement alreadyProcessLable(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("already_process_lable"));
	}
	//待处理标签按钮
	protected WebElement waitProcessLable(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("wait_process_lable"));
	}
	//订单号搜索输入框
	protected WebElement businessNoSearchInput(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("business_no_search_input"));
	}
	//客户姓名搜索输入框
	protected WebElement clientNameSearchInput(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("client_name_search_input"));
	}
	//客户身份证搜索输入框
	protected WebElement clientIdcardSearchInput(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("client_idcard_search_input"));
	}
	//搜索按钮
	protected WebElement searchBtn(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("search_btn"));
	}
	//队列记录中申请展期按钮
	protected WebElement applyExtensionBtn(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("apply_extension_btn"));
	}
	//申请展期的备注输入框
	protected WebElement extensionApplyRemark(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("extension_apply_remark"));
	}
	//申请展期提交的弹框里的确定按钮
	protected WebElement determine(){
		
		return selenium.getWebElement(carExtensionElementProperty.getproperValue("determine"));
	}
	
/*************************************************************检查点元素***************************************************************************************/	
	
	//客户姓名字段
	protected WebElement fieldClientName(){

		return selenium.getWebElement(carExtensionElementProperty.getproperValue("field_client_name").replace("订单号",orderNo));
	}
	//产品字段
	protected WebElement fieldProductName(){

		return selenium.getWebElement(carExtensionElementProperty.getproperValue("field_product").replace("订单号",orderNo));
	}
	//融资金额字段
	protected WebElement fieldFinancingSum(){

		return selenium.getWebElement(carExtensionElementProperty.getproperValue("field_financingSum").replace("订单号",orderNo));
	}
	//放款时间字段
	protected WebElement fieldPutoutDate(){

		return selenium.getWebElement(carExtensionElementProperty.getproperValue("field_putout_date").replace("订单号",orderNo));
	}
	
}
