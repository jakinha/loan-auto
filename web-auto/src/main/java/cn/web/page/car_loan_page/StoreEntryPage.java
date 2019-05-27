package cn.web.page.car_loan_page;

import java.util.List;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;

//门店录入
public class StoreEntryPage extends PageBase{

	protected StoreEntryPage(TestDataModel testDataModel,SeleniumUtil selenium, BasicTestDataBean basicTestDataBean) {
		super(testDataModel,selenium, basicTestDataBean);
	}
	
	//门店录入待处理队列-审核处理
	protected WebElement getStoreEntryProcessBut(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("store_entry_process").replace("订单号",orderNo));
	}
	
	//订单号元素
	protected WebElement getOrderNo(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("order_no"));
	}
	
	//门店录入跳转的iframe
	protected String stroeEntryIframe(){
		return  GetElemFilePropertyUtil.ifmpro.getproperValue("car_iframe_112101");
	}
	
	//左侧导航卡的【车贷】菜单下面的【门店录入】子菜单
	protected WebElement getLeftCarStoreEntryable(){
		return selenium.getWebElement(carElementProperty.getproperValue("store_entry"));
	}
	//右侧界面的添加按钮
	protected WebElement getRightAddLable(){
		return selenium.getWebElement(carElementProperty.getproperValue("add"));
	}
	
	//团队经理搜索按钮
	protected WebElement getTeamManegerSearchBtn(){
		
		return selenium.getWebElement(carElementProperty.getproperValue("distribution_search_btn"));
	}
/////////////////////////申	请	信	息/////////////////////////////////////////////////////
	//用户姓名
	protected WebElement getClientName(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_name"));
	}
	//用户身份证
	protected WebElement getClientIdcard(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_idcard"));
	}
	//用户手机号
	protected WebElement getClientMobile(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_mobile"));
	}
	//产品下拉
	protected WebElement getProductTypeDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("product_type_dropdown"));
	}
	protected WebElement getProductType(){
		return selenium.getWebElement("<//span[text()='"+System.getProperty("productType")+"']");
	}
	//借款金额
	protected WebElement getApplyMoney(){
		return selenium.getWebElement(carElementProperty.getproperValue("apply_money"));
	}
	//借款用途下拉
	protected WebElement getLoanUseDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("loan_use_dropdown"));
	}
	protected WebElement getLoanUse(){
		return selenium.getWebElement(carElementProperty.getproperValue("loan_use"));
	}
	//客户分类下拉
	protected WebElement getCustomerClassifyDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_classify_dropdown"));
	}
	//客户来源下拉
	protected WebElement getCustomerSourceDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_source_dropdown"));
	}
	protected WebElement getCustomerSource(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_source"));
	}
	//渠道来源下拉
	protected WebElement getChannelSourceDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("channel_source_dropdown"));
	}
	protected WebElement getChannelSource(){
		return selenium.getWebElement(carElementProperty.getproperValue("channel_source"));
	}
	//客户经理名称下拉
	protected WebElement getCustomerBossDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_boss_dropdown"));
	}
	//客户经理姓名搜索框
	protected WebElement getAccountManagerSearchInput(){
		return selenium.getWebElement(carElementProperty.getproperValue("account_manager_search_input"));
	}
	//选择搜索出来的客户经理(SIT 许少君)
	protected WebElement getSelectAccountManager(){
		return selenium.getWebElement(carElementProperty.getproperValue("select_account_manager"));
	}

	//选择搜索出来的客户经理(SIT 卢明俗)
	protected WebElement getSelectAccountManagerLMS(){
		return selenium.getWebElement(carElementProperty.getproperValue("select_account_manager_lms"));
	}
	//选择搜索出来的客户经理(UAT 童小芬)
	protected WebElement getSelectAccountManagerTXF(){
		return selenium.getWebElement(carElementProperty.getproperValue("select_account_manager_txf"));
	}
	
	//一团队
	protected WebElement getOneTeam(){
		return selenium.getWebElement(carElementProperty.getproperValue("one_team"));
	}
	//三团队
	protected WebElement getThreeTeam(){
		return selenium.getWebElement(carElementProperty.getproperValue("three_team"));
	}
	//申请信息保存按钮
	protected WebElement getApplySave(){
		return selenium.getWebElement(carElementProperty.getproperValue("apply_save"));
	}
/////////////////////////个	人	信	息/////////////////////////////////////////////////////	
	//客户出生日
	protected WebElement getCustomerBrithday(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_brithday"));
	}
	//客户年龄
	protected WebElement getCustomerAge(){
		return selenium.getWebElement(carElementProperty.getproperValue("customer_age"));
	}
	//最高学历下拉
	protected WebElement getHighestDegreeDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("highest_degree_dropdown"));
	}
	protected WebElement getHighestDegree(){
		return selenium.getWebElement(carElementProperty.getproperValue("highest_degree"));
	}
	//婚姻状态
	protected WebElement getMarriageDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("marriage_dropdown"));
	}
	//已婚
	protected WebElement getMarriaged(){
		return selenium.getWebElement(carElementProperty.getproperValue("married"));
	}
	//未婚
	protected WebElement getUnMarriage(){
		return selenium.getWebElement(carElementProperty.getproperValue("unmarried"));
	}
	//子女数
	protected WebElement getChildrenNum(){
		return selenium.getWebElement(carElementProperty.getproperValue("children_num"));
	}
	//供养人数
	protected WebElement getSupportNum(){
		return selenium.getWebElement(carElementProperty.getproperValue("support_num"));
	}
	//月家庭支出
	protected WebElement getMonthlyExpenses(){
		return selenium.getWebElement(carElementProperty.getproperValue("monthly_expenses"));
	}
	
	//户籍
	protected List<WebElement> getHouseholdRegisterDropdown(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("household_register_dropdown"));
	}
	//户籍具体地址
	protected WebElement getHouseholdRegisterAddress(){
		return selenium.getWebElement(carElementProperty.getproperValue("household_register_address"));
	}
	//户籍与居住地是否一致下拉
	protected WebElement getHrLiveDropdown(){
		return selenium.getWebElement(carElementProperty.getproperValue("hr_live_dropdown"));
	}
	
	//居住时间
	protected WebElement getHouseDate(){
		return selenium.getWebElement(carElementProperty.getproperValue("house_date"));
	}
	//来本市时间
	protected WebElement getToCityDate(){
		return selenium.getWebElement(carElementProperty.getproperValue("to_city_date"));
	}
	//芝麻分信用
	protected WebElement getCreditSesame(){
		return selenium.getWebElement(carElementProperty.getproperValue("credit_sesame"));
	}
	//支付宝账户
	protected WebElement getAlipayNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("alipayNo"));
	}
	//微信账户
	protected WebElement getWechatNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("wechatNo"));
	}
	//个人信息保存按钮
	protected WebElement getPersionInfoSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("persion_info_button"));
	}
////////////////////////车	辆	信	息//////////////////////////////////////////////////////////
	//车架号
	protected WebElement getCarFrame(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_frame"));
	}
	//匹配
	protected WebElement getMatch(){
		return selenium.getWebElement(carElementProperty.getproperValue("match"));
	}
	
	//选择车型按钮
	protected WebElement selectCarTypeBtn(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_type_btn"));
	}
	//选择品牌
	protected WebElement selectBrand(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_brands"));
	}
	//选择车系
	protected WebElement selectCarSeries(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_series"));
	}
	//选择车型
	protected WebElement selectCarType(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_type"));
	}
	//上牌时间
	protected WebElement getFirstOnCardDate(){
		return selenium.getWebElement(carElementProperty.getproperValue("first_onCard_date"));
	}
	//过户次数
	protected WebElement getTransferNums(){
		return selenium.getWebElement(carElementProperty.getproperValue("transfer_nums"));
	}
	//车颜色下拉
	protected WebElement getCarColorSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_color_select"));
	}
	//车颜色下拉
	protected WebElement getCarColor(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_color"));
	}
	//城市选择
	protected List<WebElement> getCitySelect(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("city_select"));
	}
	//省市
	protected List<WebElement> getCity(){
		return selenium.getMuiltWebElements("<//a[@title='北京市']");
	}
	//区
	protected List<WebElement> getArea(){
		return selenium.getMuiltWebElements("<//a[@title='市辖区']");
	}
	//车牌号
	protected WebElement getCarPlateNumber(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_plate_number"));
	}
	//出厂时间
	protected WebElement getFactoryTime(){
		return selenium.getWebElement(carElementProperty.getproperValue("factory_time"));
	}
	//登记时间
	protected WebElement getRegisTime(){
		return selenium.getWebElement(carElementProperty.getproperValue("regis_time"));
	}
	//车保险下拉
	protected WebElement getCarInsuranceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_insurance_select"));
	}
	//有车保险
	protected WebElement getHave(){
		return selenium.getWebElement(carElementProperty.getproperValue("have"));
	}
	//车年检期
	protected WebElement getYearCheckValidity(){
		return selenium.getWebElement(carElementProperty.getproperValue("year_check_validity"));
	}
	//车强险期
	protected WebElement getStrongInsuranceValidity(){
		return selenium.getWebElement(carElementProperty.getproperValue("strong_insurance_validity"));
	}
	//车商业险期
	protected WebElement getBusinessValidity(){
		return selenium.getWebElement(carElementProperty.getproperValue("business_validity"));
	}
	//车辆信息保存
	protected WebElement getCarSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("car_save"));
	}
/////////////////////////职	业	信	息/////////////////////////////////////////////////////	
	//雇佣类型下拉
	protected WebElement getEmploymentType(){
		return selenium.getWebElement(carElementProperty.getproperValue("employment_type"));
	}
	//受薪族
	protected WebElement getEmployment(){
		return selenium.getWebElement(carElementProperty.getproperValue("employment"));
	}
	//单位名称
	protected WebElement getUnitName(){
		return selenium.getWebElement(carElementProperty.getproperValue("unit_name"));
	}
	//单位性质下拉
	protected WebElement getUnitNatureSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("unit_nature_select"));
	}
	//民营公司
	protected WebElement getUnitNature(){
		return selenium.getWebElement(carElementProperty.getproperValue("unit_nature"));
	}
	//所属行业下拉
	protected WebElement getBusinessAttributeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("business_attribute_select"));
	}
	//高薪技术
	protected WebElement getBusinessAttribute(){
		return selenium.getWebElement(carElementProperty.getproperValue("business_attribute"));
	}
	//所属部门
	protected WebElement getDepartment(){
		return selenium.getWebElement(carElementProperty.getproperValue("department"));
	}
	//职位名称
	protected WebElement getJobTitle(){
		return selenium.getWebElement(carElementProperty.getproperValue("job_title"));
	}
	//是否缴纳社保下拉
	protected WebElement getPaySocialSecuritySelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("pay_social_security_select"));
	}
	//是
	protected List<WebElement> getYes(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("yes"));
	}
	//入职时间
	protected WebElement getEntryTime(){
		return selenium.getWebElement(carElementProperty.getproperValue("entry_time"));
	}
	//重庆市
	protected List<WebElement> getChongQing(){
		return selenium.getMuiltWebElements("<//a[@title='重庆市']");
	}
	//单位具体地址
	protected WebElement getNowUnitAddress(){
		return selenium.getWebElement(carElementProperty.getproperValue("now_unit_address"));
	}
	//工资发放方式下拉
	protected WebElement getSalaryGivingSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("salary_giving_select"));
	}
	//银行代发
	protected WebElement getSalaryGiving(){
		return selenium.getWebElement(carElementProperty.getproperValue("salary_giving"));
	}
	//基本薪资
	protected WebElement getBasicSalary(){
		return selenium.getWebElement(carElementProperty.getproperValue("basic_salary"));
	}
	//工资发放日
	protected WebElement getSalaryData(){
		return selenium.getWebElement(carElementProperty.getproperValue("salary_data"));
	}
	//其他收入
	protected WebElement getOtherIncome(){
		return selenium.getWebElement(carElementProperty.getproperValue("other_income"));
	}
	protected WebElement getWorkSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("work_save"));
	}
///////////////////////////房	产	信	息////////////////////////////////////////////////	
	//添加房产信息
	protected WebElement getAddHouse(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_house"));
	}
	//房产信息下拉
	protected WebElement getHouseTypeSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("house_type_select"));
	}
	//商业按揭
	protected WebElement getCommercialMortgage(){
		return selenium.getWebElement(carElementProperty.getproperValue("commercial_mortgage"));
	}
	//购买时间
	protected WebElement getPurchaseTime(){
		return selenium.getWebElement(carElementProperty.getproperValue("purchase_time"));
	}
	//房屋价格
	protected WebElement getPrice(){
		return selenium.getWebElement(carElementProperty.getproperValue("price"));
	}
	//建筑面积
	protected WebElement getConstructionArea(){
		return selenium.getWebElement(carElementProperty.getproperValue("construction_area"));
	}
	//月供
	protected WebElement getMonthlySupply(){
		return selenium.getWebElement(carElementProperty.getproperValue("monthly_supply"));
	}
	//产权比例
	protected WebElement getPropertyScale(){
		return selenium.getWebElement(carElementProperty.getproperValue("property_scale"));
	}
	//房产地址选择
	protected WebElement getPropertyAddressSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("property_address_select"));
	}
	//安徽省
	protected WebElement getProvince(){
		return selenium.getWebElement(carElementProperty.getproperValue("province"));
	}
	//合肥市
	protected WebElement getHeFeiCity(){
		return selenium.getWebElement(carElementProperty.getproperValue("city"));
	}
	//合肥市辖区
	protected WebElement getHeFeiArea(){
		return selenium.getWebElement(carElementProperty.getproperValue("area"));
	}
	//房产具体地址
	protected WebElement getPropertyAddress(){
		return selenium.getWebElement(carElementProperty.getproperValue("property_address"));
	}
	//房产信息保存
	protected WebElement getHouseInfoSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("house_info_save"));
	}
/////////////////////////	联	系	人	//////////////////////////////////////////////////////////////	
	//公司  + 按钮
	protected WebElement getAddComplany(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_complany"));
	}
	//申请表选项
	protected WebElement getApplyTable(){
		return selenium.getWebElement(carElementProperty.getproperValue("apply_table"));
	}
	//电话类型下拉
	protected WebElement getTelphoeType(){
		return selenium.getWebElement(carElementProperty.getproperValue("telphoe_type"));
	}
	//座机
	protected WebElement getLandLine(){
		return selenium.getWebElement(carElementProperty.getproperValue("land_line"));
	}
	//座机号
	protected WebElement getLandLineNum(){
		return selenium.getWebElement(carElementProperty.getproperValue("land_line_num"));
	}
	//公司信息的保存按钮
	protected WebElement getComplanySave(){
		return selenium.getWebElement(carElementProperty.getproperValue("complany_save"));
	}
	
	//添加直系联系人
	protected WebElement getAddRelativesContact(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_relatives_contact"));
	}
	//与直系亲属关系下拉
	protected WebElement getRelationSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("relation_select"));
	}
	//父母关系
	protected WebElement getRelation(){
		return selenium.getWebElement(carElementProperty.getproperValue("relation"));
	}
	//直系亲属姓名
	protected WebElement getRelativesContactName(){
		return selenium.getWebElement(carElementProperty.getproperValue("relatives_contact_name"));
	}
	//知悉贷款
	protected WebElement getKnowLoan(){
		return selenium.getWebElement(carElementProperty.getproperValue("know_loan"));
	}
	//直系亲属的信息-来源下拉
	protected WebElement getRelativesContactSourceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("relatives_contact_source_select"));
	}
	//申请表选项
	protected List<WebElement> getApplyTables(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("apply_table"));
	}
	//直系亲属电话类型下拉
	protected WebElement getRelativesContactTelphoeType(){
		return selenium.getWebElement(carElementProperty.getproperValue("relatives_contact_telphoe_type"));
	}
	//直系亲属电话类型-手机
	protected List<WebElement> getRelativesContactTelphoe(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("relatives_contact_phone"));
	}
	//直系亲属电话类型-手机号
	protected WebElement getContactPhoneNum(){
		return selenium.getWebElement(carElementProperty.getproperValue("contact_phone_num"));
	}
	//直系亲属电话类型-保存
	protected WebElement getContactSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("contact_save"));
	}
	
	//添加配偶
	protected WebElement getAddSpouse(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_spouse"));
	}
	//亲属关系-配偶
	protected WebElement getSpouseRelation(){
		return selenium.getWebElement(carElementProperty.getproperValue("spouse_relation"));
	}
	//配偶身份证
	protected WebElement getSpouseIdcard(){
		return selenium.getWebElement(carElementProperty.getproperValue("spouse_idcard"));
	}
	//配偶知悉贷款
	protected WebElement getSpouseKnowLoan(){
		return selenium.getWebElement(carElementProperty.getproperValue("spouse_know_loan"));
	}
	//配偶信息来源下拉
	protected WebElement getSpouseSourceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("spouse_source_select"));
	}
	//配偶手机类型
	protected WebElement getSpouseTelphoeType(){
		return selenium.getWebElement(carElementProperty.getproperValue("spouse_telphoe_type"));
	}
	//配偶手机号
	protected WebElement getSpousePhoneNum(){
		return selenium.getWebElement(carElementProperty.getproperValue("spouse_phone_num"));
	}
	
	//添加同事
	protected WebElement getAddColleague(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_colleague"));
	}
	//同事关系
	protected WebElement getColleagueRelation(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_relation"));
	}
	//同事部门名称
	protected WebElement getColleagueDepartment(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_department"));
	}
	//同事职位
	protected WebElement getColleagueJob(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_job"));
	}
	//同事知悉贷款
	protected WebElement getColleagueKnowLoan(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_know_loan"));
	}
	//同事来源下拉
	protected WebElement getColleagueSourceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_source_select"));
	}
	//同事电话类型
	protected WebElement getColleagueTelphoeType(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_telphoe_type"));
	}
	//同事电话号码
	protected WebElement getColleaguePhoneNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("colleague_phone_num"));
	}
	
	//其他联系人
	protected WebElement getAddOther(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_other"));
	}
	//朋友关系
	protected WebElement getFirendRelation(){
		return selenium.getWebElement(carElementProperty.getproperValue("firend_relation"));
	}
	//朋友姓名
	protected WebElement getFirendName(){
		return selenium.getWebElement(carElementProperty.getproperValue("firend_name"));
	}
	//朋友知悉贷款
	protected WebElement getFirendKnowLoan(){
		return selenium.getWebElement(carElementProperty.getproperValue("firend_know_loan"));
	}
	//朋友关系来源下拉
	protected WebElement getFirendSourceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("firend_source_select"));
	}
	//朋友电话类型
	protected WebElement getFirendTelphoeType(){
		return selenium.getWebElement(carElementProperty.getproperValue("firend_telphoe_type"));
	}
	//朋友手机号
	protected WebElement getFirendPhoneNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("firend_phone_num"));
	}
	
	//上传文件
	protected WebElement getLoadCallInfo(){
		return selenium.getWebElement(carElementProperty.getproperValue("load_call_info"));
	}
	
	//手续费下拉
	protected WebElement getPoundageFeeRateSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("poundage_fee_rate_select"));
	}
	//手续费
	protected WebElement getPoundageFeeRate(){
		return selenium.getWebElement(carElementProperty.getproperValue("poundage_fee_rate"));
	}
	//手续费保存
	protected WebElement getPoundageFeeRateSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("poundage_fee_rate_save"));
	}
	//备注
	protected WebElement getRemark(){
		return selenium.getWebElement(carElementProperty.getproperValue("remark"));
	}
	//添加备注
	protected WebElement getAddRemark(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_remark"));
	}
	//加急
	protected WebElement getExpedited(){
		return selenium.getWebElement(carElementProperty.getproperValue("expedited"));
	}
	//取消
	protected WebElement getCance(){
		return selenium.getWebElement(carElementProperty.getproperValue("cance"));
	}
	//确认
	protected List<WebElement>getConfirmText(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("confirm_text"));
	}
	//已处理搜索框-订单号
	protected WebElement getSearchOrderNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("search_order_no"));
	}
	//加急的字标
	protected WebElement getAnxiousText(){
		return selenium.getWebElement(carElementProperty.getproperValue("anxious_text"));
	}
	//联系人-公司新增+
	protected WebElement getComplanyAdd(){
		return selenium.getWebElement(carElementProperty.getproperValue("complany_add"));
	}
	//新增公司号码-手机号
	protected WebElement getComplanyPhone(){
		return selenium.getWebElement(carElementProperty.getproperValue("complany_phone"));
	}
	//编辑刚新增的公司号码-手机号
	protected WebElement getEditComplanyPhone(){
		return selenium.getWebElement(carElementProperty.getproperValue("edit_complany_phone"));
	}
	//直系亲属左侧编辑按钮
	protected WebElement getEditDirect(){
		return selenium.getWebElement(carElementProperty.getproperValue("edit_direct_info"));
	}
	//左侧的编辑直系亲属的关系下拉
	protected WebElement getDirectSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("direct_select"));
	}																 
	//左侧的编辑直系亲属的关系-兄弟姐妹
	protected List<WebElement> getDirectRelations(){
		return selenium.getMuiltWebElements(carElementProperty.getproperValue("direct_relation"));
	}
	//左侧编辑-直系亲属姓名
	protected WebElement getDirectName(){
		return selenium.getWebElement(carElementProperty.getproperValue("direct_name"));
	}
	//直系亲属左侧编辑-保存按钮
	protected WebElement getLeftEditDirectContactSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("left_edit_direct_contact_save"));
	}
	//直系亲属-右侧编辑按钮
	protected WebElement getDirectRightEdit(){
		return selenium.getWebElement(carElementProperty.getproperValue("direct_right_edit"));
	}
	//直系亲属-右侧编辑手机号
	protected WebElement getDirectRightEditPhone(){
		return selenium.getWebElement(carElementProperty.getproperValue("direct_right_edit_phone"));
	}
	//直系亲属右侧编辑-保存按钮
	protected WebElement getRightEditDirectContactSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("right_edit_direct_contact_save"));
	}
	//配偶联系人左侧编辑
	protected WebElement getEditSpouseInfo(){
		return selenium.getWebElement(carElementProperty.getproperValue("edit_spouse_info"));
	}
	//编辑配偶姓名
	protected WebElement getEditSpouseName(){
		return selenium.getWebElement(carElementProperty.getproperValue("edit_spouse_name"));
	}
	//编辑配偶身份证号
	protected WebElement getEditSpouseIdcard(){
		return selenium.getWebElement(carElementProperty.getproperValue("edit_spouse_idcard"));
	}
	//编辑配偶信息的保存按钮
	protected WebElement getEditSpouseSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("edit_spouse_save"));
	}

	

}
