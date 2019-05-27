package cn.web.data_provider.car_loan;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import cn.web.common.constant.WebPlatformConstant;
import cn.web.util.ReadConfXml;


/**
 * 车贷展期申请数据来源
 * @author huangjun
 *
 */
public class CarLoanExtensionDataProvider {

	static String casePath = WebPlatformConstant.CAR_LOAN_EXTENSION_DATA;
	
	// 申请展期搜索
	@DataProvider
	public static Iterator<Object[]> applyExtensionSearch() {
		
		return ReadConfXml.getInstence().getData(casePath, "case1");
	}
	//申请展期提交
	@DataProvider
	public static Iterator<Object[]> applyExtensionSubmit() {
		
		return ReadConfXml.getInstence().getData(casePath, "case2");
	}
	
	
}
