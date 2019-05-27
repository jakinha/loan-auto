package cn.web.service.credit_service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cn.web.base.ReadJsonBase;
import cn.web.common.GetOrdeProcess;
import cn.web.common.ImgUploadUtils;
import cn.web.common.InputDate;
import cn.web.common.TrustSystem;
import cn.web.common.WindowsUtils;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.common.constant.WebPlatformConstant;
import cn.web.common.credit_loan.CycleOperat;
import cn.web.dao.redis.ConnectRedis;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.Address;
import cn.web.util.ElementModle;
import cn.web.util.KeyBoard;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;


/**
 * 审批流程--<从录入复核至签合同>
 * 
 * @author Administrator
 * 
 *         					  StripFrame1 TabContentFrameArr StripFrame1 right  ObjectList  保存
 * 
 *     	myiframe0 frameDown   StripFrame1 TabContentFrameArr StripFrame1 right  ObjectList  意见
 *        
 *  	myiframe0 frameMiddle StripFrame1 TabContentFrameArr StripFrame1 right  ObjectList  收入
 *        
 *         
 *
 */

public class ApprovalProcessService {

	static PropertyUtil iframe_pro = GetElemFilePropertyUtil.ifmpro;
	
	static PropertyUtil pro = GetElemFilePropertyUtil.creditSysElePro;
	
	Logger logger = Logger.getLogger(ApprovalProcessService.class);
	
	private String orderNo;
	
	private Alert alert;
	
	/**
	 * 个贷录入复核
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 */
	public void entryReviewAction(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		selenium.moveToElement(pro.getproperValue("applyProc"));
		selenium.click(selenium.getWebElement(pro.getproperValue("entryReview")),"录入复核");
		selenium.switchFrameExec(iframe_pro.getproperValue("SecondFrame_"));

		GetOrdeProcess.getOrderAlert(selenium, pro.getproperValue("getOrder"));

		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));
		
		orderNo =orderNoResource(basic);
		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"),"订单号选择项");
		
		selenium.switchPrentFrame("F");

		selenium.dclick(selenium.getWebElement(pro.getproperValue("reviewProc")),"复核处理");
		
		ImgUploadUtils.imgUpload(selenium,testDataBean.getCheckPoint());
	}
	

	/**
	 * 个贷初审
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 */
	public void firstReviewAction(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		selenium.moveToElement(pro.getproperValue("reviewApproval"));
		selenium.click(selenium.getWebElement(pro.getproperValue("firstReview")),"信贷初审");
			
		processJump(selenium, basic,ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));
				
		selenium.switchFrameExec((iframe_pro.getproperValue("TabContentFrameArr") + "," + iframe_pro.getproperValue("StripFrame1")));
				
		selenium.click(selenium.getWebElement(pro.getproperValue("saveButton")),"初审审批意见界面的保存按钮");// 该操作目的是防止保存按钮的消失

		selenium.switchFrameExec((iframe_pro.getproperValue("frameDown") + "," + iframe_pro.getproperValue("myiframe0")));
				
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("inputApprovalComment")),"初审通过吧","初审意见");

		processSave(selenium, testDataBean);
		
	}
	
	/**
	 * 初审回退补件
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 */
	public void firstTrialFallbacksAction(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic){
		
		orderNo =orderNoResource(basic);
		selenium.moveToElement(pro.getproperValue("reviewApproval"));
		selenium.click(selenium.getWebElement(pro.getproperValue("firstReview")),"信贷初审");
		
		String iframes = iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("FS")+","+iframe_pro.getproperValue("myiframe0");
		selenium.switchFrameExec(iframes);
		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"),"初审列表查询订单号:"+orderNo);		
		selenium.switchPrentFrame("F");
		
		selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("fullSubmit")),11,"初审界面的提交按钮");
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		
		selenium.click(selenium.getWebElement(pro.getproperValue("first.trial")), "初审的回退选项");
		selenium.click(selenium.getWebElement(pro.getproperValue("main.why.list.select")), "初审回退原因列表");
		selenium.click(selenium.getWebElement(pro.getproperValue("main.why.list")), "初审回退原因");
		selenium.click(selenium.getWebElement(pro.getproperValue("zi.why.list.select")), "初审回退原因列表");
		selenium.click(selenium.getWebElement(pro.getproperValue("zi.why.list")), "初审回退原因");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("remark")), "回退补件咯", "初审回退补件备注信息");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"初审提交动作选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"包含断言","确定提交");
		
	}
	

	/**
	 * 个贷终审
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 */
	public void finalReviewAction(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		selenium.moveToElement(pro.getproperValue("reviewApproval"));
		selenium.click(selenium.getWebElement(pro.getproperValue("finalApproval")),"终审审批");

		processJump(selenium, basic,ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));
				

		selenium.switchFrameExec((iframe_pro.getproperValue("TabContentFrameArr") + "," + iframe_pro.getproperValue("StripFrame1")));
				
						
		selenium.click(selenium.getWebElement(pro.getproperValue("saveButton")),"终审意见的保存按钮");// 该操作目的是防止保存按钮的消失

		selenium.switchFrameExec((iframe_pro.getproperValue("frameDown") + "," + iframe_pro.getproperValue("myiframe0")));
				
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("inputApprovalComment")), "终审通过吧","终审意见");

		processSave(selenium, testDataBean);
		
/*		selenium.getDriver().quit();		
		finalReviewProductTypeProcess(basic);*/
		
	}

	/**
	 * 电话预约
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 */
	public void phoneReservation(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		orderNo = orderNoResource(basic);
		
		CycleOperat.flowTaskTableQuery(orderNo, "签约预约");
		selenium.moveToElement(pro.getproperValue("signManage"));
		selenium.click(selenium.getWebElement(pro.getproperValue("phoneReservation")),"电话预约");
		
		selenium.getDriver().navigate().refresh();
		selenium.moveToElement(pro.getproperValue("signManage"));
		selenium.click(selenium.getWebElement(pro.getproperValue("phoneReservation")),"电话预约");
		
		selenium.switchFrameExec((iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("myiframe0")));
		
		selenium.click(selenium.getWebElement("<//input[@value='" + orderNo + "']"),"电话预约的订单号选择项");
		selenium.switchPrentFrame("F");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("reservationProc")),"电话预约的预约处理");
		telphoneReserve(selenium, orderNo, testDataBean);

	}

	/** 门店签约 **/
	public void storeSign(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		orderNo = orderNoResource(basic);
		selenium.moveToElement(pro.getproperValue("signManage"));
		selenium.click(selenium.getWebElement(pro.getproperValue("storeSign")),"门店签约");
		selenium.switchFrameExec((iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("myiframe0")));

		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"), "订单号选择项");
		
		selenium.switchPrentFrame("F");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("signProc")),"签约处理");
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);

		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));
		
		// 获取置灰的客户的性别和出生年月值
		selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + ","+ iframe_pro.getproperValue("StripFrame0") + "," + iframe_pro.getproperValue("myiframe0")));
				
		String name = selenium.getPropertyValue(pro.getproperValue("R_name"), "value");
		String idcard = selenium.getPropertyValue(pro.getproperValue("R_idcard"), "value");
		String sex = selenium.getPropertyValue(pro.getproperValue("R_sex"), "value");
		String brithday = selenium.getPropertyValue(pro.getproperValue("R_brithday"), "value");
		
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("livePhone")), "02154","住宅电话");
		if(System.getProperty("productType").contains(WebPlatformConstant.PRODUCT)){
			selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("zipCode")), "000222","邮编");
			selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("email")), "huang@qq.com","邮箱");
			selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("mailingAddress")), "大上海","通讯地址");
		}
		//所属行业的选择
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + ","+ iframe_pro.getproperValue("StripFrame1") + "," + iframe_pro.getproperValue("myiframe0")));
		
		selenium.click(selenium.getWebElement(pro.getproperValue("industry")), "所属行业下拉按钮");
		selenium.click(selenium.getWebElement(pro.getproperValue("husbandry_fishery")), "所属行业");
		if(!System.getProperty("productType").contains(WebPlatformConstant.PRODUCT)){
			selenium.click(selenium.getWebElement(pro.getproperValue("other_platform_loan_info_select")), "在其他平台借款情况下拉按钮");
			selenium.click(selenium.getWebElement(pro.getproperValue("other_platform_loan_info")), "在其他平台借款情况");
			
			selenium.click(selenium.getWebElement(pro.getproperValue("other_platform_loan_money_select")), "在其他平台借款总金额按钮");
			selenium.click(selenium.getWebElement(pro.getproperValue("other_platform_loan_money")), "在其他平台借款总金额");
		}
		
		selenium.switchPrentFrame("F");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存按钮");
		SeleniumUtil.sleep(1);
		selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存按钮");

		// 读卡信息填写
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + ","+ iframe_pro.getproperValue("StripFrame2") + "," + iframe_pro.getproperValue("myiframe0")));
				
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("clientName")), name,"客户姓名");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("idCard")),idcard,"客户身份证");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("sex")), sex,"性别");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("ethnic")), "汉","民族");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("birthday")), brithday,"出生日");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("address")), Address.getIndex(6),"地址");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("organization")), Address.getIndex(6),"签发机构");
		selenium.switchPrentFrame("F");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存按钮");
		SeleniumUtil.sleep(1);
		selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存按钮");

		//针对P2P产品和信托产品的处理(所有产品都需要做华夏普惠注册绑卡，后续处理)
//		storeSignProductTypeProcess(selenium,testDataBean);
		
		selenium.switchPrentFrame("D");
		selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + "," + iframe_pro.getproperValue("StripFrame0")));
		// 上传通话详情文件
		selenium.dclick(selenium.getWebElement(pro.getproperValue("callDetails")),"通讯详情");
		windowsUtils.initWindows(3);
		//窗口标题和页面文本
		String winTitle = ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch");
		String pageText = ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.pageText");
		
		windowsUtils.switchTitleExistSame(winTitle,iframe_pro.getproperValue("ObjectList"),pageText);
		selenium.dclick(selenium.getWebElement(pro.getproperValue("browse")),"浏览");
		KeyBoard.setAndctrlVClipboardData(ReadJsonBase.readJson(testDataBean.getTestData(), "$.data.loadFilePath"));

		selenium.click(selenium.getWebElement(pro.getproperValue("confirm")),"确认");
		KeyBoard.enter();
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));
		selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + "," + iframe_pro.getproperValue("StripFrame0")));
				
		// 生成合同协议
		selenium.click(selenium.getWebElement(pro.getproperValue("contractAgreement")),"生成协议及合同");
		SeleniumUtil.sleep(2);
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"完全断言", ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.contractGenerate"));

		windowsUtils.closeAllWin(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));

	}

	/** 门店签约提交 **/
	public void storeSignSubmit(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		selenium.moveToElement(pro.getproperValue("signManage"));
		selenium.click(selenium.getWebElement(pro.getproperValue("storeSign")),"门店签约");
		selenium.switchFrameExec((iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("myiframe0")));
		
		orderNo = orderNoResource(basic);
		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"), "订单号选择项");
		selenium.switchPrentFrame("F");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit")),"门店签约界面提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1", null);
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"门店签约的提交选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1", null);
		KeyBoard.enter();
	}

	/** 合同审核提交 **/
	public void contractReviewSubmit(SeleniumUtil selenium, TestDataModel testDataBean, BasicTestDataBean basic) {

		orderNo = orderNoResource(basic);
		//运营管理
		selenium.moveToElement(pro.getproperValue("OperationManage"));
		selenium.click(selenium.getWebElement(pro.getproperValue("contractReview")),"合同审核");
		
		selenium.switchFrameExec((iframe_pro.getproperValue("SecondFrame_")+","+iframe_pro.getproperValue("myiframe0")));
		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"),"分页搜索订单号");
		selenium.switchPrentFrame("F");

		selenium.click(selenium.getWebElement(pro.getproperValue("viewDetails")),"查看详情");
		// 合同提交
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.backMainWindow(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));

		selenium.switchFrameExec((iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("myiframe0")));
				
		selenium.click(selenium.getWebElement("<//input[@value='" + orderNo + "']"),"合同审核界面的订单选择项");
		selenium.switchPrentFrame("F");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit")),"提交");
		windowsUtils.initWindows(2);

		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"合同审核界面的提交选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1", null);
		SeleniumUtil.sleep(10);
	}
	
	//电话预约的时间操作处理
	private void telphoneReserve(SeleniumUtil selenium, String applyNum, TestDataModel testDataBean) {

		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch"));
			
		selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + "," + iframe_pro.getproperValue("SecondFrame_ObjectList")+ "," + iframe_pro.getproperValue("myiframe0")));
						
		InputDate.inputTomorrowDate(selenium, pro.getproperValue("telReDate"));
		logger.info("成功输入预约日期！！");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("telReTimeSele")),"电话预约时间下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("telReTime")),"选择电话预约时间");
		selenium.switchPrentFrame("F");

		selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存");
		SeleniumUtil.sleep(1.5);
		selenium.switchPrentFrame("D");
		selenium.jsClick(selenium.getWebElement(pro.getproperValue("telReClose")),"关闭");
		
		alert = selenium.alertIsDisplay();
			
		while(null!=alert){//while是为了单击保存按钮没有生效,关闭页面时，弹出提示框，这时单击取消，继续单击保存按钮，直接关闭页面时没有弹框出现

			selenium.AlertCance(alert);
			selenium.switchFrameExec((iframe_pro.getproperValue("ObjectList") + "," + iframe_pro.getproperValue("SecondFrame_ObjectList")));
			selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存");
			SeleniumUtil.sleep(1);
			selenium.switchPrentFrame("D");
			selenium.jsClick(selenium.getWebElement(pro.getproperValue("telReClose")),"关闭");
			alert = selenium.alertIsDisplay();
		}
		windowsUtils.getMainWindow();

		selenium.switchFrameExec((iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("myiframe0")));
				
		selenium.click(selenium.getWebElement("<//input[@value='" + applyNum + "']"),"订单选择项");

		selenium.switchPrentFrame("F");

		selenium.click(selenium.getWebElement(pro.getproperValue("telReSubmit")),"预约提交");

		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1", null);

	}
	
	/** 初审、终审 获取订单 -> 审查处理 界面 ->单击 审批意见 按钮 **/
	private void processJump(SeleniumUtil selenium, BasicTestDataBean basic, String checkWinTilech) {

		String iframes = iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("FS");
		selenium.switchFrameExec(iframes);
		orderNo = orderNoResource(basic);
		
		if(System.getProperty("productType").contains("信托")){
			int i = 1;
			while(selenium.getText(selenium.getWebElement("</html/body/div[3]/font")).equals("0") && i<6){
				
				if(selenium.getDriver().getPageSource().contains(orderNo)){
					break;
				}
				logger.info("等待跑时间定时...");
				SeleniumUtil.sleep(60);
				selenium.getDriver().navigate().refresh();
				selenium.switchPrentFrame("D");
				selenium.switchFrameExec(iframes);
				i++;
			}
			if(i>=6)
				MyAssert.myAssertFalse("等待了5分钟,初审任务池单子数量一直为0,没有出现目标订单："+orderNo);
		}
		
		GetOrdeProcess.getOrderAlert(selenium, pro.getproperValue("getOrder"));

		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));

		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"),"查询到订单号:"+orderNo);		
		
		selenium.switchPrentFrame("F");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("censorProc")),"审查处理");
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);

		windowsUtils.switchTitleDiffrent(checkWinTilech);		
		String iframees = iframe_pro.getproperValue("ObjectList") + "," + iframe_pro.getproperValue("right") + ","+ iframe_pro.getproperValue("StripFrame1");
		selenium.switchFrameExec(iframees);
		selenium.click(selenium.getWebElement(pro.getproperValue("approvalComment")),"审批意见按钮");
	}

	/** 初审、终审界面 保存审批意见、关闭页面 处理 **/
	private void processSave(SeleniumUtil selenium, TestDataModel testDataBean) {

		selenium.switchPrentFrame("F2");
		alert = selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("saveButton")),11,"保存按钮");
		selenium.alertConfirm(alert,"-1", null);
		KeyBoard.enter();
		selenium.switchPrentFrame("D");

		if(testDataBean.getDescription().equals("初审")){
			
			if(System.getProperty("productType").contains("房")||System.getProperty("productType").contains("楼")){

				loanMaterialEvaluation(selenium);
			}
		}

		alert = selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("close")), 5, "关闭按钮");
		selenium.alertConfirm(alert,"-1", null);
	}
	
	/**
	 * 初审，终审的订单提交
	 * @param selenium	
	 * @param testDataBean
	 * @param orderNo
	 * @param eleKey	元素表达式的属性文件里元素的key
	 * @param logInfo	
	 */
	public void reviewSubmit(SeleniumUtil selenium,TestDataModel testDataBean,BasicTestDataBean basic,String eleKey,String logInfo){
		
		orderNo =orderNoResource(basic);
		
		selenium.moveToElement(pro.getproperValue("reviewApproval"));

		selenium.click(selenium.getWebElement(pro.getproperValue(eleKey)),logInfo);

		String iframes = iframe_pro.getproperValue("SecondFrame_") + "," + iframe_pro.getproperValue("FS")+","+iframe_pro.getproperValue("myiframe0");
		selenium.switchFrameExec(iframes);
		selenium.pageQuery("<//input[@value='" + orderNo + "']",pro.getproperValue("pageNo"),"订单号:"+orderNo);		
		selenium.switchPrentFrame("F");
	
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit")),"提交按钮");
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交动作选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"包含断言","确定提交");
		
		if(testDataBean.getDescription().equals("终审提交")){
			SeleniumUtil.sleep(1.5);
			alert = selenium.alertIsDisplay();
			selenium.alertConfirm(alert,"包含断言","是否确认通过");
			selenium.exceptionAlert("终审提交时");
			selenium.getDriver().quit();
		}
	}
	
	/**
	 * 借款材料评估<选择的是房产的产品，则增加房产信息。选择的是车辆产品，则添加车辆信息>
	 * @param selenium
	 */
	private void loanMaterialEvaluation(SeleniumUtil selenium){
		
		String iframees = iframe_pro.getproperValue("ObjectList") + "," + iframe_pro.getproperValue("right") + ","+ iframe_pro.getproperValue("StripFrame1");
		selenium.switchFrameExec(iframees);
		selenium.click(selenium.getWebElement(pro.getproperValue("loanMaterialEvaluation")),"借款材料评估按钮");
		selenium.switchFrameExec((iframe_pro.getproperValue("TabContentFrameArr")+","+iframe_pro.getproperValue("StripFrame5")));
		selenium.dclick(selenium.getWebElement(pro.getproperValue("newAdd")),"新增按钮");
		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));
		selenium.click(selenium.getWebElement(pro.getproperValue("houseTypeSelect")),"房屋类型选择");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullRoomValue")),"房屋类型");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("houseDyTypeSelect")),"房屋抵押类型选择");
		selenium.click(selenium.getWebElement(pro.getproperValue("fullDyRoomValue")),"房屋抵押类型");
		logger.info("成功选择了房屋抵押类型！！");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("purchaseContract")),"房屋购买合同");
		selenium.click(selenium.getWebElement(pro.getproperValue("deed")),"房屋房产所有权证");

		selenium.switchPrentFrame("F");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("saveButton")),"保存");
		selenium.switchPrentFrame("D");
		
	}
	
	/**判断订单号来源
	 * 一：从系统属性中获得
	 * 二：从basic对象获得
	 * @return String 返回一个订单号
	 * **/
	public static String orderNoResource(BasicTestDataBean basic){
		
		if(StringUtils.isNotBlank(System.getProperty("applyNum"))){
			return System.getProperty("applyNum");
		}
		else if(null!=basic.getApplyNum()){
			return basic.getApplyNum();
		}
		else{
			return ConnectRedis.getKeyValue("applyNum");
		}
	}
	
	/**在终审这里 针对中粮系的产品和一般系的产品的不同处理**/
	public void finalReviewProductTypeProcess(BasicTestDataBean basic){
		
		//若是中粮所属产品，则需要去信托计划去审核,否则去资金系统推包
		if(System.getProperty("productType").contains(WebPlatformConstant.PRODUCT)){
			SeleniumUtil seleniumNew = new SeleniumUtil();
			TrustSystem.trustPlanToDebtReview(seleniumNew,basic);
		}
		else{
			logger.info("p2p单子直接进入到电话预约节点");
		}
	}
	
	/**在门店签约这里 针对中粮系的产品和一般系的产品的不同处理**/
	public void storeSignProductTypeProcess(SeleniumUtil selenium,TestDataModel testDataBean){
		
		// 中粮所属产品的话，则需要进行银行卡验证的操作
		if(System.getProperty("productType").contains(WebPlatformConstant.PRODUCT)){
			
			zhongliang(selenium, testDataBean);
		}
		/*<前提：去华夏普惠app注册绑卡,期望走接口>
		 * 刷新平安账户 和 银行卡刷新，带出华夏普惠注册绑卡的信息  <非中粮所属产品>
		 */
		else{
			System.out.println("先取华夏普惠注册绑卡.........");
			selenium.switchPrentFrame("D");
			selenium.switchFrameExec(iframe_pro.getproperValue("ObjectList"));
			selenium.click(selenium.getWebElement(pro.getproperValue("pinganRefresh")),"平安账户【刷新】");
			selenium.click(selenium.getWebElement(pro.getproperValue("BindBankCardRefresh")),"绑定银行卡【刷新】");
		}
	}
	
	/**合同审核提交之后，对于中粮系产品和p2p产品的处理
	 * 中粮产品，去信托系统做合同审核和放款操作
	 * p2p产品，则..........
	 * **/
	public void contractTrialAfterProcess(SeleniumUtil selenium,BasicTestDataBean basic){
		
		orderNo = orderNoResource(basic);
		
		if(System.getProperty("productType").contains(WebPlatformConstant.PRODUCT)){

			//信托计划
			selenium.moveToElement(pro.getproperValue("trust.plan"));
			selenium.click(selenium.getWebElement(pro.getproperValue("trust.plan.match")),"信托计划匹配");
			
			ApplyOrderWriteInfoService.search(selenium,pro.getproperValue("search.inputbox"),orderNo,"订单号搜索");

			selenium.click(selenium.getWebElement(pro.getproperValue("check.box")),"订单列选项框");
			
			String signedMoneyStr =selenium.getPropertyValue(pro.getproperValue("signed.money"), "value");
			String replace = signedMoneyStr.replace("," , "");
			//签约金额
			int signedMoney = Integer.parseInt(replace.substring(0, replace.indexOf(".")));
			
			selenium.switchPrentFrame("F");
			selenium.click(selenium.getWebElement(pro.getproperValue("match.trust.plan")),"匹配信托计划");
			WindowsUtils windowsUtils = new WindowsUtils(selenium);
			
			windowsUtils.initWindows(2);
			windowsUtils.switchNullTitle();
			
			selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));

			//遍历所有信托计划的所剩募资金额
			List<WebElement> findElements = selenium.getDriver().findElements(ElementModle.getEleModle("<//div[@id='tableContainer']/table/tbody/tr"));
			findElements.remove(0);
			int i = 1;
			for (;i<=findElements.size();i++) {
				
				String fundraisingMoneyValue = selenium.getDriver().findElement(ElementModle.getEleModle("<//div[@id='tableContainer']/table/tbody/tr["+i+"]/td[5]/input")).getAttribute("value");
				String alreadyFundraisingMoneyValue = selenium.getDriver().findElement(ElementModle.getEleModle("<//div[@id='tableContainer']/table/tbody/tr["+i+"]/td[6]/input")).getAttribute("value");
				//总募资金额
				int fundraisingMoney = Integer.parseInt(fundraisingMoneyValue.substring(0, fundraisingMoneyValue.indexOf(".")));
				//已经募资金额
				int alreadyFundraisingMoney = Integer.parseInt(alreadyFundraisingMoneyValue.substring(0, alreadyFundraisingMoneyValue.indexOf(".")));
				//剩余募资金额
				int surplusFundraisingMoney = fundraisingMoney-alreadyFundraisingMoney;
				
				if(surplusFundraisingMoney>signedMoney){
					selenium.click(selenium.getWebElement(pro.getproperValue("<//div[@id='tableContainer']/table/tbody/tr["+i+"]")), "选择信托计划项");
					break;
				}
			}
			if(i>findElements.size()){
				logger.info("没有找到可用的信托计划 ,目前的信托计划的所剩募资金额都小于签约金额,请先添加一个可用的信托计划!!");
			}
			else{
				selenium.switchPrentFrame("F");
				selenium.click(selenium.getWebElement(pro.getproperValue("configbut")),"确定按钮");
				Alert alert = selenium.alertIsDisplay();
				selenium.alertConfirm(alert, "包含断言", "确认是否继续");
				selenium.getDriver().quit();
				
				SeleniumUtil seleniumNew = new SeleniumUtil();
				TrustSystem.trustContractTrial(seleniumNew,basic);
			}
		}
		else{
			System.out.println("暂时没有对p2p产品合同审核之后的操作");
		}
	}
	
	/**
	 * 门店签约时中粮产品的银行卡校验处理
	 * @param selenium
	 * @param testDataBean
	 */
	private void zhongliang(SeleniumUtil selenium,TestDataModel testDataBean){
		
		selenium.switchPrentFrame("D");
		String iframes=iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("StripFrame4")+","+iframe_pro.getproperValue("myiframe0");
		selenium.switchFrameExec(iframes);
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("bankNo")), "6222023803013297"+RandomUtil.randomNum(3),"银行卡号");
		selenium.click(selenium.getWebElement(pro.getproperValue("bankNameSelect")),"银行选择");
		selenium.click(selenium.getWebElement(pro.getproperValue("bankNameValue")),"银行名字");

		selenium.dclick(selenium.getWebElement(pro.getproperValue("bankBranchSelect")),"银行开户支行");
		selenium.click(selenium.getWebElement(pro.getproperValue("bankBranchSelect")),"银行开户支行");
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(3);

		String iframees = iframe_pro.getproperValue("ObjectList")+","+iframe_pro.getproperValue("myiframe0");
		String winTitle = ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch");
		windowsUtils.switchTitleExistSame(winTitle,iframees,"股份有限公司");
		selenium.dclick(selenium.getWebElement(pro.getproperValue("bankBranchValue")),"银行分支所在城市");

		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_ch") );
		
		selenium.switchFrameExec(iframes);
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("bankPhone")), "15088554466","预留在银行的手机号码");
		selenium.switchPrentFrame("F");
		
		alert = selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("bankNoVerifyBut")),10,"银行卡验证按钮");
		selenium.alertConfirm(alert,"完全断言", ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.bankVerify"));
		
		//判断弹出的新alert,验证码已发送到手机
		alert = selenium.clickDisplayAlert(null,50,"开始进行放款卡号验证的Alert!");
		selenium.alertConfirm(alert,"完全断言","验证码已发送至手机，请注意查收！");
	
		selenium.switchFrameExec(iframe_pro.getproperValue("myiframe0"));
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("phoneMsgverify")),"111111","短信码");

		int i=0;
		do{
			if(i!=0){
				if(i>10){Assert.fail("输入10种银行卡号校验都不通过");}
				selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("bankNo")),"6222023803013297"+RandomUtil.randomNum(3),"银行卡号");
			}
			selenium.click(selenium.getWebElement(pro.getproperValue("verify")),"校验按钮");
			alert = selenium.clickDisplayAlert(null,50,"校验结果Alert");
			i++;
		}
		while(!alert.getText().equals(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.bankVerifyPass")));
		alert.accept();
	}
	
}
