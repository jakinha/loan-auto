package cn.web.service.car_loan_service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.alibaba.fastjson.JSONObject;

import cn.web.base.ReadJsonBase;
import cn.web.common.GetOrdeProcess;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.LocalEvaluatePage;
import cn.web.util.Constant;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;

/**
 * 本地评估功能
 * @author huangjun
 *
 */
public class LocalEvaluateService extends LocalEvaluatePage{

	private Logger logger = Logger.getLogger(LocalEvaluateService.class);
	
	private List<WebElement> findElements ;
	
	PropertyUtil propertyUtil = new PropertyUtil(Constant.PUBLIC_CONFIG);
	
	private String saveSucess = checkPointProperty.getproperValue("save_sucess");
	
	private String car300Price;
	
	private List<Integer> list = new ArrayList<>();
	
	private DecimalFormat decimalFormat = new DecimalFormat("#.0");
	
	public LocalEvaluateService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);
	}
	public LocalEvaluateService(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super(selenium, basicTestDataBean);
	}
	
	public LocalEvaluateService() {}


	/**本地评估队列界面处理**/
	public void carSysLocalEvaluatePendingQueue(){

		LoanFrontPubOperat.leftNavigation(selenium, "local_evaluate", "car_iframe_112111", testDataModel.getDescription());
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
				
				carSysLocalEvaluatePendingQueue();
			}
			
		}

		GetOrdeProcess.getOrder(selenium,getOrderBtn(),basicTestDataBean.getApplyNum(),getWaitQueueProcessNum(),getTaskPoolNum(),getTodayProcessNum_3());

		selenium.dclick(getLocalEvaluateProcessBut(), "本地评估待处理队列-审核处理");
		
		MyAssert.pageIsHaveExceptionHint(selenium.getDriver());

	}
	
	/**本地评估-车辆信息**/
	public void localEvaluateCarInfo(String isGetValuation){
		
		selenium.input(getMileage(), "2000", "里程数");

		selenium.jsClick(getPaintGradeSelect(), "漆面等级下拉");
		findElements = getSuperior();//优
		selenium.jsClick(findElements.get(2), "漆面等级");
	
		SeleniumUtil.sleep(0.5);
		findElements = getGood();//良
		selenium.jsClick(getInteriorGradeSelect(), "内饰等级下拉");
		selenium.jsClick(findElements.get(0), "内饰等级");
		
		SeleniumUtil.sleep(0.5);
		findElements = getMedium();//中
		selenium.click(getConditionDeviceSelect(), "装置等级下拉");
		selenium.jsClick(findElements.get(0), "装置等级");
		
		findElements = getCitySelect();
		selenium.jsClick(findElements.get(0), "估价城市控件");
		
		findElements = selenium.getMuiltWebElements("<//a[@title='北京']");
		selenium.jsClick(findElements.get(0), "估价省份");
		
		findElements = selenium.getMuiltWebElements("<//a[@title='北京']");
		selenium.jsClick(findElements.get(1), "估价城市");
	
		if(isGetValuation.equalsIgnoreCase("y")){
			
			car300Logic();
		}
		
		findElements = getNone();
		selenium.jsClick(getWaterCarSelect(), "泡水下拉");
		selenium.jsClick(findElements.get(1), "无泡水");
		
		selenium.jsClick(getfireCarSelect(), "火烧下拉");
		selenium.jsClick(findElements.get(2), "无火烧");
		
		selenium.jsClick(getMajorSelect(), "重大事故下拉");
		selenium.jsClick(findElements.get(3), "无重大事故");
		
		selenium.jsClick(getFrameGradeSelect(), "骨架等级下拉");
		selenium.jsClick(getFrameGrade(), "骨架等级");
		
		String testData = testDataModel.getTestData();
		selenium.input(getViolationSummary(), ReadJsonBase.readJson(testData, "$.car_info.violation_summary"), "违章");
		selenium.input(getSumFraction(), ReadJsonBase.readJson(testData, "$.car_info.sum_fraction"), "违章累计X分");
		selenium.input(getViolationSummaryMoney(), ReadJsonBase.readJson(testData, "$.car_info.violation_summary_money"), "违章元");
		selenium.input(getXTwelvePoints(), ReadJsonBase.readJson(testData, "$.car_info.x_twelve_points"), "违章12分");
		
		selenium.click(getEvaluateSummary(), "单击车辆评价");
		selenium.input(getEvaluateSummary(), "车辆还是很不错的", "车辆评价");

		selenium.jsClick(getCarSave(),saveSucess, "本地评估-车辆保存按钮");
	}
	
	/**车300估价**/
	public void car300Logic(){
		
		selenium.loopClickAssertion(getAppraisal(), "获取估价按钮",checkPointProperty.getproperValue("valuation_sucess"));

		selenium.click(getBlurDetermine(), "估价成功的确定按钮");
		
		String showCar300Price = selenium.getText(getAppraisalResult());
		car300Price = decimalFormat.format(Double.parseDouble(showCar300Price)*10000);
	}
	
	/**本地评估报告**/
	public void localEvaluateReport(){
		
		//评估信息
		selenium.jsClick(getLocalEvaluateEport(), "评估报告");
		selenium.jsClick(getAssessWaySelect(), "评估方式下拉");
		selenium.jsClick(getScene(), "评估方式-实地");
		
		selenium.input(getAssessMaster(), "评估大神", "评估师");
		selenium.jsClick(getAssessSave(),saveSucess, "评估方式-保存按钮");
		
		//车辆基本信息
		selenium.input(getGasDisplacement(), "2.0", "排气量");
	
		selenium.jsClick(getUseType(), "使用性质");
		selenium.jsClick(getNotServer(), "非营运");
		
		selenium.input(getNuclearCarriersNum(), ReadJsonBase.readJson(testDataModel.getTestData(), "$.evaluate_report.nuclear_carriers_num"), "核载人数");
	
		selenium.jsClick(getCarBaseDataSave(),saveSucess, "车辆基本信息保存按钮");

		//车辆检测
		selenium.jsClick(getWearEgreeSelect(), "磨损情况下拉");
		selectNoneOrHave("没有磨损");
		
		selenium.jsClick(getDamageDegreeSelect(), "破损情况下拉");
		selectNoneOrHave("没有破损");
		
		selenium.jsClick(getInteriorRenovationSelect(), "内饰翻新情况下拉");
		selectNoneOrHave("没有内饰翻新");
		
		selenium.jsClick(getMeterSettableSelect(), "调表可能下拉");
		selectNoneOrHave("没有调表可能");
		
		selenium.jsClick(getAirBagSelect(),"安全气囊下拉");
		selenium.jsClick(getAirBag(), "安全气囊-正常");

		String readJson = ReadJsonBase.readJson(testDataModel.getTestData(), "$.evaluate_report.actualRun");
		selenium.input(getActualRun(), readJson, "实际行驶公里");
		selenium.input(getChangingBox(), "BSX", "变速箱");
		selenium.input(getMotor(), "FDJ", "发动机");
		selenium.input(getCarCheckSummary(), "车辆各方面性能指标都还OK", "车辆检测汇总");
		
		selenium.jsClick(getCarDetectingSave(),saveSucess, "车辆检测保存按钮");
		
		String localEstimate = ReadJsonBase.readJson(testDataModel.getTestData(), "$.evaluate_report.custom_estimate");
		
		if(localEstimate.equalsIgnoreCase("false")){
			//此处的设计，因为直接进入评估报告界面，界面下方的车300估价会少1块钱，所有需要刷新下页面（手动操作是没问题的）
			selenium.switchPrentFrame("D");
			selenium.dclick(getEvaluationReviewLable(), "评估复核标签");
			String iframes= GetElemFilePropertyUtil.ifmpro.getproperValue("car_iframe_112111");
			selenium.switchFrameExec(iframes);
			selenium.jsClick(getLocalEvaluateEport(), "评估报告");
			
			//风险排查
			String getCar300RiskPrice = selenium.getText(getCar300RiskPrice());
			String car300RiskPrice =decimalFormat.format(Double.parseDouble(getCar300RiskPrice));
			MyAssert.myAssertEquals(car300RiskPrice, car300Price);
		
			selenium.input(getValuationNetive(), String.valueOf(Double.parseDouble(car300RiskPrice)-1000), "本地评估价格");
		}
		else {
			selenium.input(getValuationNetive(), localEstimate, "本地评估价格");
		}
		
	
		selenium.input(getRiskPoints(), "这辆车没有太大的风险,OK的", "风险点表现");
	
		selenium.jsClick(getRiskSave(), "风险排查 保存按钮");

	}
	
	/**本地评估提交操作**/
	public void localEvaluateSubmitOpera(){
		
		selenium.dclick(getEvaluateSubmit(), "提交");
		
		selenium.dclick(getConfirm(),GetElemFilePropertyUtil.checkPointPro.getproperValue("local_evaluate_oprea_sucess"), "确认按钮");
		
		LoanFrontPubOperat.alreadProcessQueue(selenium,basicTestDataBean.getApplyNum());
	}
	
	/**本地评估取消操作**/
	public void localEvaluateCanceOpera(){
		
		selenium.dclick(getEvaluateCance(), "取消");
		
		selenium.jsClick(getMainReasonSelect(), "本地评估取消订单的主原因下拉操作");
		
		selenium.jsClick(getMainReason(), "本地评估取消订单的主原因--产品因素");
		
		selenium.jsClick(getSubReasonSelect(), "本地评估取消订单的子原因下拉操作");
		
		selenium.jsClick(getSubReason(), "本地评估取消订单的主原因--费率高");
		
		selenium.jsClick(localCanceOrderConfim(),GetElemFilePropertyUtil.checkPointPro.getproperValue("local_evaluate_cance_sucess"), "取消框里的确认按钮");
		
		LoanFrontPubOperat.alreadProcessQueue(selenium,basicTestDataBean.getApplyNum());
	}
	
	/**本地评估提交失败操作**/
	public void localEvaluateSubmitOperaFailPrompt(String applyNum,String promptInfo){
		
		JSONObject jsonObject = JSONObject.parseObject(testDataModel.getCheckPoint());
		SeleniumUtil.sleep();

		selenium.dclick(getEvaluateSubmit(),jsonObject.getString(promptInfo), "提交");

		selenium.jsClick(getBackModify(), "返回修改");
		selenium.jsClick(getLocalEvaluateCarInfo(), "车辆信息");
		
	}
	
	/**处理下拉元素-有、无**/
	private void selectNoneOrHave(String loginfo){
		
		int i =0;
		
		WebDriver driver = selenium.getDriver();
		
		SeleniumUtil.sleep(1);
		
		for(;i<20;i++){

			if(list.contains(i)){
				logger.info("该位置的元素已经单击选择过了,位置在i:"+i);
				continue;
			}
			else{
				
				try {
					SeleniumUtil.sleep(0.3);
					driver.findElement(By.xpath("/html/body/div["+i+"]/div[1]/div[1]/ul/li[2]/span")).click();
					logger.info("成功单击到:"+loginfo+",位置在i:"+i);
					list.add(i);
					break;

					
				} catch (Exception e) {
					logger.info("没有单击到目标元素,继续努力找并单击!,位置在i:"+i);
				}
			}
		}
		if (i>=20) {
			MyAssert.myAssertFalse("没有找到要单击的元素：/html/body/div["+i+"]/div[1]/div[1]/ul/li[2]/span");
		}
	}
	
}
