package cn.web.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.alibaba.fastjson.JSONObject;

import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;

public class PageBase {
	
	protected final static Logger logger = Logger.getLogger(PageBase.class);
	
	
	protected static PropertyUtil carElementProperty = GetElemFilePropertyUtil.carSysElePro;
	
	protected static PropertyUtil carExtensionElementProperty = GetElemFilePropertyUtil.carExtensionElePro;
	
	protected static PropertyUtil iframeProperty = GetElemFilePropertyUtil.ifmpro;
	
	protected static PropertyUtil checkPointProperty = GetElemFilePropertyUtil.checkPointPro;
	
	protected SeleniumUtil selenium;
	
	protected BasicTestDataBean basicTestDataBean;
	
	protected TestDataModel testDataModel;
	
	protected JSONObject jsonObject;
	
	protected String orderNo ;
	
	protected PageBase(TestDataModel testDataModel,SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		this.testDataModel = testDataModel;
		
		jsonObject =JSONObject.parseObject(this.testDataModel.getTestData()) ;
		
		this.selenium = selenium;
		
		this.basicTestDataBean = basicTestDataBean;
		
		this.orderNo = basicTestDataBean.getApplyNum();
	}
	
	protected PageBase(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){

		this.selenium = selenium;
		
		this.basicTestDataBean = basicTestDataBean;
		
		this.orderNo = basicTestDataBean.getApplyNum();
	}
	
	protected PageBase(){}

	//退出用户登陆
	protected WebElement getLoginOutUser(){
		return selenium.getWebElement(carElementProperty.getproperValue("login_user"));
	}
	
	//退出
	protected WebElement getLoginOut(){
		return selenium.getWebElement(carElementProperty.getproperValue("login_out"));
	}
	
	//提交
	protected WebElement getStoreSubmit(){
		return selenium.getWebElement(carElementProperty.getproperValue("submit"));
	}
	
	//提交
	protected WebElement getEvaluateSubmit(){
		return selenium.getWebElement(carElementProperty.getproperValue("evaluate_submit"));
	}
	
	//取消
	protected WebElement getEvaluateCance(){
		return selenium.getWebElement(carElementProperty.getproperValue("cance"));
	}
	
	//回退
	protected WebElement getBackBtn(){
		return selenium.getWebElement(carElementProperty.getproperValue("go_back"));
	}
	
	//获单
	protected String getOrderBtn(){
		return carElementProperty.getproperValue("get_order");
	}
	
	//待处理数
	protected String getWaitQueueProcessNum(){
		return carElementProperty.getproperValue("wait_queue_process_num");
	}
	
	//任务池件数
	protected String getTaskPoolNum(){
		return carElementProperty.getproperValue("task_pool_num");
	}
	
	//当天已处理数
	protected String getTodayProcessNum_3(){
		return carElementProperty.getproperValue("today_process_num_3");
	}
	
	//当天已处理数
	protected String getTodayProcessNum_4(){
		return carElementProperty.getproperValue("today_process_num_4");
	}
	
	//确定按钮
	protected WebElement getBlurDetermine(){
		
		WebElement webElement = selenium.getElement(carElementProperty.getproperValue("blur_determine"));
		
		if(null == webElement){
			
			MyAssert.myAssertFalse("目标元素没有找到："+carElementProperty.getproperValue("blur_determine"));
		}
		return webElement;
	}
	
	//开启获单
	protected WebElement getStartGetOrder(){
		return selenium.getWebElement(carElementProperty.getproperValue("start_getOrder"));
	}
	
	//确定
	protected WebElement getDetermine(){
		return selenium.getWebElement(carElementProperty.getproperValue("determine"));
	}
	
	//搜索按钮
	protected WebElement getSearchBtn(){
		return selenium.getWebElement(carElementProperty.getproperValue("search_btn"));
	}
	
	//主原因下拉
	protected WebElement getMainReasonSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("main_reason_select"));
	}
	
	//子原因下拉
	protected WebElement getSubReasonSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("sub_reason_select"));
	}
	
	//主原因-产品因素
	protected WebElement getMainReason(){
		return selenium.getWebElement(carElementProperty.getproperValue("main_reason_product"));
	}

	//子原因-费率高
	protected WebElement getSubReason(){
		return selenium.getWebElement(carElementProperty.getproperValue("sub_reason_rate"));
	}
	
	/**************************************************详情页头信息字段***********************************************************************************/
	
	//头信息-客户姓名
	protected WebElement heardInfoCustomerName(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_customer_name"));
	}
	//头信息-客户身份证
	protected WebElement heardInfoCustomerIdcard(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_customer_idcard"));
	}
	//头信息-产品名称
	protected WebElement heardInfoProductName(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_product_name"));
	}
	//头信息-订单号
	protected WebElement heardInfoOrderNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_order_no"));
	}
	//头信息-手机号
	protected WebElement heardInfoPhone(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_phone"));
	}
	//头信息-申请金额
	protected WebElement heardInfoFinancingAmount(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_financing_amount"));
	}
	//头信息-车架号
	protected WebElement heardInfoFrameNumber(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_frame_number"));
	}
	//头信息-进件时间
	protected WebElement heardInfoEntryTime(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_entry_time"));
	}
	//头信息-所属门店
	protected WebElement heardInfoOwnStore(){
		return selenium.getWebElement(carElementProperty.getproperValue("heard_info_own_store"));
	}
	
}
