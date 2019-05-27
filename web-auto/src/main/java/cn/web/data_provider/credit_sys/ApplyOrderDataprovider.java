package cn.web.data_provider.credit_sys;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import cn.web.common.constant.WebPlatformConstant;
import cn.web.util.ReadConfXml;



public class ApplyOrderDataprovider {

	static String casePath = WebPlatformConstant.APPLY_ORDER_DATA;
	
	// 借款申请
	@DataProvider
	public static Iterator<Object[]> applyData() {
		return ReadConfXml.getInstence().getData(casePath, "case1");
	}
	
	// 复核
	@DataProvider
	public static Iterator<Object[]> reviewData() {
		return ReadConfXml.getInstence().getData(casePath, "case2");
	}
	
	// 初审
	@DataProvider
	public static Iterator<Object[]> firstTrialData() {
		return ReadConfXml.getInstence().getData(casePath, "case3");
	}
	
	// 终审
	@DataProvider
	public static Iterator<Object[]> finalTrialData() {
		return ReadConfXml.getInstence().getData(casePath, "case4");
	}
	
	// 电话预约
	@DataProvider
	public static Iterator<Object[]> telReserveData() {
		return ReadConfXml.getInstence().getData(casePath, "case5");
	}

	
	// 门店签约
	@DataProvider
	public static Iterator<Object[]> storeSignedData() {
		return ReadConfXml.getInstence().getData(casePath, "case6");
	}
	
	// 门店签约提交
	@DataProvider
	public static Iterator<Object[]> storeSignedSubmitData() {
		return ReadConfXml.getInstence().getData(casePath, "case7");
	}
	
	// 合同审核提交
	@DataProvider
	public static Iterator<Object[]> checkSubmitData() {
		return ReadConfXml.getInstence().getData(casePath, "case8");
	}

	@DataProvider
	public static Iterator<Object[]> applyFullInfoWriteData() {
		return ReadConfXml.getInstence().getData(casePath, "case9");
	}
	
	//初审回退补件
	@DataProvider
	public static Iterator<Object[]> firstTrialFallbacksData() {
		return ReadConfXml.getInstence().getData(casePath, "case10");
	}
	
	//初审回退补件提交
	@DataProvider
	public static Iterator<Object[]> firstTrialFallbacksSubmitData() {
		return ReadConfXml.getInstence().getData(casePath, "case11");
	}
	
	//取消新增申请
	@DataProvider
	public static Iterator<Object[]> orderApplyCanceData() {
		return ReadConfXml.getInstence().getData(casePath, "case12");
	}
	
	//信托计划匹配
	@DataProvider
	public static Iterator<Object[]> trustPlanMatchData() {
		return ReadConfXml.getInstence().getData(casePath, "case13");
	}
	
	
}
