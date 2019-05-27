package cn.web.data_provider.car_loan;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import cn.web.common.constant.WebPlatformConstant;
import cn.web.util.ReadConfXml;



public class CarLoanFrontDataProvider {

	static String casePath = WebPlatformConstant.CAR_LOAN_FRONT_DATA;

	// 门店录入
	@DataProvider
	public static Iterator<Object[]> storeEntryData() {
		return ReadConfXml.getInstence().getData(casePath, "case1");
	}

	//本地评估
	@DataProvider
	public static Iterator<Object[]> localEvaluateData(){
		return ReadConfXml.getInstence().getData(casePath, "case2");
	}
	
	@DataProvider
	public static Iterator<Object[]> car300LogicData(){
		return ReadConfXml.getInstence().getData(casePath, "case2-1");
	}
	
	
	//总部评估
	@DataProvider
	public static Iterator<Object[]> headquartersEvaluateData(){
		return ReadConfXml.getInstence().getData(casePath, "case3");
	}
	
	//初审
	@DataProvider
	public static Iterator<Object[]> firstTrialData(){
		return ReadConfXml.getInstence().getData(casePath, "case4");
	}
	
	//初审拒绝
	@DataProvider
	public static Iterator<Object[]> firstTrialRejectData(){
		return ReadConfXml.getInstence().getData(casePath, "case4-1");
	}
	
	// 初审回退总部评估
	@DataProvider
	public static Iterator<Object[]> firstTrialBackData(){
		return ReadConfXml.getInstence().getData(casePath, "case4-2");
	}
	
	// 初审实地调查
	@DataProvider
	public static Iterator<Object[]> firstTrialFieldSurveyData(){
		return ReadConfXml.getInstence().getData(casePath, "case4-3");
	}
	
	//终审
	@DataProvider
	public static Iterator<Object[]> finallyTrialData(){
		return ReadConfXml.getInstence().getData(casePath, "case5");
	}
	
	// 终审回退初审
	@DataProvider
	public static Iterator<Object[]> finallyTrialBackData(){
		return ReadConfXml.getInstence().getData(casePath, "case5-1");
	}
	
	//终审实地调查
	@DataProvider
	public static Iterator<Object[]> finalTrialFieldSurveyData(){
		return ReadConfXml.getInstence().getData(casePath, "case5-2");
	}
	
	//安装GPS
	@DataProvider
	public static Iterator<Object[]> installGPSData(){
		return ReadConfXml.getInstence().getData(casePath, "case6");
	}
	
	//门店签约
	@DataProvider
	public static Iterator<Object[]> storeSigningData(){
		return ReadConfXml.getInstence().getData(casePath, "case7");
	}
	
	//资料审核
	@DataProvider
	public static Iterator<Object[]> dataReviewData(){
		return ReadConfXml.getInstence().getData(casePath, "case8");
	}
	
	//合同审核
	@DataProvider
	public static Iterator<Object[]> contractReviewData(){
		return ReadConfXml.getInstence().getData(casePath, "case9");
	}

	//更换银行卡
	@DataProvider
	public static Iterator<Object[]> changBankCardData(){
		return ReadConfXml.getInstence().getData(casePath, "case10");
	}
	
	//流程监控、综合查询 合同状态查询
	@DataProvider
	public static Iterator<Object[]> integratedQueryData(){
		return ReadConfXml.getInstence().getData(casePath, "case11");
	}

	//本地评估订单取消
	@DataProvider
	public static Iterator<Object[]> localEvaluateCanceData(){
		return ReadConfXml.getInstence().getData(casePath, "case12");
	}
	
	@DataProvider
	public static Iterator<Object[]> reviewBackStroeEntryData(){
		return ReadConfXml.getInstence().getData(casePath, "case13");
	}
	
	
}
