package cn.web.service.car_loan_service;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.ChangBankCardPage;
import cn.web.util.Constant;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;

public class ChangBankCardService extends ChangBankCardPage{
	
	private PropertyUtil carLoanLoginInfo = new PropertyUtil(Constant.PUBLIC_CONFIG);

	public ChangBankCardService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	/**
	 * 更换银行卡
	 * @param orderNo 订单号
	 */
	public void changBankCardService(String orderNo){
		
		selenium.refreshPage();
		
		selenium.click(selenium.getElement(LoanFrontPubOperat.carLoanFrontMenuLable), "车贷贷前");
		
		selenium.jsClick(getChangBankCardMenu(),"更换银行卡菜单");
		
		LoanFrontPubOperat.processMuiltISameIframe(selenium,"更换银行卡", "更换银行卡菜单待处理");
		
		selenium.cleanAndInput(getSearchOrderInput(),orderNo, "订单号搜索框："+orderNo);
		
		selenium.loopClickAssertion(getSearchBtn(),"搜索按钮",orderNo);
		
		selenium.jsClick(getChangBankCardBtn(), "更换银行卡按钮");
		
		selenium.jsClick(getChang(), "更换");
		
		StoreSigningService storeSigningService = new StoreSigningService(testDataModel, selenium, basicTestDataBean);
		
		
		String newBankCardNo = storeSigningService.tieBankCard(30,"622588027211" + RandomUtil.randomNum(4),null);
		
		selenium.jsClick(getChangBankNoSubmit(), "添加银行卡的  提交  按钮");
		SeleniumUtil.sleep();
		selenium.jsClick(getXIcon(), "关闭银行卡绑定提示框的X图标");
		SeleniumUtil.sleep();
		selenium.jsClick(getChangBankCardAnnalBtn(), "更换记录按钮");
		
		String oldBankCardNo = selenium.getText(getOldBankNoText());
		String newBankCardNoText = selenium.getText(getNewBankNoText());
		String changBankNoRule = selenium.getText(getChangBankNoRule());
		
		if(newBankCardNo.equals(newBankCardNoText) &&! newBankCardNo.equals(oldBankCardNo) && changBankNoRule.equals(carLoanLoginInfo.getproperValue("DG00000"))){
			logger.info("更换银行卡成功！！");
		}
		else{
			MyAssert.myAssertFalse("更换银行卡记录显示有问题！！");
		}
	}
	
}
