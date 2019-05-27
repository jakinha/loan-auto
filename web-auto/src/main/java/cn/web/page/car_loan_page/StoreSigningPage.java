package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class StoreSigningPage extends PageBase{

	protected StoreSigningPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	
	//门店签约
	protected WebElement getStoreSigning(){
		return selenium.getWebElement(carElementProperty.getproperValue("store_signing"));
	}
	//待处理搜索订单号框
	protected WebElement getSearchOrderNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("search_order_no"));
	}

	//签约处理
	protected WebElement getSigningProcess(){
		return selenium.getWebElement(carElementProperty.getproperValue("signing_process"));
	}
	//客户姓名
	protected WebElement getName(){
		return selenium.getWebElement(carElementProperty.getproperValue("name"));
	}
	//客户身份证
	protected WebElement getIdcard(){
		return selenium.getWebElement(carElementProperty.getproperValue("idcard"));
	}
	//性别
	protected WebElement getSexText(){
		return selenium.getWebElement(carElementProperty.getproperValue("sex_text"));
	}
	//性别下拉
	protected WebElement getSexSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("sex_select"));
	}
	protected String getSex(){
		return carElementProperty.getproperValue("sex");
	}
	//民族
	protected WebElement getEthnicGroup(){
		return selenium.getWebElement(carElementProperty.getproperValue("ethnic_group"));
	}
	//出生年月
	protected WebElement getBirthday(){
		return selenium.getWebElement(carElementProperty.getproperValue("birthday"));
	}
	//地址
	protected WebElement getAddress(){
		return selenium.getWebElement(carElementProperty.getproperValue("address"));
	}
	//签发机构
	protected WebElement getIssuingAgency(){
		return selenium.getWebElement(carElementProperty.getproperValue("issuing_agency"));
	}
	//读卡保存按钮
	protected WebElement getReadCardSave(){
		return selenium.getElement(carElementProperty.getproperValue("read_card_save"));
	}
	//读卡保存按钮（资料审核回退到门店签约时）
	protected WebElement getReadCardBackSave(){
		return selenium.getElement(carElementProperty.getproperValue("read_card_back_save"));
	}
	
	//添加银行卡信息
	protected WebElement getAddBankCard(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_bank_card"));
	}
	//银行卡号
	protected WebElement getBankCardNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("bank_card_no"));
	}
	//预留银行手机号
	protected WebElement getBankPhoneNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("bank_phone_no"));
	}
	//银行开户省份
	protected WebElement getOpenPrivince(){
		return selenium.getWebElement(carElementProperty.getproperValue("open_privince"));
	}
	//银行开户城市
	protected WebElement getOpenCity(){
		return selenium.getWebElement(carElementProperty.getproperValue("open_city"));
	}
	//开户支行
	protected WebElement getOpenBranchBank(){
		return selenium.getWebElement(carElementProperty.getproperValue("open_branch_bank"));
	}
	//开户银行
	protected WebElement getOpenBank(){
		return selenium.getWebElement(carElementProperty.getproperValue("open_bank"));
	}
	//添加银行卡的  下一步  按钮
	protected WebElement getNext(){
		return selenium.getWebElement(carElementProperty.getproperValue("store_next"));
	}
	//获取短信验证码
	protected WebElement getMsgCode(){
		return selenium.getWebElement(carElementProperty.getproperValue("msg_code"));
	}
	//短信码输入框
	protected WebElement getMsgCodeInputBox(){
		return selenium.getWebElement(carElementProperty.getproperValue("msg_code_input"));
	}
	//关闭短信码界面  x图标
	protected WebElement getCloseIcon(){
		return selenium.getWebElement(carElementProperty.getproperValue("close_icon"));
	}
	//添加银行卡的  提交  按钮
	protected WebElement getStoreSigningBankNoSubmit(){
		return selenium.getWebElement(carElementProperty.getproperValue("store_signing_bank_no_submit"));
	}

	//融资方下拉
	protected WebElement getFinanciersSelect(){
		return selenium.getElement(carElementProperty.getproperValue("financiers_select"));
	}
	//融资方下拉(回退情况)
	protected WebElement getFinanciersBackSelect(){
		return selenium.getElement(carElementProperty.getproperValue("financiers_back_select"));
	}
	
	//自然人
	protected WebElement getNaturalPerson(){
		return selenium.getWebElement(carElementProperty.getproperValue("natural_person"));
	}
	//融资方保存按钮
	protected WebElement getFinanciersSave(){
		return selenium.getElement(carElementProperty.getproperValue("financiers_save"));
	}
	//融资方保存按钮(回退情况)
	protected WebElement getFinanciersBackSave(){
		return selenium.getElement(carElementProperty.getproperValue("financiers_back_save"));
	}
	
	//修改放款信息
	protected WebElement getUpdateLoanInfo(){
		return selenium.getWebElement(carElementProperty.getproperValue("update_loan_info"));
	}
	//签约产品下拉
	protected WebElement getSiginProductSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("sigin_product_select"));
	}
	//选择新的产品
	protected WebElement getUpdateSiginProduct(){
		return selenium.getWebElement(carElementProperty.getproperValue("update_sigin_product"));
	}
	//备注
	protected WebElement getUpdateSiginProductRemark(){
		return selenium.getWebElement(carElementProperty.getproperValue("update_sigin_product_remark"));
	}
	//保存
	protected WebElement getUpdateSiginProductSave(){
		return selenium.getWebElement(carElementProperty.getproperValue("update_sigin_product_save"));
	}
	//放款金额
	protected WebElement getAmount(){
		return selenium.getWebElement(carElementProperty.getproperValue("amount"));
	}
	//确认
	protected WebElement getConfirm(){
		return selenium.getWebElement(carElementProperty.getproperValue("confirm"));
	}
	//单击获取短信码后的弹出框确定按钮
	protected WebElement tieCardConfim(){
		return selenium.getWebElement(carElementProperty.getproperValue("tie_card_confim"));
	}
	
	
}
