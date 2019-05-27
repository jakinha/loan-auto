package cn.web.service.car_loan_service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cn.web.base.ReadJsonBase;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dao.sql.SqlUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.StoreEntryPage;
import cn.web.util.ElementModle;
import cn.web.util.IdCardGenerator;
import cn.web.util.KeyBoard;
import cn.web.util.MyAssert;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;

/**
 * 门店录入功能
 * @author huangjun
 *
 */
public class StoreEntryService extends StoreEntryPage{

	
	private String checkSavaSucess = checkPointProperty.getproperValue("save_sucess");
	
	private String checkSubmitSucess = checkPointProperty.getproperValue("check_submit_sucess");
	
	private String expeditedCheckSubmitSucess = checkPointProperty.getproperValue("expedited_check_submit_sucess");
	
	private String checkSearchResult = checkPointProperty.getproperValue("search_result");
	
	private String checkCarFrameAtWay = checkPointProperty.getproperValue("car_frame_number_way");
	
	private String invokerRejectInterfaceError = checkPointProperty.getproperValue("invoker_reject_interface_error");
	
	private String testData; 
	
	private List<WebElement> findElements;
	
	private String marryStatus = "已婚";

	public StoreEntryService(TestDataModel testDataModel,SeleniumUtil selenium,BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel,selenium,basicTestDataBean);
		
		testData = testDataModel.getTestData();
		
		
	}
	
	/**车贷系统门店录入**/
	public void carSysStoreEntry(){

		writeApplyInfo();

		writePersionInfo();

	}
	
	/**车贷 进行  门店录入申请信息和车辆信息,校验车架号在途件拦截**/
	public void checkCarFrameAtWay(){

		checkCarFrame(checkCarFrameAtWay);

	}
	
	/**车贷 进行  门店录入申请信息和车辆信息,校验车架号及不拦截**/
	public void checkCarFrameAtWayNotFilter(){
		
		checkCarFrame(checkSavaSucess);

	}
	
	private void checkCarFrame(String checkCarFrameAtWay){
		
		selenium.refreshPage();
		
		selenium.switchPrentFrame("D");
		
		writeApplyInfo();

		carSysPersionInfo();
		
		carSysCarInfo(checkCarFrameAtWay);
		
		SeleniumUtil.sleep();
	}
	
	/**填写申请信息**/
	private void writeApplyInfo(){
		
		comingWritePage();

		if(carSysApplyInfo() == false){
			
			selenium.switchPrentFrame("D");
			
			selenium.refreshPage();
			
			comingWritePage();
			
			if(carSysApplyInfo() == false){

				MyAssert.myAssertFalse("门店录入进行了第二次录入，申请信息保存还是不成功");
			}
		}
	}
	
	/**填写除申请信息之外的所有信息**/
	private void writePersionInfo(){
		
		carSysPersionInfo();
		
		carSysCarInfo(checkSavaSucess);
		
		carSysJobInfo();
		
		addHouseInfo();
		
		addContact();
		
		uploadCallInfo();
		
		handlingFee();
		
		addRemark();
		
	}
	
	/**车贷系统门店录入-只验证未婚条件**/
	public void carSysStoreEntryUnMarry(String marryStatus){

		this.marryStatus = marryStatus;
		
		comingWritePage();

		if(carSysApplyInfo() == false){
			
			selenium.switchPrentFrame("D");
			
			selenium.refreshPage();
			
			comingWritePage();
			
			if(carSysApplyInfo() == false){

				MyAssert.myAssertFalse("门店录入进行了第二次录入，申请信息保存还是不成功");
			}
		}

		carSysPersionInfo();
		
		carSysCarInfo(checkSavaSucess);
		
		carSysJobInfo();
		
		addContact();
		
		System.out.println("================准备更改婚姻状态为已婚=================");
		
		modifyContact();

	}
	
	/**将婚姻状态改成已婚修再改联系人信息**/
	public void modifyContact(){
		
		selenium.jsClick(getMarriageDropdown(), "婚姻状况下拉");
		
		selenium.jsClick(getMarriaged(), "已婚");
		
		selenium.loopClickAssertion(getPersionInfoSave(), "个人信息保存按钮", checkSavaSucess, 1.5);
		
		SeleniumUtil.sleep(1);
		
		addSpouseContact(1);
		
		SeleniumUtil.sleep(4);
		
		excuModifyContact();
	}
	
	/**修改联系人信息**/
	private void excuModifyContact(){
		
		String conplanyPhone = "17111111111";
		String directPhone = "17122222222";
		String goodBrothers ="好兄弟";
		String newSpouse = "新老婆";
		String newSpouseIdcard = new IdCardGenerator().generate();
		
		List<String> exceptList = new ArrayList<>();
		exceptList.add(goodBrothers);
		exceptList.add(newSpouse);
		exceptList.add(newSpouseIdcard);
		
		//添加公司信息
		selenium.jsClick(getComplanyAdd(), "联系人-公司新增+");
		selenium.input(getComplanyPhone(),"17100000000","新增+公司 手机号");
		selenium.jsClick(getComplanySave(), "公司信息的保存按钮");
		SeleniumUtil.sleep(1);
		//编辑刚刚新增的公司号码
		selenium.jsClick(getEditComplanyPhone(), "编辑按钮");
		selenium.cleanAndInput(getComplanyPhone(),conplanyPhone,"编辑公司 手机号");
		selenium.jsClick(getComplanySave(), "公司信息的保存按钮");
		SeleniumUtil.sleep(1);
		
		//左侧编辑直系亲属
		selenium.jsClick(getEditDirect(), "编辑直系亲属信息");
		selenium.jsClick(getDirectSelect(), "直系亲属关系下拉");
		List<WebElement> directRelations = getDirectRelations();
		selenium.jsClick(directRelations.get(1), "直系亲属关系-兄弟姐妹");
		selenium.cleanAndInput(getDirectName(),goodBrothers,"直系亲属关系-兄弟姐妹");
		SeleniumUtil.sleep(0.5);
		selenium.jsClick(getLeftEditDirectContactSave(), "直系亲属保存按钮");
		SeleniumUtil.sleep(1);
		//直系亲属右侧的编辑
		selenium.jsClick(getDirectRightEdit(), "右侧编辑直系亲属信息");
		selenium.cleanAndInput(getDirectRightEditPhone(),directPhone,"直系亲属手机号变更");
		SeleniumUtil.sleep(0.5);
		selenium.jsClick(getRightEditDirectContactSave(), "直系亲属保存按钮");
		SeleniumUtil.sleep(1);
		
		//编辑配偶信息
		selenium.jsClick(getEditSpouseInfo(), "左侧编辑配偶信息");
		selenium.cleanAndInput(getEditSpouseName(),newSpouse,"配偶姓名");
		selenium.cleanAndInput(getEditSpouseIdcard(),newSpouseIdcard,"配偶身份证");
		SeleniumUtil.sleep(0.5);
		selenium.jsClick(getEditSpouseSave(), "配偶保存按钮");
		
		String dbConplanyPhone = new SqlUtil().getAddModify(basicTestDataBean.getApplyNum(),"09",conplanyPhone);
		MyAssert.myAssertEquals(dbConplanyPhone, conplanyPhone);
		
		String dbDirectPhone = new SqlUtil().getAddModify(basicTestDataBean.getApplyNum(),"04",directPhone);
		MyAssert.myAssertEquals(dbDirectPhone, directPhone);
		
		List<String> list = new SqlUtil().getAddModify(basicTestDataBean.getApplyNum(),"新老婆");
		
		for(int i =0 ;i<exceptList.size();i++){
			
			if(!list.contains(exceptList.get(i))){
				MyAssert.myAssertFalse("数据库并没有更新页面所修改的联系人信息："+exceptList.get(i));
			}
		}
		
	}

	/**进入录单填写信息界面**/
	public void comingWritePage(){
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		selenium.jsClick(getLeftCarStoreEntryable(),"门店录入");
		
		selenium.switchFrameExec(stroeEntryIframe());
		
		selenium.jsClick(getRightAddLable(), "新增");
	}
	
	
	/**车贷系统-申请 信息**/
	public boolean carSysApplyInfo(){
		
		selenium.input(getClientName(), basicTestDataBean.getClientName(), "客户姓名");
		selenium.input(getClientIdcard(), basicTestDataBean.getIdCard(), "客户身份证");
		selenium.input(getClientMobile(), basicTestDataBean.getPhoneNum(), "客户手机号");
		
		selenium.jsClick(getProductTypeDropdown(), "产品类型下拉");
		selenium.jsClick(getProductType(),System.getProperty("productType"));//参数话产品

		selenium.input(getApplyMoney(),ReadJsonBase.readJson(testData, "$.apply_info.loan_money"), "借款金额");
		
		selenium.jsClick(getLoanUseDropdown(),"借款用途下拉");
		selenium.jsClick(getLoanUse(),"日常生活消费");

		selenium.jsClick(getCustomerClassifyDropdown(), "客户分类");
		String customer = ReadJsonBase.readJson(testData, "$.apply_info.customer_classify");
		selenium.jsClick(selenium.getWebElement("<//span[text()='"+customer+"']"), customer);
		
		selenium.jsClick(getCustomerSourceDropdown(), "客户来源");
		selenium.jsClick(getCustomerSource(), "主动拜访");
		
		selenium.jsClick(getChannelSourceDropdown(), "渠道来源");
		selenium.jsClick(getChannelSource(), "电销");

		selenium.loopClickAssertion(getCustomerBossDropdown(), "客户经理名称下拉", "全部",0.2);
		SeleniumUtil.sleep(2);

		String accountManager = System.getProperty("accountManager");
		
		if(StringUtils.isNotBlank(accountManager)){
			
			//供开发使用
			if(accountManager.equals("KF00000")){
				
				logger.info("走到了开发专用的选择的客户经理");
				
				selenium.jsClick(getThreeTeam(), "三团队");
					
				selenium.cleanAndInput(getAccountManagerSearchInput(),"童小芬", "输入客户经理名字");
				
				selectAccountManager("select_account_manager_txf");
				
				selenium.jsClick(getSelectAccountManagerTXF(), "三团队的客户经理-童小芬");

			}
		}
		
		//回归测试使用
		else{
			
			logger.info("走到了测试专用的选择的客户经理");
			
			selenium.jsClick(getOneTeam(), "一团队");
			
			selenium.cleanAndInput(getAccountManagerSearchInput(),ReadJsonBase.readJson(testData, "$.apply_info.account_manager"), "输入客户经理名字");
			
			selectAccountManager("select_account_manager");
			
			selenium.jsClick(getSelectAccountManager(), "选择一团队客户经理-皇皇皇");
		}

		for(int i =1;i<5; i++){
			
			if(i %2 == 0){
				selenium.jsClick(getApplySave(),"保存");
			}
			else{
				selenium.dclick(getApplySave(),"保存");
			}
			SeleniumUtil.sleep(2.5);
			
			if(selenium.getDriver().getPageSource().contains(checkSavaSucess)){
				
				logger.info("申请 信息 保 存 成 功");
				
				SeleniumUtil.sleep(1);
				
				basicTestDataBean.setApplyNum(selenium.getText(getOrderNo()));
				
				basicTestDataBean.setTrialMoney(ReadJsonBase.readJson(testData, "$.apply_info.loan_money"));
				
				return true;
			}
			if(i == 4){
				return false;
			}
		}
		return false;

	}
	
	private void selectAccountManager(String selectAccountManagerElementKey){
		
		do{
			selenium.jsClick(getTeamManegerSearchBtn(), "团队经理 搜索按钮");
		}
		while(false == selenium.displayWait(ElementModle.getEleModle(carElementProperty.getproperValue(selectAccountManagerElementKey))));
	}
	
	/**车贷系统-个人信息**/
	private void carSysPersionInfo(){
		
		//校验申请信息带入的客户的年龄和出生日
		String brithday = selenium.getText(getCustomerBrithday());
		String age = selenium.getText(getCustomerAge());
		int customerBrithday = Integer.parseInt(brithday.replace("-", ""));
		int customerAge = Integer.parseInt(age);
		int expectBrithday = Integer.parseInt(basicTestDataBean.getIdCard().substring(6, 14));
		int expectAge= (Integer.parseInt(new SimpleDateFormat("YYYY").format(new Date())))-Integer.parseInt(String.valueOf(expectBrithday).substring(0, 4));		
		MyAssert.myAssertEquals(customerBrithday, expectBrithday);
		MyAssert.myAssertEquals(customerAge, expectAge);
		
		selenium.jsClick(getHighestDegreeDropdown(), "最高学历下拉");
		selenium.jsClick(getHighestDegree(), "博士");
		
		selenium.jsClick(getMarriageDropdown(), "婚姻状况下拉");
		
		if(marryStatus.equals("已婚")){
			selenium.jsClick(getMarriaged(), "已婚");
			}
		else if(marryStatus.equals("未婚")){
			selenium.jsClick(getUnMarriage(), "未婚");
		}
		selenium.input(getChildrenNum(),ReadJsonBase.readJson(testData, "$.persion_info.child_no"), "子女数");
		selenium.input(getSupportNum(), ReadJsonBase.readJson(testData, "$.persion_info.support_no"), "供养人数");
		selenium.input(getMonthlyExpenses(),ReadJsonBase.readJson(testData, "$.persion_info.monthly_expenses"), "月家庭支出");
		
		List<WebElement> householdRegisterWebElements = getHouseholdRegisterDropdown();
		selenium.jsClick(householdRegisterWebElements.get(3),"户籍");
		
		selenium.jsClick(selenium.getWebElement("<//a[text()='安徽省']"),"安徽省");
		selenium.jsClick(selenium.getWebElement("<//a[text()='合肥市']"),"合肥市");
		selenium.jsClick(selenium.getWebElement("<//a[text()='市辖区']"),"市辖区");
		selenium.input(getHouseholdRegisterAddress(),"瑶海区", "户籍具体地址");
		
		selenium.jsClick(getHrLiveDropdown(), "户籍居住是否一样");
		
		String asSame = ReadJsonBase.readJson(testData, "$.persion_info.as_same");
		
		if(asSame.equals("是")){
			SeleniumUtil.sleep(0.3);
			selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue("isyes")+"li[1]"), "户籍居住是否一样["+asSame+"]");
		}
		else{
			System.out.println("暂时没有对户籍与现住地是否一致的情况做处理");
		}
		selenium.readOnlyRemove(getHouseDate(),"readOnly");
		selenium.input(getHouseDate(), "2017-01-01", "居住时间");
		selenium.readOnlyRemove(getToCityDate(),"readOnly");
		selenium.click(getToCityDate(), "单击来本市时间");
		selenium.input(getToCityDate(), "2017-01-01", "来本市时间");
		selenium.click(getCreditSesame(), "单击芝麻分信用");
		selenium.input(getCreditSesame(), ReadJsonBase.readJson(testData, "$.persion_info.credit_sesame"), "芝麻信用分");
		selenium.input(getAlipayNo(), ReadJsonBase.readJson(testData, "$.persion_info.alipayNo"), "支付宝帐号");
		selenium.input(getWechatNo(), ReadJsonBase.readJson(testData, "$.persion_info.wechatNo"), "微信号");
		selenium.dclick(getPersionInfoSave(),checkSavaSucess,"个人信息保存");

	}
	
	/**车辆信息**/
	private void carSysCarInfo(String checkSavaPrompt){

//		String carFrameNo = ReadJsonBase.readJson(testData, "$.car_info.car_rack_number");
//		new SqlUtil().carFrame(carFrameNo);//车架号需要重新处理
//		
//		selenium.input(getCarFrame(), carFrameNo,"车架号");
//		selenium.dclick(getMatch(), "匹配");
//		SeleniumUtil.sleep();

		selenium.input(getCarFrame(), basicTestDataBean.getCarFrameNumber(),"车架号");

		//选择车型按钮
		selenium.jsClick(selectCarTypeBtn(), "选择车型按钮");
		//选择品牌
		selenium.jsClick(selectBrand(), "选择品牌");
		//选择车系
		selenium.jsClick(selectCarSeries(), "选择车系");
		//选择车型
		selenium.jsClick(selectCarType(), "选择车型");
		
		selenium.readOnlyRemove(getFirstOnCardDate(),"readOnly");
		selenium.input(getFirstOnCardDate(), "2017-01-01", "上牌时间");
		KeyBoard.enter();
		
		selenium.input(getTransferNums(),ReadJsonBase.readJson(testData, "$.car_info.transfer_time"), "过户次数");
		SeleniumUtil.sleep(1);
		
		selenium.jsClick(getCarColorSelect(), "车身颜色下拉");
		selenium.jsClick(getCarColor(), "颜色");
		
		findElements = getCitySelect();
		selenium.jsClick(findElements.get(2), "车牌归属地");
		
		findElements = new ArrayList<>();
		findElements = getCity();
		selenium.click(findElements.get(3), "省份");
		findElements = new ArrayList<>();
		findElements = getArea();
		selenium.click(findElements.get(2), "城市");
		
		selenium.jsClick(getCarPlateNumber(), "单击车牌号");
		selenium.input(getCarPlateNumber(), ReadJsonBase.readJson(testData, "$.car_info.license_plate_num"), "车牌号");

		selenium.input(getFactoryTime(), "2016-05-07", "车出厂时间");
		KeyBoard.enter();
		
		selenium.input(getRegisTime(), "2017-05-07", "车登记时间");
		KeyBoard.enter();

		selenium.jsClick(getCarInsuranceSelect(), "车保险下拉");
		selenium.jsClick(getHave(), "有车保险");
		
		selenium.input(getYearCheckValidity(), "2020-01-01", "车年检期");
		KeyBoard.enter();

		selenium.input(getStrongInsuranceValidity(), "2020-01-01", "车强险期");
		KeyBoard.enter();

		selenium.input(getBusinessValidity(), "2020-01-01", "车商业险期");
		KeyBoard.enter();
		
		selenium.jsClick(getCarSave(),checkSavaPrompt,"车辆信息保存按钮");
		
		MyAssert.myAssertFalse(selenium.getDriver().getPageSource(), invokerRejectInterfaceError);
		
	}
	
	/**职业信息**/
	private void carSysJobInfo(){
		
		selenium.jsClick(getEmploymentType(), "雇佣类型下拉");
		selenium.jsClick(getEmployment(), "受薪族");
		
		selenium.input(getUnitName(), ReadJsonBase.readJson(testData, "$.job_info.unit_name"), "单位名称");
		
		selenium.jsClick(getUnitNatureSelect(), "单位性质下拉");
		selenium.jsClick(getUnitNature(), "民营");
		
		selenium.jsClick(getBusinessAttributeSelect(), "所属行业下拉");
		selenium.jsClick(getBusinessAttribute(), "高新技术");
		
		selenium.input(getDepartment(), ReadJsonBase.readJson(testData, "$.job_info.department"), "所属部门");
		
		selenium.input(getJobTitle(), ReadJsonBase.readJson(testData, "$.job_info.position"), "职位名称");
		
		selenium.jsClick(getPaySocialSecuritySelect(), "是否缴纳社保下拉");
		findElements = new ArrayList<>();
		findElements = getYes();
		selenium.jsClick(findElements.get(1), "缴纳社保");

		selenium.input(getEntryTime(), "2017-01-01", "入职时间");


		KeyBoard.enter();
		
		findElements = new ArrayList<>();
		findElements = getCitySelect();
		selenium.click(findElements.get(3), "工作地点");
		
		List<WebElement> jobLiveFindElements = new ArrayList<>();
		jobLiveFindElements = getChongQing();
		WebElement privinceElement = jobLiveFindElements.get(3);
		selenium.click(jobLiveFindElements.get(3), "重庆市");
		
		WebElement cityElement = privinceElement.findElement(By.xpath("./parent::dd/parent::dl/parent::div/parent::div/parent::div/div[2]/div/dl/dd/a"));
		selenium.click(cityElement, "市辖区");

		WebElement areaElement = cityElement.findElement(By.xpath("./parent::dd/parent::dl/parent::div/parent::div/parent::div/div[3]/div/dl/dd/a[1]"));
		selenium.click(areaElement, "万州区");
		
		selenium.input(getNowUnitAddress(),"徐汇区漕宝路1111号", "具体地址");
		
		selenium.jsClick(getSalaryGivingSelect(), "工资发放形式下拉");
		SeleniumUtil.sleep(1);
		selenium.jsClick(getSalaryGiving(), "银行代发");
		
		selenium.input(getBasicSalary(), ReadJsonBase.readJson(testData, "$.job_info.basic_salary"), "基本薪资");
		selenium.input(getSalaryData(), ReadJsonBase.readJson(testData, "$.job_info.salary_data"), "工资发放日");
		selenium.input(getOtherIncome(), ReadJsonBase.readJson(testData, "$.job_info.other_income"), "其他收入");
		selenium.jsClick(getWorkSave(),checkSavaSucess,"职业保存");

	}
	
	/**添加房产信息**/
	public void addHouseInfo(){
		
		SeleniumUtil.sleep(3);
		selenium.click(getAddHouse(), "添加房产按钮");
		
		selenium.jsClick(getHouseTypeSelect(), "房产类型下拉");
		selenium.jsClick(getCommercialMortgage(), "商业按揭");
		selenium.input(getPurchaseTime(), "2018-05-01", "房屋购买时间");

		KeyBoard.enter();
		selenium.input(getPrice(), "1000000", "房屋购买价格");
		selenium.input(getConstructionArea(), "120", "建筑面积");
		selenium.input(getMonthlySupply(), "5000", "月租/月供");
		selenium.input(getPropertyScale(), "100", "产权比例");
		
		selenium.jsClick(getPropertyAddressSelect(), "房产地址选择");
		selenium.jsClick(getProvince(), "安徽省");
		selenium.jsClick(getHeFeiCity(), "合肥市");
		selenium.jsClick(getHeFeiArea(), "市辖区");
		selenium.cleanAndInput(getPropertyAddress(), "瑶海区", "房产详详细地址");
		
		selenium.dclick(getHouseInfoSave(), checkSavaSucess, "房产信息保存按钮");

	}
	
	/**添加所有联系人**/
	public void addContact(){
		
		addComplanyInfo();
		SeleniumUtil.sleep(1);
		addRelativesContact();
		
		if(this.marryStatus.equals("已婚")){
			SeleniumUtil.sleep(1);
			addSpouseContact(1);
		}
		SeleniumUtil.sleep(1);
		addColleagueContact();
		SeleniumUtil.sleep(1);
		addOtherContact();
		SeleniumUtil.sleep(1);
		
	}
	
	/***添加公司信息**/
	public void addComplanyInfo(){
		selenium.jsClick(getAddComplany(), "公司  + 按钮");
		selenium.jsClick(getApplyTable(), "申请表选项");
		selenium.jsClick(getTelphoeType(), "电话类型");
		selenium.jsClick(getLandLine(), "座机");
		selenium.input(getLandLineNum(), "021-565656", "公司座机号");
		selenium.jsClick(getComplanySave(), "公司信息的保存按钮");
	}
	
	/**添加直系联系人**/
	public void addRelativesContact(){
		selenium.jsClick(getAddRelativesContact(), "添加直系联系人");
		
		selenium.jsClick(getRelationSelect(), "与直系亲属关系下拉");
		selenium.jsClick(getRelation(), "与直系亲属关系-父母");
		
		selenium.input(getRelativesContactName(), "父母大人", "直系亲属姓名");
		
		selenium.jsClick(getKnowLoan(), "知悉贷款");
		
		selenium.jsClick(getRelativesContactSourceSelect(), "直系亲属的信息-来源下拉");
		findElements = getApplyTables();
		selenium.jsClick(findElements.get(1), "申请表");
		
		selenium.jsClick(getRelativesContactTelphoeType(), "直系亲属电话类型下拉");
		findElements = new ArrayList<>();
		findElements = getRelativesContactTelphoe();
		selenium.dclick(findElements.get(1), "直系亲属所谓电话类型-手机");
		
		selenium.input(getContactPhoneNum(), "183"+RandomUtil.randomNum(8), "直系亲属的号码");
		
		selenium.dclick(getContactSave(), "直系亲属保存按钮");
	}
	
	/**添加配偶联系人**/
	public void addSpouseContact(int index){
		
		selenium.jsClick(getAddSpouse(), "添加配偶联系人");
		
		selenium.jsClick(getRelationSelect(), "与直系亲属关系下拉");
		selenium.jsClick(getSpouseRelation(), "与直系亲属关系-配偶");
		
		selenium.input(getRelativesContactName(), "老婆大人", "直系亲属姓名");
		
		selenium.input(getSpouseIdcard(), "530524198002119565", "配偶身份证");
		
		selenium.jsClick(getSpouseKnowLoan(), "知悉贷款");
		
		selenium.jsClick(getSpouseSourceSelect(), "直系亲属配偶的信息-来源下拉");
		findElements = getApplyTables();
		selenium.jsClick(findElements.get(index), "申请表");
		
		selenium.jsClick(getSpouseTelphoeType(), "直系亲属-配偶电话类型下拉");
		findElements = getRelativesContactTelphoe();
		selenium.jsClick(findElements.get(index), "直系亲属-配偶电话类型-手机");
		
		selenium.input(getSpousePhoneNum(), "183"+RandomUtil.randomNum(8), "直系亲属-配偶的号码");
	
		selenium.dclick(getContactSave(), "配偶信息保存按钮");
	}
	
	/**添加同事联系人**/
	public void addColleagueContact(){
		selenium.jsClick(getAddColleague(), "添加同事联系人");
		
		selenium.jsClick(getRelationSelect(), "与同事关系下拉");
		selenium.jsClick(getColleagueRelation(), "同事关系");
		
		selenium.input(getRelativesContactName(), "同事大哥", "同事姓名");
		selenium.input(getColleagueDepartment(), "技术部", "部门名称");
		selenium.input(getColleagueJob(), "测试", "职位名称");
		selenium.jsClick(getColleagueKnowLoan(), "是否知悉");
		
		selenium.jsClick(getColleagueSourceSelect(), "同事-来源下拉");
		findElements = getApplyTables();
		selenium.jsClick(findElements.get(1), "申请表");

		selenium.jsClick(getColleagueTelphoeType(), "同事电话类型下拉");
		findElements = getRelativesContactTelphoe();
		selenium.jsClick(findElements.get(1), "直系亲属-配偶电话类型-手机");
		
		selenium.input(getColleaguePhoneNo(), "183"+RandomUtil.randomNum(8), "同事手机号码");
		selenium.jsClick(getContactSave(), "同事信息保存按钮");
	}
	
	/**添加其他联系人**/
	public void addOtherContact(){
		selenium.jsClick(getAddOther(), "添加其他联系人");
		
		selenium.jsClick(getRelationSelect(), "其他联系人关系下拉");
		selenium.jsClick(getFirendRelation(), "朋友关系");
		
		selenium.input(getFirendName(), "好朋友", "朋友姓名");
		
		selenium.jsClick(getFirendKnowLoan(), "朋友是否知悉");
		
		selenium.jsClick(getFirendSourceSelect(), "其他-来源下拉");
		findElements = getApplyTables();
		selenium.jsClick(findElements.get(1), "申请表");
		
		selenium.jsClick(getFirendTelphoeType(), "电话类型下拉");
		findElements = getRelativesContactTelphoe();
		selenium.jsClick(findElements.get(1), "朋友电话类型-手机");
		
		selenium.input(getFirendPhoneNo(), "183"+RandomUtil.randomNum(8), "朋友手机号码");
		selenium.jsClick(getContactSave(), "朋友信息保存按钮");
	}
	
	/**上传通话详情**/
	public void uploadCallInfo(){

		selenium.click(getLoadCallInfo(), "上传文件");
		SeleniumUtil.sleep(2.5);
		KeyBoard.setAndctrlVClipboardData(System.getProperty("user.dir")+File.separator+"load-file"+File.separator+"callList.xlsx");
	}
	
	/**手续费**/
	public void handlingFee(){

		selenium.jsClick(getPoundageFeeRateSelect(), "手续费下拉");
		selenium.jsClick(getPoundageFeeRate(), "手续费0.5");
		selenium.jsClick(getPoundageFeeRateSave(), "手续费保存");
	}
	
	/**添加备注**/
	public void addRemark(){
		
		selenium.input(getRemark(), ReadJsonBase.readJson(testData, "$.remark.store_remark"), "门店录入备注");
		
		selenium.click(getAddRemark(), "添加备注按钮");
	}
	
	/**门店录入 提交、加急、取消  操作**/
	public void storeSubmitOpera(String applyNum,String opera){
		
		String confirmText = checkPointProperty.getproperValue("oprea_confirm");
		
		if(opera.equals("提交")){
			
			selenium.loopClickAssertion(getStoreSubmit(),opera, confirmText);
			
			stroyEtrySubmitConfirmBtn(1,checkSubmitSucess);
		}
		else if(opera.equals("加急")){
			
			selenium.loopClickAssertion(getExpedited(),opera, confirmText);

			stroyEtrySubmitConfirmBtn(2,expeditedCheckSubmitSucess);
		}
		
		else if(opera.equals("取消")){
			
			selenium.loopClickAssertion(getCance(),opera, confirmText);
			
			List<WebElement> muiltWebElements = getConfirmText();
			
			selenium.jsClick(getMainReasonSelect(), "主原因下拉");
			
			selenium.jsClick(getMainReason(), "主原因-产品原因");
			
			selenium.jsClick(getSubReasonSelect(), "子原因下拉");
			
			selenium.jsClick(getSubReason(), "子原因-费率高");

				
			SeleniumUtil.sleep(1);
			
			selenium.loopClickAssertion(muiltWebElements.get(0),"确认按钮", checkSubmitSucess,5);

		}
		
		selenium.input(getSearchOrderNo(), applyNum, "已处理搜索框-订单号");

		selenium.loopClickAssertion(selenium.getElement(carElementProperty.getproperValue("store_search_btn")),"搜索按钮",  checkSearchResult);
		
		if(opera.equals("加急")) {
			
			String anxiousText = selenium.getText(getAnxiousText()).trim();
			
			MyAssert.myAssertEquals(anxiousText, "急");
		}
	}
	
	private void stroyEtrySubmitConfirmBtn(int index,String checkPoint){
		
		SqlUtil sqlUtil = new SqlUtil();
		
		String orderStatusName = null;

		do{

			List<WebElement> muiltWebElements = getConfirmText();
			
			selenium.jsClick(muiltWebElements.get(index), "确认按钮");
			
			SeleniumUtil.sleep(2);
			
			if(selenium.getDriver().getPageSource().contains(checkPoint)){
				
				logger.info("页面出现了提交成功的提示语 了");
				break;
			}
			else{
				logger.info("页面没有出现提交成功的提示语 ,再去数据库查看订单的状态.......");
				SeleniumUtil.sleep();
				orderStatusName = sqlUtil.getOrderStatusName(basicTestDataBean.getApplyNum(),"order_status_name");
			}
		}
		while(!"评估复核".equals(orderStatusName));
		
		if("评估复核".equals(orderStatusName)){
			
			logger.info("数据库查询到该订单已经流转到下个节点了,订单提交成功了");

		}
	}
}
