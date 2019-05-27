package cn.web.service.car_loan_service;


import cn.web.util.*;
import com.alibaba.fastjson.JSONObject;

import cn.web.base.ReadJsonBase;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.StoreSigningPage;


public class StoreSigningService extends StoreSigningPage {

	private JSONObject jsonObject = null;

	public StoreSigningService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {

		super(testDataModel, selenium, basicTestDataBean);
		jsonObject = JSONObject.parseObject(testDataModel.getTestData());
	}

	/** 门店签约待处理队列 **/
	public void carSysStoreSigningPendingQueue() {
		
		LoanFrontPubOperat.leftNavigation(selenium, "store_signing", "car_iframe_112104", testDataModel.getDescription());

		selenium.input(getSearchOrderNo(), basicTestDataBean.getApplyNum(), "搜索订单号:"+basicTestDataBean.getApplyNum());

		selenium.jsClick(getSearchBtn(), "搜索按钮");

		SeleniumUtil.sleep();
		selenium.jsClick(getSigningProcess(), "签约处理");

	}

	/** 签约处理 **/
	public void carSysStoreSigningProcess() {
		
		SeleniumUtil.sleep(2.5);
		
		if (!selenium.getDriver().getPageSource().contains("汉")) {

			// 读卡
			JSONObject readCard = jsonObject.getJSONObject("read_card");
			readCard = setCustomerInfo(readCard);

			selenium.input(getName(), readCard.getString("name"), "客户姓名");

			selenium.input(getIdcard(), readCard.getString("idcard"), "客户身份证");

			String sex = selenium.getText(getSexText()).trim();
			selenium.jsClick(getSexSelect(), "性别下拉");
			selenium.jsClick(selenium.getWebElement(getSex().replace("性别", sex)), "性别");

			selenium.input(getEthnicGroup(), "汉", "民族");

			selenium.input(getBirthday(), readCard.getString("birthday"), "客户出生年月");
			KeyBoard.enter();

			selenium.input(getAddress(), "上海市 闵行区", "地址");
			selenium.input(getIssuingAgency(), "上海市", "签发机构");

			if(getReadCardSave() != null){
				selenium.dclick(getReadCardSave(),checkPointProperty.getproperValue("save_sucess") ,"读卡信息保存按钮");
			}
			else{
				selenium.dclick(getReadCardBackSave(),checkPointProperty.getproperValue("save_sucess"), "读卡信息保存按钮");
			}
		}
		//更换银行卡号
//		tieBankCard(30,"62070700000000" + RandomUtil.randomNum(4),"yes");
		tieBankCard(30, BankCardHead.ABC.getCode()+RandomUtil.randomNum(4),"yes");
		JSONObject jsonObjectCheckPoint = JSONObject.parseObject(testDataModel.getCheckPoint());
		String addBankSucess = jsonObjectCheckPoint.getString("add_bank_sucess");

		selenium.jsClick(getStoreSigningBankNoSubmit(),addBankSucess, "添加银行卡的  提交  按钮");
		
		if(getFinanciersSelect() != null){
			selenium.dclick(getFinanciersSelect(),"融资方下拉");
		}
		
		else{
			selenium.dclick(getFinanciersBackSelect(),"融资方下拉");
		}
		
		selenium.jsClick(getNaturalPerson(), "融资方-自然人");
		
		if(getFinanciersSave() != null){
			selenium.dclick(getFinanciersSave(),"融资方保存按钮");
		}
		
		else{
			selenium.dclick(getFinanciersBackSave(),"融资方保存按钮");
		}
	}

	/**
	 * 绑定银行卡
	 * @param count		
	 * 					判断添加银行卡的失败次数
	 * 
	 * @param bankCardNum
	 * 					银行卡号
	 * 
	 * @param isNeedAddBankOperator		
	 * 						是否需要进行单击【添加银行卡】按钮
	 * @return
	 */
	public String tieBankCard(int count,String bankCardNum,String isNeedAddBankOperator) {

		String bankCardNo = bankCardNum;
		String isNeedAddBankOpera = isNeedAddBankOperator;

		if (count < 1) {
			MyAssert.myAssertFalse("绑定了30个不同的招商银行卡号，都提示申请错误");
		}

		JSONObject addBankCard = jsonObject.getJSONObject("add_bank_card");
		String bank_no = addBankCard.getString("bank_no");
		
		if(null != isNeedAddBankOpera){
			selenium.jsClick(getAddBankCard(), "添加银行卡");
		}

		if ("-1".equals(bank_no)) {// 若测试数据里银行卡值是-1，则输入的银行卡是随机生成的有效银行卡,否则就使用默认的测试数据
			
			selenium.input(getBankCardNo(), bankCardNo, "银行卡号");
			
		} else {
			
			selenium.input(getBankCardNo(), bank_no, "银行卡号");
		}

		selenium.input(getBankPhoneNo(), "15099999999", "手机号");
		selenium.input(getOpenPrivince(), "上海市", "开户省");
		selenium.input(getOpenCity(), "上海市", "开户市");
		selenium.input(getOpenBranchBank(), "招商支行", "招商支行");

		selenium.jsClick(getNext(), "下一步按钮");
		selenium.jsClick(getMsgCode(), "获取短信验证码按钮");

		for (int i = 0; i < 10; i++) {

			SeleniumUtil.sleep();
			String pageSource = selenium.getDriver().getPageSource();

			if (pageSource.contains("提示")) {
				SeleniumUtil.sleep();
				selenium.jsClick(tieCardConfim(), "签约绑卡获取短信码后的弹出框确定按钮");
				break;
			}
		}

		if (getStoreSubmit().isEnabled() == false) {
			
			logger.info("关闭短信码界面--x图标,重新进行添加银行卡操作");
			
			selenium.click(getCloseIcon(), "关闭短信码界面--x图标");
			SeleniumUtil.sleep();
			
			bankCardNo = "622588027211" + RandomUtil.randomNum(4);
			tieBankCard(count - 1,bankCardNo,isNeedAddBankOpera);
			
		} else {
			
			SeleniumUtil.sleep(2);

			selenium.input(getMsgCodeInputBox(), "111111", "输入短信码");

		}
		return bankCardNo;
	}

	/** 修改放款信息-签约产品 **/
	public void updateSiginProduct() {

		selenium.jsClick(getUpdateLoanInfo(), "修改放款信息");

		selenium.click(getSiginProductSelect(), "签约产品的下拉");
		
		SeleniumUtil.sleep(0.8);
		selenium.click(getUpdateSiginProduct(), "签约产品-车辆抵押36期");

		SeleniumUtil.sleep(0.8);
		selenium.input(getUpdateSiginProductRemark(), "更换产品了哦", "更换产品");
		
		selenium.dclick(getUpdateSiginProductSave(), "保存成功", "保存按钮");

	}

	/** 修改放款信息-提额/降额 **/
	public void updateLoanAmount(TestDataModel testDataModel,String financingAmountStatus) {

		SeleniumUtil.sleep(3);
		
		selenium.jsClick(getUpdateLoanInfo(), "更改放款信息");
		
		int amount = Integer.parseInt(ReadJsonBase.readJson(testDataModel.getTestData(), "$.data.trialMoney"));

		if (financingAmountStatus.equals("提额")){

			selenium.cleanAndInput(getAmount(), String.valueOf(amount + 3000), "提高融资金额");
		}
		else if (financingAmountStatus.equals("降额")){

			selenium.cleanAndInput(getAmount(), String.valueOf(amount - 3000), "降低融资金额");
		}
		
		selenium.input(getUpdateSiginProductRemark(), "更改产品的备注信息", "修改了融资金额");
		SeleniumUtil.sleep(1);

		selenium.dclick(getUpdateSiginProductSave() ,"保存按钮");
		
		if(false == MyAssert.myAssertTrue(selenium.getDriver(), checkPointProperty.getproperValue("save_sucess"))){
			
			logger.info("重新再点击保存按钮！");
			selenium.jsClick(getUpdateSiginProductSave(), checkPointProperty.getproperValue("save_sucess"), "保存按钮");	
		}

		SeleniumUtil.sleep(2.5);
	}

	/** 门店签约提交 **/

	public void storeSigningSubmitOpera(SeleniumUtil selenium, String applyNum) {
		
		selenium.loopClickAssertion(getEvaluateSubmit(), "提交", checkPointProperty.getproperValue("oprea_confirm"));
		
		selenium.dclick(getConfirm(), "提交操作后的弹框里的确认按钮");
		
		LoanFrontPubOperat.processQueueSearch(selenium, applyNum, "already_search_order_no");

		selenium.loopClickAssertion(getSearchBtn() , "搜索按钮", checkPointProperty.getproperValue("search_result"));

	}

	private JSONObject setCustomerInfo(JSONObject data) {

		if (data.getString("name").equals("0") && data.getString("idcard").equals("0")
				&& data.getString("birthday").equals("0")) {

			String idcard = basicTestDataBean.getIdCard();
			String dateFormat = idcard.substring(6, 14);
			String year = dateFormat.substring(0, 4) + "-";
			String month = dateFormat.substring(4, 6) + "-";
			String day = dateFormat.substring(6);
			String birthday = year + month + day;

			data.put("name", basicTestDataBean.getClientName());
			data.put("idcard", idcard);
			data.put("birthday", birthday);

		}
		return data;
	}
}
