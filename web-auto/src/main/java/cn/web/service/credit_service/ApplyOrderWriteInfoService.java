package cn.web.service.credit_service;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;

import cn.web.base.ReadJsonBase;
import cn.web.common.ImgUploadUtils;
import cn.web.common.SetValueTo;
import cn.web.common.TrustSystem;
import cn.web.common.WindowsUtils;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.common.constant.WebPlatformConstant;
import cn.web.dao.sql.SqlUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.Address;
import cn.web.util.KeyBoard;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;
/**
 * 新增贷款、申请详情 信息填写
 * @author huangjun
 *
 */

public class ApplyOrderWriteInfoService{
	
	static PropertyUtil iframe_pro = GetElemFilePropertyUtil.ifmpro;
	static PropertyUtil pro = GetElemFilePropertyUtil.creditSysElePro;
	
	//填写更多信息的界面需要跳转的iframe
	static private String iframe1=iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("StripFrame1")+","+iframe_pro.getproperValue("myiframe0");
	static private String iframe2=iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("StripFrame2")+","+iframe_pro.getproperValue("myiframe0");
	static private String iframe3=iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("StripFrame3")+","+iframe_pro.getproperValue("myiframe0");
	
	static Logger logger = Logger.getLogger(ApplyOrderWriteInfoService.class);
	
	/**
	 * 新增借款申请
	 * @param selenium
	 * @param testDataBean	测试数据<不变值>
	 * @param basic		基本测试数据<可变值>
	 */
	public static void addApplyLoan(SeleniumUtil selenium,TestDataModel testDataBean,BasicTestDataBean  basic){

		String json = testDataBean.getTestData();
		SqlUtil sql = new SqlUtil();
		//关联点存入redis缓存中<客户姓名、身份证号、手机号>
//		ConnectRedis.setKeyValue("clientName_key", basic.getClientName());
//		ConnectRedis.setKeyValue("idCard_key", basic.getIdCard());
//		ConnectRedis.setKeyValue("phoneNum_key", basic.getPhoneNum());
		
		//插入客户申请意向信息
		sql.customerApplicationIntentions(basic.getClientName(),basic.getPhoneNum(),basic.getIdCard());
		//解决提示华夏助手app提示框和证件号是黑名单的问题
		sql.hxHelperApp(basic.getClientName(),basic.getIdCard(),"0");
		writeBasicInfo(selenium,basic,testDataBean,json);
		
		search(selenium, pro.getproperValue("loan.name"), basic.getClientName(), "搜索贷款人姓名");
		SetValueTo.setValueToBean(selenium ,pro.getproperValue("applyNum"), basic);

	}
	
	/**
	 * 申请详情信息填写
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 */
	public static void applyFullInfoWrite(SeleniumUtil selenium,TestDataModel testDataBean,BasicTestDataBean  basic){
		
		applyProcess(selenium,basic,"wait.process.apply","待处理申请");
		String order = ApprovalProcessService.orderNoResource(basic);
		search(selenium, pro.getproperValue("apply_num_input"), order, "订单号搜索框");
		selenium.click(selenium.getWebElement(pro.getproperValue("applyNum")), "订单号："+order);
		selenium.switchPrentFrame("F");

		selenium.click(selenium.getWebElement(pro.getproperValue("ploanFullInfo")),"申请详情按钮");
		
		writeFullInfo(selenium ,testDataBean);
		
		processSubmit(selenium,testDataBean,basic.getApplyNum());
	}

	/**填写新增申请的基本信息**/
	public static void writeBasicInfo(SeleniumUtil selenium,BasicTestDataBean  basic,TestDataModel testDataBean,String json){
		
		applyProcess(selenium,basic,"wait.process.apply","待处理申请");
		
		selenium.switchPrentFrame("F");
		
		selenium.switchFrameExec(iframe_pro.getproperValue("SecondFrame_"));
		
		
		selenium.click(selenium.getWebElement(pro.getproperValue("addLoan")),"新增申请");
		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("accountName")), basic.getClientName(),"用户姓名");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("IDCard")), basic.getIdCard(),"身份证");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("phoneNum")),"手机号输入框");
		selenium.exceptionAlert("新增申请,填写手机号时");
		
		//输入手机号
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("phoneNum")), basic.getPhoneNum(),"手机号");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("cityselect")),"选择城市");
		switchWindowOpr(selenium,pro.getproperValue("city"),ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"),"城市");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("sellNameSelect")),"销售人员");
		switchWindowOpr(selenium,"<//input[@value='"+System.getProperty("sellPerson")+"']",ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"),"销售人员");

		selenium.click(selenium.getWebElement(pro.getproperValue("productStyle")),"产品类型下拉");
		selenium.click(selenium.getWebElement("<//select[@id='R0F13']/option[text()='"+System.getProperty("productType")+"']"),"产品类型");
	
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("ploanMoney")),ReadJsonBase.readJson(json, "$.data.loan_money"),"贷款金额");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("ploanDateSelect")),"借款期限下拉");
		selenium.click(selenium.getWebElement("<//select[@id='R0F17']/option[text()='"+System.getProperty("productData")+"个月']"),"借款期限");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("ploanUseSelect")),"借款用途选择");
		selenium.click(selenium.getWebElement(pro.getproperValue("ploanUse")),"借款用途");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("accountSourceSelect")),"客户来源下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("visit")),"主动拜访");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("engageStyleSelect")),"顾薪类型下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("engage")),"受薪族");
		//跳转到父级frame
		selenium.switchPrentFrame("F");

		p2pAndTrustProcess(selenium,basic.getClientName());
		
				
		
	}
	
	/**
	 * 信贷系统进件，对P2P产品和信托产品的处理
	 * @param selenium
	 * @param clientName	客户姓名
	 */
	private static void p2pAndTrustProcess(SeleniumUtil selenium,String clientName){
		
		Alert alert;
		
		if(System.getProperty("productType").contains(WebPlatformConstant.PRODUCT)){

			alert = selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("saveButton")),10,"保存按钮");
			String text = alert.getText();
			if(text.contains("信托计划截止时间为")){
				selenium.alertConfirm(alert,"-1", null);
			}
			else if(text.contains("没有匹配")){
				selenium.alertConfirm(alert,"-1", null);
				TrustSystem.createTrustPlan(selenium);
				p2pAndTrustProcess(selenium,clientName);
			}
			else{
				MyAssert.myAssertFalse("新增中粮借款保存时,弹出异常提示:"+text);
			}
		}
		else{
			selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存按钮");
			SeleniumUtil.sleep(3);
			selenium.exceptionAlert("新增借款保存时");
			
			selenium.switchPrentFrame("D");
			String iframes = iframe_pro.getproperValue("SecondFrame_")+","+iframe_pro.getproperValue("myiframe0");
			selenium.switchFrameExec(iframes);
			if(!selenium.getDriver().getPageSource().contains(clientName)){
				MyAssert.myAssertFalse("新增申请没有保存成功");
			}
		}
		logger.info("保存成功,新增申请借款基本信息填写完成！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	}
	
	/**填写更多借款的个人详细信息**/
	public static void writeFullInfo(SeleniumUtil selenium,TestDataModel testDataBean){

		String json = testDataBean.getTestData();
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));
			
		//个人基本信息
		writePersonInfo(selenium,json);
		//职业信息
		writeWorkInfo(selenium,json);
		//其他信息
		writeOtherInfo(selenium);
		
		if(System.getProperty("productType").contains("房")||System.getProperty("productType").contains("楼")){
			
			writeHouseInfo(selenium);
		}
	}
	
	
	/**填写个人基本信息**/
	public static void writePersonInfo(SeleniumUtil selenium,String json){
		
		if(selenium.alertIsDisplay()!=null){
			KeyBoard.enter();
		}
		
		selenium.switchFrameExec(iframe1);
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullPhoneTime")),ReadJsonBase.readJson(json, "$.data.phone_time"),"手机使用时长");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullEducation")),"学历选择下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullEducation_value")),"学历");

		selenium.click(selenium.getWebElement(pro.getproperValue("fullMarriage")),"婚姻状况下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullMarriage_value")),"婚姻状况");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullChild")),"子女数下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullChild_value")),"子女数");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullProvide")),"供养人数下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullProvide_value")),"供养人数");
		
		selenium.readOnlyRemove(selenium.getWebElement(pro.getproperValue("fullLiveSDate")), "readOnly");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullLiveSDate")), ReadJsonBase.readJson(json, "$.data.liveSDate"),"现居住起始时间");

		selenium.click(selenium.getWebElement(pro.getproperValue("fullLiveType")),"居住类型下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullLiveType_value")),"居住类型");

		selenium.readOnlyRemove(selenium.getWebElement(pro.getproperValue("fullFromCityDate")), "readOnly");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullFromCityDate")), ReadJsonBase.readJson(json, "$.data.fromCityDate"),"来本市时间");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullProvince")),"居住地省下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullProvince_value")),"居住地省");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullCity")),"居住地市下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullCity_value")),"居住地市下拉");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullDistrict")),"居住地区下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullDistrict_value")),"居住地区");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullAddressInfo")),  Address.getIndex(6),"居住地址");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullHProvince")),"户籍居住地省下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullHProvince_value")),"户籍居住地省");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullHCity")),"户籍居住地市下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullHCity_value")),"户籍居住地市");
		logger.info("户籍居住地市选择成功");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullHDistrict")),"户籍居住地区下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullHDistrict_value")),"户籍居住地区");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullHAddressInfo")),  Address.getIndex(5),"户籍居住地址");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullCRCitySame")),"户籍与居住地不在一个城市下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullCRCitySame_value")),"户籍与居住地不在一个城市");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullLocalCR")),"非本地户口下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullLocalCR_value")),"非本地户口");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullMonthlyIncome")), ReadJsonBase.readJson(json, "$.data.monthlyIncome"),"月收入");
	}
	
	/**填写职业信 息**/
	public static void writeWorkInfo(SeleniumUtil selenium,String json){
		
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec(iframe2);
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullUnit")), "名企"+RandomUtil.randomStr(3),"单位名称");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullUnitNature")),"单位性质下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullUnitNature_value")),"单位性质");
		
		selenium.readOnlyRemove(selenium.getWebElement(pro.getproperValue("fullSWorkDate")),"readOnly");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullSWorkDate")), ReadJsonBase.readJson(json, "$.data.fromCityDate"),"现单位入职时间");//入职时间即来本市的时间
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullDepartment")), "最屌部门"+RandomUtil.randomStr(2),"部门名称");

		selenium.click(selenium.getWebElement(pro.getproperValue("fullUProvince")),"居住地省下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullUProvince_value")),"居住地省");

		selenium.click(selenium.getWebElement(pro.getproperValue("fullUCity")),"居住地市下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullUCity_value")),"居住地市");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullUDistrict")),"居住地区下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullUDistrict_value")),"居住地区");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullUAddressInfo")), Address.getIndex(5),"居住地址");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullPostName")),"最屌职位"+RandomUtil.randomStr(2),"职位名称");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullCivilServants")),"否公务员下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullCivilServants_value")),"否公务员");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullWorkGrade")),"职务等级下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullWorkGrade_value")),"职务等级");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullUnitTel")), "021565","单位电话");

		selenium.click(selenium.getWebElement(pro.getproperValue("fullWage")),"工资发放形式下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullWage_value")),"工资发放形式");
	}
	
	/**填写联系人信息**/
	public static void writeOtherInfo(SeleniumUtil selenium){
		
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec(iframe3);
		
		//配偶
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullSpouseName")), "彤"+RandomUtil.randomStr(2),"配偶名称");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullSpouseTel")), "150"+RandomUtil.randomNum(8),"配偶手机号");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow")),"是否知悉借款下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow_value")),"是否知悉借款");
		//直系亲属
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullFamilyName")), "任"+RandomUtil.randomStr(2),"亲属名称");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullFamilyTel")), "150"+RandomUtil.randomNum(8),"亲属手机号");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow1")),"是否知悉借款下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow1_value")),"是否知悉借款");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullFelation1")),"选择亲属关系下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullFelation1_value")),"选择亲属关系");
		//同事
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullColleagueName")), "杨"+RandomUtil.randomStr(2),"同事名称");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullColleagueTel")), "150"+RandomUtil.randomNum(8),"同事手机号");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow2")),"是否知悉借款下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow2_value")),"是否知悉借款");
		//其他
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullOtherName")), "张"+RandomUtil.randomStr(2),"其他联系人名称");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("fullOtherTel")), "150"+RandomUtil.randomNum(8),"其他联系人手机号");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow3")),"是否知悉借款下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullKnow3_value")),"是否知悉借款");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullFelation2")),"选择其他联系人关系下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullFelation2_value")),"选择其他联系人关系");
		
	}
	
	/**申请 贷款  提交信息处理**/
	public static void processSubmit(SeleniumUtil selenium,TestDataModel testDataBean,String orderNo){
		Alert alert;
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec(iframe_pro.getproperValue("ObjectList"));
		selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("fullSubmit")),11,"提交按钮");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1", null);
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(3);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交动作选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1",null);
		SeleniumUtil.sleep(1.5);
		KeyBoard.enter();
		
	}
	
	//填写房产信息<申请填写信息界面 >
	public static void writeHouseInfo(SeleniumUtil selenium){
		
		selenium.switchPrentFrame("D");
		String iframe4=iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("StripFrame4");
		selenium.switchFrameExec(iframe4);
		selenium.dclick(selenium.getWebElement(pro.getproperValue("newAdd")),"新增");
		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));
		selenium.click(selenium.getWebElement(pro.getproperValue("estateSelect")),"购房类型下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("estateValue")),"购房类型");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("monthMonry")), "3000","月供房贷");

		selenium.readOnlyRemove(selenium.getWebElement(pro.getproperValue("byEstateData")), "readOnly");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("byEstateData")), "2014/09/11","购房日期");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("estatePrice")), "100000000","购房价格");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("estateArea")), "120","房屋面积");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("applyYear")), "30","贷款年限");

		selenium.click(selenium.getWebElement(pro.getproperValue("estateAddrePSelect")),"购房的省份下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("estateAddrePValue")),"购房的省份");

		selenium.click(selenium.getWebElement(pro.getproperValue("estateCitySelect")),"购房的市区下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("estateCityValue")),"购房的市区");

		selenium.click(selenium.getWebElement(pro.getproperValue("estateAddreSelect")),"购房的县城下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("estateAddreValue")),"购房的县城");

		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("estateAddreInfo")), "大中国","购房的具体地址");

		selenium.click(selenium.getWebElement(pro.getproperValue("estateIsMyNameSelect")),"购房是否再本人名下下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("estateIsMyNameValue")),"购房是否再本人名下");
		
	}

	
	/**
	 * 适用 信贷新增申请页面
	 * 跳转窗口后并进行操作，该页面有两个iframe(ObjectList和myiframe0)
	 * 目标页面操作完成后返回到主窗口中，并且跳转到iframe(SecondFrame_,myiframe0)
	 * @param selenium
	 * @param element	
	 * @param wintitle
	 * @param logInfo
	 */
	public static void switchWindowOpr(SeleniumUtil selenium , String element,String wintitle,String logInfo){
		
		String frames=iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("myiframe0");
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(wintitle);
		selenium.switchFrameExec(frames);//跳转到对应的frame
		selenium.click(selenium.getWebElement(element),logInfo);
		logger.info("单击操作元素-"+element+" 成功");
		
		selenium.switchPrentFrame("F");//跳转到父级frame
		selenium.click(selenium.getWebElement(pro.getproperValue("configbut")),logInfo);
		logger.info("单击操作元素【确定】成功");
		
		windowsUtils.getMainWindow();
		
		//回到主窗口界面后需要跳转iframe才能操作元素
		String mainFrames=iframe_pro.getproperValue("SecondFrame_")+","+iframe_pro.getproperValue("myiframe0");
		selenium.switchFrameExec(mainFrames);

	}

	/**
	 * 新增申请处理<待处理申请，已处理申请>
	 * @param selenium
	 * @param basic
	 * @param processStatus
	 * @param logInfo
	 */
	public static void applyProcess(SeleniumUtil selenium,BasicTestDataBean basic,String processStatus,String logInfo){
		
		selenium.moveToElement(pro.getproperValue("loanProcess"));
		logger.info("鼠标移动到[借款处理]成功");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("loanApply")),"借款申请");
		
		selenium.switchFrameExec(iframe_pro.getproperValue("FirstFrame"));

		selenium.click(selenium.getWebElement(pro.getproperValue(processStatus)),logInfo);
		
	}
	
	/**
	 * 申请借款搜索功能
	 * @param selenium
	 * @param searchElement	搜索框的元素
	 * @param searchKeyWord	搜索的关键字
	 * @param logInfo	
	 */
	public static void search(SeleniumUtil selenium,String searchElement,String searchKeyWord,String logInfo){
		
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec(iframe_pro.getproperValue("SecondFrame_"));
		
		selenium.click(selenium.getWebElement(pro.getproperValue("search")),"搜索按钮");
		selenium.cleanAndInput(selenium.getWebElement(searchElement),searchKeyWord,logInfo);
		selenium.click(selenium.getWebElement(pro.getproperValue("query")),"查询按钮");
		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));
	}
	
	/**回退补件提交**/
	public static void fallBackSubmit(SeleniumUtil selenium,BasicTestDataBean basic,TestDataModel testDataBean){
		
		selenium.moveToElement(pro.getproperValue("loanProcess"));
		logger.info("鼠标移动到[借款处理]成功");
		selenium.click(selenium.getWebElement(pro.getproperValue("fall.back")),"回退补件选项");
		String order = ApprovalProcessService.orderNoResource(basic);
		search(selenium,pro.getproperValue("apply_num_input"),order,"搜索申请编号");
		selenium.click(selenium.getWebElement("<//input[@value='" + order + "']"), "查询到订单号");
		
		selenium.switchPrentFrame("F");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit")),"提交按钮");
		SeleniumUtil.sleep(2);
		//处理订单复核的时候，影像上传失败的情况
		if(imgAgainUpload(selenium,testDataBean.getCheckPoint())){
			
			WindowsUtils windowsUtils = new WindowsUtils(selenium);
			windowsUtils.initWindows(2);
			windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
			selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交动作选择列表的提交");
			Alert alert = selenium.alertIsDisplay();
			selenium.alertConfirm(alert,"包含断言","确定提交");
		}
	}
	
	
	private static boolean imgAgainUpload(SeleniumUtil selenium,String checkPoint){
		
		Alert alert = selenium.getDriver().switchTo().alert();

		if(null != alert && !"".equals(alert.getText())||alert.getText().contains("影像平台审核失败，请补全影像资料")){
			
			System.out.println("提交失败:Alert的提示：["+alert.getText()+"] 重新对影像进行上传操作.");
			alert.accept();
			selenium.click(selenium.getWebElement(pro.getproperValue("viewDetails")), "审查详情 按钮");
			selenium.click(selenium.getWebElement(pro.getproperValue("process")), "处理 按钮");
			
			ImgUploadUtils.imgUpload(selenium,checkPoint);
			return false;
		}
		return true;
	}
	
}
