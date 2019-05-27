package cn.web.page.car_loan_page;

import java.util.List;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class LocalEvaluatePage extends PageBase{

	protected LocalEvaluatePage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	protected LocalEvaluatePage(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {

		super(selenium, basicTestDataBean);

	}
	
	protected LocalEvaluatePage() {

	}
	
	//本地评估 待处理审核按钮
	protected WebElement getLocalEvaluateProcessBut(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("local_evaluate_process").replace("订单号",orderNo));
	}
	
	//开启获单
	protected WebElement getStartGetOrder(){
		return selenium.getWebElement(carElementProperty.getproperValue("start_getOrder"));
	}
	//确定
	protected WebElement getDetermine(){
		return selenium.getWebElement(carElementProperty.getproperValue("determine"));
	}

	//里程数
	protected WebElement getMileage(){
		return selenium.getWebElement(carElementProperty.getproperValue("mileage"));
	}
	//漆面等级下拉
	protected WebElement getPaintGradeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("paint_grade_select"));
	}
	//优
	protected List<WebElement> getSuperior(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("superior"));
	}
	//良
	protected List<WebElement> getGood(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("good"));
	}
	//内饰等级下拉
	protected WebElement getInteriorGradeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("interior_grade_select"));
	}
	//中
	protected List<WebElement> getMedium(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("medium"));
	}
	//装置等级下拉
	protected WebElement getConditionDeviceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("condition_device_select"));
	}
	//城市
	protected List<WebElement> getCitySelect(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("city_select"));
	}
	//获取车300估价
	protected WebElement getAppraisal(){
		return selenium.getWebElement(carElementProperty.getproperValue("get_appraisal"));
	}
	//估价成功的确定按钮
/*	protected WebElement getBlurDetermine(){
		return selenium.getWebElement(carElementProperty.getproperValue("blur_determine"));
	}*/
	//估价结果值
	protected WebElement getAppraisalResult(){
		return selenium.getWebElement(carElementProperty.getproperValue("appraisal_result"));
	}
	//无
	protected List<WebElement> getNone(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("none"));
	}
	//泡水下拉
	protected WebElement getWaterCarSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("water_car_select"));
	}
	//火烧下拉
	protected WebElement getfireCarSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("fire_car_select"));
	}
	//重大事故下拉
	protected WebElement getMajorSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("major_select"));
	}
	//违章
	protected WebElement getViolationSummary(){
		return selenium.getWebElement(carElementProperty.getproperValue("violation_summary"));
	}
	//违章累计X分
	protected WebElement getSumFraction(){
		return selenium.getWebElement(carElementProperty.getproperValue("sum_fraction"));
	}
	//违章的钱
	protected WebElement getViolationSummaryMoney(){
		return selenium.getWebElement(carElementProperty.getproperValue("violation_summary_money"));
	}
	//X次违章12分
	protected WebElement getXTwelvePoints(){
		return selenium.getWebElement(carElementProperty.getproperValue("x_twelve_points"));
	}
	//车辆评价
	protected WebElement getEvaluateSummary(){
		return selenium.getWebElement(carElementProperty.getproperValue("evaluate_summary"));
	}
	//本地评估车辆信息保存按钮
	protected WebElement getCarSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_save"));
	}
	//评估报告
	protected WebElement getLocalEvaluateEport(){
		return selenium.getWebElement(carElementProperty.getproperValue("local_evaluate_eport"));
	}
	//车辆信息
	protected WebElement getLocalEvaluateCarInfo(){
		return selenium.getWebElement(carElementProperty.getproperValue("local_evaluate_carinfo"));
	}
	//评估方式下拉
	protected WebElement getAssessWaySelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("assess_way_select"));
	}
	//评估方式-实地
	protected WebElement getScene(){
		return selenium.getWebElement(carElementProperty.getproperValue("scene"));
	}
	//评估师
	protected WebElement getAssessMaster(){
		return selenium.getWebElement(carElementProperty.getproperValue("assess_master"));
	}
	//评估方式保存按钮
	protected WebElement getAssessSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("assess_save"));
	}
	//排气量
	protected WebElement getGasDisplacement(){
		return selenium.getWebElement(carElementProperty.getproperValue("gas_displacement"));
	}
	//使用性质下拉
	protected WebElement getUseType(){
		return selenium.getWebElement(carElementProperty.getproperValue("use_type"));
	}
	//非营运
	protected WebElement getNotServer(){
		return selenium.getWebElement(carElementProperty.getproperValue("not_server"));
	}
	//核载人数
	protected WebElement getNuclearCarriersNum(){
		return selenium.getWebElement(carElementProperty.getproperValue("nuclear_carriers_num"));
	}
	//车辆基本信息保存按钮
	protected WebElement getCarBaseDataSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_base_data_save"));
	}
	//磨损情况下拉
	protected WebElement getWearEgreeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("wear_egree_select"));
	}
	//破损情况下拉
	protected WebElement getDamageDegreeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("damage_degree_select"));
	}
	//内饰翻新情况下拉
	protected WebElement getInteriorRenovationSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("interior_renovation_select"));
	}
	//调表可能 下拉
	protected WebElement getMeterSettableSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("meter_settable_select"));
	}
	//安全气囊 下拉
	protected WebElement getAirBagSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("air_bag_select"));
	}
	//安全气囊-正常
	protected WebElement getAirBag(){
		return selenium.getWebElement(carElementProperty.getproperValue("air_bag"));
	}
	//行驶公里数
	protected WebElement getActualRun(){
		return selenium.getWebElement(carElementProperty.getproperValue("actualRun"));
	}
	//变速箱
	protected WebElement getChangingBox(){
		return selenium.getWebElement(carElementProperty.getproperValue("changing_box"));
	}
	//发动机
	protected WebElement getMotor(){
		return selenium.getWebElement(carElementProperty.getproperValue("motor"));
	}
	//车辆检测评估
	protected WebElement getCarCheckSummary(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_check_summary"));
	}
	//车辆检测保存按钮
	protected WebElement getCarDetectingSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_detecting_save"));
	}
	//评估复核标签
	protected WebElement getEvaluationReviewLable(){
		return selenium.getWebElement("<//*[@id='tab-menu']/div/a/span");
	}
	//车300估价后价格带入到下面的字段值
	protected WebElement getCar300RiskPrice(){
		return selenium.getWebElement(carElementProperty.getproperValue("car300_risk_price"));
	}
	//本地评估价格
	protected WebElement getValuationNetive(){
		return selenium.getWebElement(carElementProperty.getproperValue("valuation_netive"));
	}
	//风险点
	protected WebElement getRiskPoints(){
		return selenium.getWebElement(carElementProperty.getproperValue("risk_points"));
	}
	//风险排查保存按钮
	protected WebElement getRiskSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_risk_save"));
	}
	//确认按钮
	protected WebElement getConfirm(){
		return selenium.getWebElement(carElementProperty.getproperValue("confirm"));
	}
	
	//取消操作框里的确认按钮
	protected WebElement localCanceOrderConfim(){
		return selenium.getWebElement(carElementProperty.getproperValue("local_cance_order_confim"));
	}
	
	//骨架等级下拉
	protected WebElement getFrameGradeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("frame_grade_select"));
	}
	//骨架等级
	protected WebElement getFrameGrade(){
		return selenium.getWebElement(carElementProperty.getproperValue("frame_grade"));
	}
	
	//返回修改
	protected WebElement getBackModify(){
		return selenium.getWebElement(carElementProperty.getproperValue("back_modify"));
	}
	
}
