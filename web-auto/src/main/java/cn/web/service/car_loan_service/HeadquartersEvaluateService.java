package cn.web.service.car_loan_service;

import java.text.DecimalFormat;

import cn.web.base.ReadJsonBase;
import cn.web.common.GetOrdeProcess;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.HeadquartersEvaluatePage;
import cn.web.util.Constant;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;



/**
 * 总部评估功能
 * @author huangjun
 *
 */
public class HeadquartersEvaluateService extends HeadquartersEvaluatePage{

	private DecimalFormat decimalFormat = new DecimalFormat("#.0");
	
	PropertyUtil propertyUtil = new PropertyUtil(Constant.PUBLIC_CONFIG);
	
	private String car300Price;
	
	public HeadquartersEvaluateService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	public HeadquartersEvaluateService( SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super(selenium, basicTestDataBean);

	}
	
	/**总部评估**/
	public void carSysHeadquartersEvaluate(String testData){
		
		String headquartersEvaluate = ReadJsonBase.readJson(testData, "$.data.current_node").trim();
				
		String headquartersEvaluateIframe = ReadJsonBase.readJson(testData, "$.data.current_iframe").trim();
		
		LoanFrontPubOperat.leftNavigation(selenium, headquartersEvaluate, headquartersEvaluateIframe, testDataModel.getDescription());
		
		SeleniumUtil.sleep();
		
		//判断获单状态开关是否关闭和开启获单成功与否
		if(!selenium.getDriver().getPageSource().contains("申请编号") && !selenium.getDriver().getPageSource().contains("开启")){
			
			selenium.click(getStartGetOrder(), "开启获单");
			selenium.click(getDetermine(), "确定");
			SeleniumUtil.sleep();
			
			MyAssert.pageIsHaveExceptionHint(selenium.getDriver());
			
			if(selenium.getDriver().getPageSource().contains("确定")){//判断开启获单是否失败
				
				selenium.click(getDetermine(), "确定");
				SeleniumUtil.sleep(3);
				
				carSysHeadquartersEvaluate(testData);
			}
		}
		
		GetOrdeProcess.getOrder(selenium,getOrderBtn(),basicTestDataBean.getApplyNum(),getWaitQueueProcessNum(),getTaskPoolNum(),getTodayProcessNum_4());

		selenium.dclick(getHeadquartersEvaluateProcessBut(), "总部评估待处理队列-审核处理");
		
		
		//车300获取估价
		selenium.jsClick(getAppraisal(),"总部评估-获取估价按钮");
		selenium.jsClick(getBlurDetermine(), "估价成功的确定按钮");
		String showCar300Price = selenium.getText(getAppraisalResult());
		car300Price =decimalFormat.format(Double.parseDouble(showCar300Price)*10000);
		
		selenium.jsClick(getCarSave(), "总部评估-车辆信息保存按钮");
		
		String getCar300RiskPrice = selenium.getText(getHeadquartersCar300RiskPrice());
		String car300RiskPrice = decimalFormat.format(Double.parseDouble(getCar300RiskPrice));
		MyAssert.myAssertEquals(car300RiskPrice, car300Price);
		
		selenium.input(getHeadquartersPrice(),String.valueOf(Double.parseDouble(car300RiskPrice)-2000), "总部评估价格");

		selenium.jsClick(getHeadquartersEvaluateSave(), "总部评估保存按钮");
	}
	
	/**总部评估提交操作**/
	public void headquartersEvaluateSubmitOpera(String applyNum){
		
		selenium.loopClickAssertion(getEvaluateSubmit(), "提交", checkPointProperty.getproperValue("definite"));
		
		selenium.loopClickAssertion(getBlurDetermine(), "确定按钮", checkPointProperty.getproperValue("check_submit_sucess"));
		
		LoanFrontPubOperat.alreadProcessQueue(selenium,applyNum);
	}

}
