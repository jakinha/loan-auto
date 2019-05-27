package cn.web.service.car_loan_service;

import org.apache.commons.lang3.StringUtils;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;


/**
 * 订单详情页
 * @author huangjun
 *
 */
public class DetailsPageServices extends PageBase{

	public DetailsPageServices(){}
	
	/**
	 * 头信息
	 * @param seleniumUtil
	 * @param testDataModel
	 * @param basicTestDataBean
	 */
	public void headInfo(SeleniumUtil seleniumUtil,TestDataModel testDataModel,BasicTestDataBean basicTestDataBean){
		
		logger.info("开始对详情页的头信息字段检查。。。。。。。");
		
		SeleniumUtil.sleep(2);
		
		String heardInfoCustomerName = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_customer_name")));
		
		String heardInfoCustomerIdcard = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_customer_idcard")));
		
		String heardInfoProductName = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_product_name")));
		
		String heardInfoOrderNo = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_order_no")));
		
		String heardInfoPhone = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_phone")));
		
		String heardInfoFinancingAmount = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_financing_amount")));
		
		String heardInfoFrameNumber = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_frame_number")));
		
		String heardInfoEntryTime = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_entry_time")));
		
		String heardInfoOwnStore = seleniumUtil.getText(seleniumUtil.getWebElement(carElementProperty.getproperValue("heard_info_own_store")));
		
		String heardInfoFinancingAmountFM = heardInfoFinancingAmount.replace(",", "");
		
		MyAssert.myAssertEquals(heardInfoCustomerName, basicTestDataBean.getClientName());
		
		if(StringUtils.isNotBlank(heardInfoCustomerIdcard)){
			logger.info("头信息的身份证号存在");
		}
		else{
			MyAssert.myAssertFalse("头信息的身份证号字段值为空");
		}
		
		MyAssert.myAssertEquals(heardInfoProductName, System.getProperty("productType"));
		
		MyAssert.myAssertEquals(heardInfoOrderNo, basicTestDataBean.getApplyNum());
		
		if(StringUtils.isNotBlank(heardInfoPhone)){
			logger.info("头信息的手机号存在");
		}
		else{
			MyAssert.myAssertFalse("头信息的手机号字段值为空");
		}
		
		MyAssert.myAssertEquals(heardInfoFinancingAmountFM, basicTestDataBean.getTrialMoney());
		
		if(StringUtils.isNotBlank(heardInfoFrameNumber) && StringUtils.isNotBlank(heardInfoEntryTime) && StringUtils.isNotBlank(heardInfoOwnStore)){
			
			logger.info("头信息的车架号字段、入单时间字段、所属门店字段 值 存在");
		}
		
		else {
			MyAssert.myAssertFalse("头信息的车架号字段、入单时间字段、所属门店字段 值  不存在");
		}
		
		logger.info("对详情页的头信息字段检查结束。。。。。。。");
	}
	
}
