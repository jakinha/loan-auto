package cn.web.service.car_loan_extension_service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import cn.web.common.carloan.ExtensionPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_extension_page.ApplyExtensionPage;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;


/**
 * 申请展期
 * @author huangjun
 *
 */
public class ApplyExtensionService extends ApplyExtensionPage{
	
	

	public ApplyExtensionService(TestDataModel testDataModel, SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
			
		super(testDataModel, selenium, basicTestDataBean);
		
	}

	/**
	 * 搜索
	 * @param basicTestDataBean
	 */
	
	public void search(BasicTestDataBean basicTestDataBean){
		
		String financingSum = jsonObject.getString("financingSum");
		
		new ExtensionPubOperat().leftNavigation(selenium,carExtensionElementProperty.getproperValue("car_loan_extension_apply_menu"),"展期申请");
		
		if(testDataModel.getDescription().equals("根据订单号搜索")){
			
			selenium.input(businessNoSearchInput(), basicTestDataBean.getApplyNum(), "根据订单号搜索");
		}
		else if(testDataModel.getDescription().equals("根据姓名搜索")){
			
			selenium.input(clientNameSearchInput(), basicTestDataBean.getClientName(), "根据姓名搜索");
		}
		else if(testDataModel.getDescription().equals("根据身份证搜索")){
			
			selenium.input(clientIdcardSearchInput(), basicTestDataBean.getIdCard(), "根据身份证搜索");
		}
		
		else if(testDataModel.getDescription().equals("根据订单号搜索-不存在")){
			
			selenium.input(businessNoSearchInput(), jsonObject.getString("orderNum"), "根据不存在订单号搜索");
		}
		else if(testDataModel.getDescription().equals("根据姓名搜索-不存在")){
			
			selenium.input(clientNameSearchInput(),jsonObject.getString("client_name"), "根据不存在姓名搜索");
		}
		else if(testDataModel.getDescription().equals("根据身份证搜索-不存在")){
			
			selenium.input(clientIdcardSearchInput(), jsonObject.getString("id_card"), "根据不存身份证搜索");
		}
		
		else{
			
			MyAssert.myAssertFalse("用例数据的描述请从传入一个有效值");
		}
		
		selenium.jsClick(searchBtn(), "搜索按钮");
		
		SeleniumUtil.sleep();
		
		if(testDataModel.getDescription().contains("不存在")){
			
			if(selenium.getDriver().getPageSource().contains("你的队列中暂时没有可处理的单子...")){
				
				logger.info("根据 不存在  的搜索条件搜索结果是空记录,通过");
			}
			else{
				
				MyAssert.myAssertFalse("根据  不存在  的搜索条件搜索结果居然有记录,用例失败");
			}
		}
		else{
			
			Map<String, Object> fieldValue = new ConcurrentHashMap<String,Object>();
			
			Map<String, Object> exValue = new HashMap<String,Object>();
			
			fieldValue.put("clientName",selenium.getText(fieldClientName()).trim());
			fieldValue.put("productName",selenium.getText(fieldProductName()).trim());
			fieldValue.put("financingSum",selenium.getText(fieldFinancingSum()).trim());
			fieldValue.put("putoutDate",selenium.getText(fieldPutoutDate()).trim());
			
			exValue.put("clientName",basicTestDataBean.getClientName());
			exValue.put("productName",System.getProperty("productType"));
			exValue.put("financingSum",financingSum);
			
			for(Map.Entry<String, Object> mEntry:fieldValue.entrySet()){
				
				if(mEntry.getKey().equals("putoutDate")){
					
					if(StringUtils.isNotBlank(mEntry.getValue().toString())){
						logger.info(mEntry.getKey()+"-- 字段有值");
					}
					else{
						MyAssert.myAssertFalse(mEntry.getKey()+"-- 字段没有值");
	
					}
				}
				else if(mEntry.getValue().toString().equals(exValue.get(mEntry.getKey()).toString())){
					
					logger.info("断言成功,"+mEntry.getKey()+"-- 字段有值");
				}
				else{
					
					MyAssert.myAssertFalse("断言失败,"+mEntry.getKey()+"-- 字段值不对");
	
				}
			}
		}
	}
	
	/**申请展期**/
	public void applyExtension(){
		
		selenium.jsClick(applyExtensionBtn(), "申请展期按钮");
		
		selenium.input(extensionApplyRemark(), jsonObject.getString("applyExtensionRemark"),"申请展期备注");
		
		selenium.jsClick(determine(), "申请展期提交的确认按钮");
		
		selenium.jsClick(alreadyProcessLable(), "已处理队列按钮");
		
	}
	
	
}
