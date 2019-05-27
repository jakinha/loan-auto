package cn.web.common.carloan;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cn.web.base.LoginBase;
import cn.web.base.PageBase;
import cn.web.base.ReadJsonBase;
import cn.web.common.ImgUploadUtils;
import cn.web.dao.sql.SqlUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.service.car_loan_service.FinallyTrialService;
import cn.web.service.car_loan_service.FirstTrialService;
import cn.web.service.car_loan_service.HeadquartersEvaluateService;
import cn.web.service.car_loan_service.LocalEvaluateService;
import cn.web.service.car_loan_service.StoreEntryService;
import cn.web.util.Constant;
import cn.web.util.KeyBoard;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;

/**
 * 公共的操作,只集成父类的一些静态的属性
 * @author huangjun
 *
 */
public class LoanFrontPubOperat extends PageBase{

	private static Logger logger = Logger.getLogger(LoanFrontPubOperat.class);
	
	private static PropertyUtil configPropertyUtil = new PropertyUtil(Constant.PUBLIC_CONFIG);
	
	private static String checkOpreaSucess = checkPointProperty.getproperValue("oprea_sucess");
	
	private static String checkBackSucess = checkPointProperty.getproperValue("back_sucess");
	
	private static String checkSearchResult = checkPointProperty.getproperValue("search_result");

	/**车贷贷前 子菜单 **/
	public static String carLoanFrontMenuLable = carElementProperty.getproperValue("car_loan_front");
	
	/**订单号搜索框**/
	public static String carLoanFrontSearchInputBox = carElementProperty.getproperValue("search_order_no");
	
	/**搜索按钮**/
	public static String carLoanFrontSearchBtn = carElementProperty.getproperValue("search_btn");
	
	/**勾选记录框**/
	public static String carLoanFrontCheckBox = carElementProperty.getproperValue("check_box");
	
	/**分配按钮**/
	public static String carLoanFrontDistributionBtn = carElementProperty.getproperValue("distribution");
	
	/**分配选择PC**/
	public static String carLoanFrontDistributionItemPc = carElementProperty.getproperValue("pc");
	
	/**输入分配人的输入框**/
	public static String carLoanFrontDistributionSearchInput = carElementProperty.getproperValue("distribution_search");
	
	/**输入分配人的输入框的搜索按钮**/
	public static String carLoanFrontDistributionSearchBtn = carElementProperty.getproperValue("distribution_search_btn");
	
	/**分页下拉**/
	public static String carLoanFrontPageSelect = carElementProperty.getproperValue("page_select");
	
	/**100页**/
	public static String carLoanFrontHundredItem = carElementProperty.getproperValue("hundred_item");
	
	/**页数输入框**/
	public static String carLoanFrontPageNumInput = carElementProperty.getproperValue("page_num_input");
	
	/**门店录入待处理 分页下拉**/
	public static String carLoanFrontStroeWaitQueuePageSelect = carElementProperty.getproperValue("store_entry_page_select");
	
	/**门店录入待处理 页数输入框**/
	public static String carLoanFrontStroeWaitQueuePageInput = carElementProperty.getproperValue("store_entry_page_num_input");
	
	/**影像平台**/
	public static String carLoanFrontImgUpload = carElementProperty.getproperValue("img_upload");
	
	/**本地评估-复核处理按钮**/
	public static String carLoanFrontLocalReview = carElementProperty.getproperValue("local_evaluate_process");
	
	/**总部评估-评估处理按钮**/
	public static String carLoanFrontHQReview = carElementProperty.getproperValue("headquarters_evaluate_process");
	
	/**初审-审核处理按钮**/
	public static String carLoanFrontFirstReview = carElementProperty.getproperValue("first_trial_process");
	
	/**门店录入-申请详情按钮**/
	public static String applyDailBtn = carElementProperty.getproperValue("apply_dail_btn");
	
	/**终审审核**/
	public static String carLoanFrontFinalReview = carElementProperty.getproperValue("final_trial_process");
	
	/**待处理标签**/
	public static String carLoanFrontWaitLable = carElementProperty.getproperValue("wait_process_queue");
	
	/**已处理标签**/
	public static String carLoanFrontAlreadyLable = carElementProperty.getproperValue("already_process_queue");
	
	/**回退按钮**/
	public static String carLoanFrontGoBackBtn = carElementProperty.getproperValue("go_back");
	
	/**回退到的节点下拉**/
	public static String carLoanFrontGoBackNodeSelect = carElementProperty.getproperValue("go_back_node_select");
	
	/**回退到的节点主原因下拉**/
	public static String carLoanFrontGoBackMainReasonSelect = carElementProperty.getproperValue("back_main_reason_select");
	
	/**备注**/
	public static String carLoanFrontRemark = carElementProperty.getproperValue("why_remark");
	
	/**回退到的节点主原因下拉**/
	public static String carLoanFrontGoBackSubReasonSelect = carElementProperty.getproperValue("back_sub_reason_select");
	
	/**确认**/
	public static String carLoanFrontGoBackConfirmBtn = carElementProperty.getproperValue("first_back_confirm");
	
	/**确认**/
	public static String carLoanFrontGoBackConfirmContainsText = carElementProperty.getproperValue("confirm_contains_text");
	
	/**初审、终审拒绝按钮**/
	public static String carLoanFrontRejectBtn = carElementProperty.getproperValue("reject");
	
	/**初审、终审拒绝主原因下拉**/
	public static String carLoanFrontRejectMainReasonSelect = carElementProperty.getproperValue("main_reason_select");
	
	/**初审、终审拒绝主原因**/
	public static String carLoanFrontRejectMainReason = carElementProperty.getproperValue("reject_main_reason");
	
	/**初审、终审拒绝子原因下拉**/
	public static String carLoanFrontRejectSubReasonSelect = carElementProperty.getproperValue("sub_reason_select");
	
	/**初审、终审拒绝子原因**/
	public static String carLoanFrontRejectSubReason = carElementProperty.getproperValue("reject_sub_reason");
	
	/**初审、终审拒绝确认按钮**/
	public static String carLoanFrontRejectConfimBtn = carElementProperty.getproperValue("reject_confim");
	
	/**初审审核结果角标**/
	public static String carLoanFrontFirstTrialResultTag = carElementProperty.getproperValue("first_trial_result");
	
	/**终审审核结果角标**/
	public static String carLoanFrontFinalTrialResultTag = carElementProperty.getproperValue("final_trial_result");
	
	/**取消、拒绝 确认按钮**/
	public static String carLoanFrontCanceRejectConfirmText = carElementProperty.getproperValue("confirm_text");
	
	/**流程监控**/
	public static String carLoanFrontProcessListener = carElementProperty.getproperValue("process_listener");
	
	/**初审、终审 通过按钮**/
	public static String carLoanFrontTrialPass = carElementProperty.getproperValue("trial_pass");
	
	/**初审、终审 通过确认的按钮**/
	public static String carLoanFrontTrialPassConfirm = carElementProperty.getproperValue("confirm");
	
	/**实地调查项**/
	public static String carLoanFrontFieldSurveyItem = carElementProperty.getproperValue("contains_field_survey");
	
	/**实地调查 提交**/
	public static String carLoanFrontFieldSurveySubmit = carElementProperty.getproperValue("submit_field_survey");
	
	/**实地调查 工作单位**/
	public static String carLoanFrontFieldSurveyUnit = carElementProperty.getproperValue("survey_unit");
	
	/**实地调查 居住地**/
	public static String carLoanFrontFieldSurveyLive = carElementProperty.getproperValue("survey_live");
	
	/**实地调查 备注**/
	public static String carLoanFrontFieldSurveyRemark = carElementProperty.getproperValue("field_survey_remark");
	
	/**终审实地调查 备注**/
	public static String carLoanFrontFieldSurveyRemarkFinalReview = carElementProperty.getproperValue("field_survey_remark_final");
	
	/**提交实地调查确认按钮**/
	public static String carLoanFrontFieldSurveySubmitDetermine = carElementProperty.getproperValue("determine");
	
	/**实地分配**/
	public static String carLoanFrontFieldAssign = carElementProperty.getproperValue("field_assign");
	
	/**实地调查**/
	public static String carLoanFrontFieldSurvey = carElementProperty.getproperValue("field_survey");
	
	/**实地处理**/
	public static String carLoanFrontFieldProcess = carElementProperty.getproperValue("field_process");
	
	/**实地调查-公司描述**/
	public static String carLoanFrontFieldCompanyDescribe = carElementProperty.getproperValue("company_describe");
	
	/**实地调查-居住地描述**/
	public static String carLoanFrontFieldLiveDescribe = carElementProperty.getproperValue("live_describe");
	
	/**实地调查-房产描述**/
	public static String carLoanFrontFieldHouseDescribe = carElementProperty.getproperValue("house_describe");
	
	/**实地调查-保存**/
	public static String carLoanFrontFieldSurveySave = carElementProperty.getproperValue("field_survey_save");
	
	/**实地调查-提交**/
	public static String carLoanFrontEvaluateSubmit = carElementProperty.getproperValue("evaluate_submit");
	
	/**
	 * 搜索分配的人员的操作
	 * @param selenium
	 * @param elepro	订单号搜索框
	 * @param orderNum	订单号
	 * @param loginRule	业务员角色
	 */
	public static void searchAssignOperat(SeleniumUtil selenium,String orderNum,String loginRule){
		
		if(StringUtils.isBlank(orderNum)){
			
			MyAssert.myAssertFalse("申请录入-填写基本信息用例失败,订单号未生产,故后续的用例都将失败");
		}
		
		selenium.input(selenium.getWebElement(carLoanFrontSearchInputBox), orderNum, "搜索订单号:");
		
		selenium.dclick(selenium.getWebElement(carLoanFrontSearchBtn), "搜索按钮");
		SeleniumUtil.sleep(2);
		selenium.jsClick(selenium.getWebElement(carLoanFrontCheckBox), "勾选记录");
		
		selenium.click(selenium.getWebElement(carLoanFrontDistributionBtn), "分配按钮");
		
		for(int i = 0;i<3;i++){
			
			selenium.jsClick(selenium.getWebElement(carLoanFrontDistributionItemPc), "pc");
			SeleniumUtil.sleep(0.8);

		}
		selenium.input(selenium.getWebElement(carLoanFrontDistributionSearchInput), loginRule, "输入要分配的业务员姓名");
		
		SeleniumUtil.sleep(0.8);
		
		selenium.dclick(selenium.getWebElement(carLoanFrontDistributionSearchBtn), "分配弹框里的搜索按钮");
		SeleniumUtil.sleep(1);

		WebElement	webElement = selenium.getWebElement("<//span[text()='"+configPropertyUtil.getproperValue(loginRule)+"("+loginRule+")']");
		
		WebElement goalElement = webElement.findElement(By.xpath("./parent::span/parent::span/span[2]/button/span"));
		selenium.dclick(goalElement, "选择");
		
		Alert alert = selenium.alertIsDisplay();

		if(null != alert){
			
			MyAssert.myAssertEquals(alert.getText(), "分配成功!");
		}

		SeleniumUtil.sleep(1);
		KeyBoard.enter();
		KeyBoard.enter();
		SeleniumUtil.sleep(1);
		selenium.switchPrentFrame("D");
	}
	
	/**
	 * 分页查询,选择每页显示100条数据
	 * @param selenium
	 * 
	 * @param applyNum
	 * 				订单号
	 * @return
	 * 			true：找到单子，false:没有找到单子
	 */
	public static boolean pageQuery(SeleniumUtil selenium,String applyNum){
		
		if(StringUtils.isBlank(applyNum)){
			
			MyAssert.myAssertFalse("申请录入-填写基本信息用例失败,订单号未生产");
		}	
		
		SeleniumUtil.sleep(2);
		
		if(!selenium.getDriver().getPageSource().contains(applyNum)){
			
			selenium.jsClick(selenium.getWebElement(carLoanFrontPageSelect), "分页下拉");
			
			selenium.jsClick(selenium.getWebElement(carLoanFrontHundredItem), "100条/页");
			
			SeleniumUtil.sleep();
			
			int i = 1;
			
			while(!selenium.getDriver().getPageSource().contains(applyNum)){
				
				i++;
				selenium.cleanAndInput(selenium.getWebElement(carLoanFrontPageNumInput), String.valueOf(i), "输入第"+i+"页");
				
				SeleniumUtil.sleep(1);
				
				KeyBoard.enter();
				
				if(i>5){
					
					return false;
				}
			}
			
			logger.info("单子找到了:"+applyNum);
		}

		return true;
	}
	
	/**
	 * 门店录入待处理队列  分页查询,选择每页显示100条数据
	 * @param selenium
	 * @param applyNum
	 * @return
	 */
	public static boolean storeEntryPageQuery(SeleniumUtil selenium,String applyNum){
		
		SeleniumUtil.sleep(2);
		
		if(!selenium.getDriver().getPageSource().contains(applyNum)){
			
			selenium.jsClick(selenium.getWebElement(carLoanFrontStroeWaitQueuePageSelect), "门店录入待处理队列 -分页下拉");
			selenium.jsClick(selenium.getWebElement(carLoanFrontHundredItem), "100条/页");
			SeleniumUtil.sleep();
			int i = 1;
			
			while(!selenium.getDriver().getPageSource().contains(applyNum)){
				i++;
				selenium.cleanAndInput(selenium.getWebElement(carLoanFrontStroeWaitQueuePageInput), String.valueOf(i), "输入第"+i+"页");
				SeleniumUtil.sleep(1);
				KeyBoard.enter();
				if(i>5){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 操作左侧导航栏车贷菜单下的子菜单并跳转iframe
	 * @param selenium
	 * @param subMenuElement	要操作的左侧车贷贷前菜单下面的子菜单元素(元素定位属性文件里的键)
	 * @param iframe	元素定位属性里键
	 * @param subMenuDescripet	子菜单的名称
	 */
	public static void leftNavigation(SeleniumUtil selenium,String subMenuElement,String iframe,String subMenuDescripet){

		selenium.switchPrentFrame("F");
		
		selenium.refreshPage();
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontMenuLable), "车贷贷前");
		
		selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue(subMenuElement)),subMenuDescripet);

		selenium.switchFrameExec(iframeProperty.getproperValue(iframe));
	}

	
	/**影像上传的操作**/
	public static void someNodeImgUpload(SeleniumUtil selenium,String nodeName){
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontImgUpload), "影像平台");
		
		SeleniumUtil.sleep();
		KeyBoard.tableAndShfit();
		KeyBoard.enter();
		
		ImgUploadUtils.uploadImg("carsys-store-entry", nodeName);//车贷的影像上传的exe
	}
	
	/**
	 * 订单回退到某个节点后，在该节点对订单进行再次提交操作
	 * 1.待处理队列  找到订单号 ，单击 【处理操作】
	 * 2.订单直接提交走（初审，终审要重新输入审批意见等操作）
	 * @param selenium
	 * @param order	订单号
	 */
	public static <T>  void pendingQueueDirectProcessOrder(T viewTrial, SeleniumUtil selenium , String order,String nodeCommit){

		pageQuery(selenium,order);
		
		try {

			if(viewTrial instanceof LocalEvaluateService){

				selenium.dclick(selenium.getWebElement(carLoanFrontLocalReview.replace("订单号", order)), "复核  处理");
				
				((LocalEvaluateService) viewTrial).localEvaluateSubmitOpera();
			
			}else if(viewTrial instanceof HeadquartersEvaluateService){
				
				selenium.dclick(selenium.getWebElement(carLoanFrontHQReview.replace("订单号", order)), "评估 处理");
				
				((HeadquartersEvaluateService) viewTrial).headquartersEvaluateSubmitOpera(order);
				
			}else if(viewTrial instanceof FirstTrialService){

				selenium.dclick(selenium.getWebElement(carLoanFrontFirstReview.replace("订单号", order)), "审核 处理");
				
				((FirstTrialService) viewTrial).carSysFirstTrial();
				firstFinalTrialPass(selenium, order);
				
			}
			else if(viewTrial instanceof StoreEntryService){
				
				selenium.dclick(selenium.getWebElement(applyDailBtn.replace("订单号", order)), "申请详情");
				
				((StoreEntryService)viewTrial).storeSubmitOpera(order, "提交");
			}
			
			else 
				MyAssert.myAssertFalse("没有传进来一个符合类型变量,实际传进来的泛型的实际类型是:"+viewTrial.getClass().getName());
		
		} catch (Exception e) {
			
			e.printStackTrace();
			MyAssert.myAssertFalse("程序处理问题，对象转换错误,传进来的泛型的实际类型是:"+viewTrial.getClass().getName());
		}
	}
	
	/**
	 * 回退提交后，原节点登陆再次提交到下个节点
	 * @param selenium
	 * @param testDataBean
	 * @param basic
	 * @param leftSubMenuEleKey	左侧导航栏的子菜单
	 * @param rightIframeEle	右侧的iframe
	 * @param nodeName  节点名称
	 * @param viewTrial 某节点的功能的对象
	 */
	public static <T> void goBackAfterReSubmit(T viewTrial,SeleniumUtil selenium,TestDataModel testDataBean,BasicTestDataBean basic){
		
		String leftSubMenuEleKey = ReadJsonBase.readJson(testDataBean.getTestData(), "$.data.current_node").trim();
		
		String rightIframeEle = ReadJsonBase.readJson(testDataBean.getTestData(), "$.data.current_iframe").trim();
		
		leftNavigation(selenium, leftSubMenuEleKey, rightIframeEle, testDataBean.getDescription());
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontWaitLable), "待处理标签");
		
		pageQuery(selenium,basic.getApplyNum());
		
		try {
	
			if(viewTrial instanceof FirstTrialService){
				
				selenium.dclick(selenium.getWebElement(carLoanFrontFirstReview.replace("订单号", basic.getApplyNum())), "审核  处理");
				((FirstTrialService) viewTrial).carSysFirstTrial();
				firstFinalTrialPass(selenium, basic.getApplyNum());
			}
			
			else if(viewTrial instanceof FinallyTrialService){

				selenium.dclick(selenium.getWebElement(carLoanFrontFinalReview.replace("订单号", basic.getApplyNum())), "审核 处理");
				((FinallyTrialService) viewTrial).carSysFinallyTrial();
				firstFinalTrialPass(selenium, basic.getApplyNum());
			}
	
			else if(viewTrial instanceof HeadquartersEvaluateService){

				selenium.dclick(selenium.getWebElement(carLoanFrontHQReview.replace("订单号", basic.getApplyNum())), "评估  处理");
				((HeadquartersEvaluateService) viewTrial).headquartersEvaluateSubmitOpera(basic.getApplyNum());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			MyAssert.myAssertFalse("程序处理问题，对象转换错误");
		}
	}
	
	/**已处理队列 -检查提交走的记录是否在已处理队列中**/
	public static void alreadProcessQueue(SeleniumUtil selenium,String applyNum){
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontAlreadyLable), "已处理队列项");

		selenium.cleanAndInput(selenium.getWebElement(carLoanFrontSearchInputBox), applyNum, "已处理搜索框-订单号");
		
		selenium.loopClickAssertion(selenium.getElement(carLoanFrontSearchBtn),"搜索按钮",checkSearchResult);

	}
	
	/**
	 * 处理队列的搜索
	 * @param selenium
	 * @param applyNum
	 * @param searchInputBoxEle	搜索框的元素定位表达式
	 */
	public static void processQueueSearch(SeleniumUtil selenium,String applyNum,String searchInputBoxEle){

		selenium.jsClick(selenium.getWebElement(carLoanFrontAlreadyLable), "已处理队列项");
		
		SeleniumUtil.sleep(1);
		
		selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue(searchInputBoxEle)), "搜索输入框");
		
		SeleniumUtil.sleep(1);
		
		selenium.cleanAndInput(selenium.getWebElement(carElementProperty.getproperValue(searchInputBoxEle)), applyNum, "已处理搜索框-订单号");
		
	}
	
	/**
	 * 1.当前节点 回退操作
	 * 2.退出当前节点登陆
	 * 3.登陆被回退到的节点
	 * 4.直接将订单提交走
	 * 5.退出被回退的节点登陆
	 * @param selenium	
	 * @param applyNum	订单号
	 * @param testData	测试数据
	 * @param backRole	回退到的那个角色用户
	 * @param subMenuElement	左侧导航栏的子菜单-回退到节点名称的元素(元素属性里得键)
	 * @param iframe	回退到节点名称的待处理队列iframe元素(元素属性里得键)
	 * @param subMenuDescripet	回退到节点名称的描述
	 */
	public static <T> void  goBackOpera(T viewTrial, SeleniumUtil selenium,String applyNum,String testData){
		
		String backNodeElementKey = ReadJsonBase.readJson(testData, "$.data.back_node");
		String mainReason = ReadJsonBase.readJson(testData, "$.data.main_reason");
		String subReason = ReadJsonBase.readJson(testData, "$.data.sub_reason");
		String remark = ReadJsonBase.readJson(testData, "$.data.remark");
		
		String subMenuElementKey = ReadJsonBase.readJson(testData, "$.data.other_node").trim();
		String iframe = ReadJsonBase.readJson(testData, "$.data.other_iframe").trim();
		String subMenuDescripet =ReadJsonBase.readJson(testData, "$.data.other_node_info").trim();
		
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontGoBackBtn), "回退按钮");
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontGoBackNodeSelect), "回退节点下拉");
		selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue(backNodeElementKey)), "回退到 "+subMenuDescripet+" 节点");
	
		selenium.jsClick(selenium.getWebElement(carLoanFrontGoBackMainReasonSelect), "主原因下拉");
		selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue(mainReason)), "主原因");
		
		selenium.input(selenium.getWebElement(carLoanFrontRemark), remark, "回退的备注信息");
		
		if(!subReason.equals("0")){//subReason为0，则不需要操作子原因
			
			selenium.jsClick(selenium.getWebElement(carLoanFrontGoBackSubReasonSelect), "子原因下拉");
			selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue(subReason)), "子原因");
			selenium.dclick(selenium.getWebElement(carLoanFrontGoBackConfirmContainsText), "确认按钮");
		}
		else{
			selenium.dclick(selenium.getWebElement(carLoanFrontGoBackConfirmBtn), "确认按钮");
		}

		int i = 0;
		while(!selenium.getDriver().getPageSource().contains(checkBackSucess) && selenium.getDriver().getPageSource().contains(checkOpreaSucess)){
				
			if(i>7){
					MyAssert.myAssertFalse("回退操作出现异常，见截图");
				}
			SeleniumUtil.sleep(0.7);
			i++;
		}
		
		alreadProcessQueue(selenium,applyNum);
		
		logger.info("订单回退操作结束");
		
		leftNavigation(selenium, subMenuElementKey, iframe, subMenuDescripet);
		
		pendingQueueDirectProcessOrder(viewTrial,selenium, applyNum,subMenuDescripet);

		logger.info("回退的节点-"+subMenuDescripet+":对订单进行提交操作结束");
	}
	
	/**
	 * 初审/终审  拒绝操作
	 * @param selenium
	 * @param applyNum	订单号
	 * @param rejectNode	哪个节点进行拒绝操作
	 * @param checkText	终审  拒绝/复议拒绝
	 */
	public static void rejectOpera(SeleniumUtil selenium,String applyNum,String rejectNode,String checkText){
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectBtn), "拒绝");
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectMainReasonSelect), "主原因下拉");
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectMainReason), "主原因-不符合进件要求");
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectSubReasonSelect), "子原因下拉");
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectSubReason), "子原因-申请人");
		selenium.dclick(selenium.getWebElement(carLoanFrontRejectConfimBtn), "确认按钮");
		
		int i = 0;
		while(selenium.getDriver().getPageSource().contains(checkOpreaSucess)){
				
			if(i>7){
					MyAssert.myAssertFalse("拒绝操作出现异常，见截图");
				}
			SeleniumUtil.sleep(0.7);
			i++;
		}
		
		alreadProcessQueue(selenium, applyNum);

		if(rejectNode.equals("终审拒绝")){
			
			String firstTrialResult = selenium.getText(selenium.getWebElement(carLoanFrontFirstTrialResultTag));
			String finalTrialResult = selenium.getText(selenium.getWebElement(carLoanFrontFinalTrialResultTag));
			
			MyAssert.myAssertEquals(firstTrialResult,"无权限拒绝");
			MyAssert.myAssertEquals(finalTrialResult, checkText);
		}

	}
	
	/**
	 * 取消，拒绝操作--主原因，子原因，备注  信息
	 * @param selenium	
	 * @param mainReason	主原因(元素定位的属性文件里的key)
	 * @param subCause		子原因(元素定位的属性文件里的key)
	 */
	public static void canceRejectOperat(SeleniumUtil selenium,String mainReason,String subCause){
	
		String mainReasonKey = carElementProperty.getproperValue(mainReason);
		String subCauseKey = carElementProperty.getproperValue(subCause);
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectMainReasonSelect), "主原因下拉");
		selenium.jsClick(selenium.getWebElement(mainReasonKey), mainReasonKey.split("=")[1].replace("'", ""));
		KeyBoard.enter();
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontRejectSubReasonSelect), "子原因下拉");
		selenium.jsClick(selenium.getWebElement(subCauseKey), subCauseKey.split("=")[1].replace("'", ""));
		KeyBoard.enter();
		
		selenium.input(selenium.getWebElement(carLoanFrontRemark), "备注啦啦啦啦啦啦", "备注");
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontCanceRejectConfirmText), "确认按钮");
		
	}
	
	/**
	 * 初审结点的 流程监控，订单的重新分配 <处理订单被其他业务员获走的情况>
	 * @param selenium
	 * @param orderNo	订单号	
	 * @param salesman	要分配到的业务员姓名代号
	 */
	public static boolean processMonitoring(SeleniumUtil selenium,String orderNo,String salesmanCode){
		
		selenium.switchPrentFrame("D");
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontProcessListener), "流程监控");
		
		processMuiltISameIframe(selenium,"重置","流程监控");

		searchAssignOperat(selenium,orderNo, salesmanCode);
		
		return true;
		
	}
	
	/**
	 * 贷前合同审核执行通过,则执行 放款、还款2期操作,并且生产客户信息的properties文件
	 * @param productName	产品名称
	 * @param basic
	 * @param module	模块名称
	 * @param testResult	合同审核用例执行结果
	 */
	public static void loanRepayment(String productName,BasicTestDataBean basic,String module){
			
		if(productName.equals("GPS3期-1.98") || productName.equals("移交3期-1.90") || productName.equals("分期36期D")){
			
			if(module.equals("车贷进件")){

				execLoanRepayment(basic, "carLoanApply.properties");
				
			}
			else if(module.equals("车贷(初审/终审)实地调查")){
				
				execLoanRepayment(basic, "fieldSurvey.properties");
			}
		}
	}
	
	/**初审/终审 通过**/
	public static void firstFinalTrialPass(SeleniumUtil selenium,String applyNum){
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontTrialPass), "通过按钮");
		
		selenium.dclick(selenium.getWebElement(carLoanFrontTrialPassConfirm),checkPointProperty.getproperValue("oprea_sucess"), "确认");
		
		alreadProcessQueue(selenium,applyNum);
	}
	
	/**初审/终审 提交实地调查,并  实地分配  和 进行  实地调查 **/
	public static void firstTrialFieldSurvey(SeleniumUtil selenium,String applyNum,TestDataModel testData,String loginRole){
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurveyItem), "实地调查项");
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurveySubmit), "提交实地调查");
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurveyUnit), "实地调查工作单位");
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurveyLive), "实地调查居住地");
		
		if(testData.getDescription().equals("初审实地调查")){
			
			selenium.input(selenium.getWebElement(carLoanFrontFieldSurveyRemark), "要进行实地调查了哦，警戒中", "实地调查备注内容");
		}
		else{
			selenium.input(selenium.getWebElement(carLoanFrontFieldSurveyRemarkFinalReview.replace("订单号", applyNum)), "要进行实地调查了哦，警戒中", "实地调查备注内容");
		}
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurveySubmitDetermine), "提交实地调查的 确定  按钮");
	
		alreadProcessQueue(selenium,applyNum);
		
		fieldAssign(selenium,applyNum,loginRole);//实地分配
		
		LoginBase login =new LoginBase();
		login.carLoanSysLoginOut(selenium);
		login.carLoanNodeNameLogin(selenium, loginRole,false);
		
		wirteFieldSurveyInfo(selenium,applyNum,testData,loginRole);//实地调查
		
	}
	
	/**
	 * 处理多个 class=table_iframe的页面跳转
	 * @param selenium
	 * @param assertText	跳转iframe的判断依据的文本
	 * @param logInfo 节点跳转失败的日志信息
	 */
	public static void processMuiltISameIframe(SeleniumUtil selenium,String assertText,String logInfo){
		
		List<WebElement> muiltWebElements = selenium.getMuiltWebElements(iframeProperty.getproperValue("tab_iframe"));

		int i= 0;
		for(;i<muiltWebElements.size();i++){
			
			try{
				selenium.getDriver().switchTo().frame(muiltWebElements.get(i));
				
			}catch(Exception e){
				logger.info("没有跳转到正确的iframe里，继续跳转iframe");

			}
			logger.info("跳转到正确的iframe里了");
			
			boolean flag = MyAssert.myAssertTrue(selenium.getDriver(), assertText);
			System.out.println("flag："+flag);
			if(true == flag){
				break;
			}
		}
		
		if(i>=muiltWebElements.size()){
			MyAssert.myAssertFalse(logInfo+"节点 - 跳转iframe失败");
		}
	}
	
	/**实地分配**/
	private static void fieldAssign(SeleniumUtil selenium,String applyNum,String loginRole){
		
		selenium.switchPrentFrame("D");
		
		selenium.refreshPage();
		
		selenium.click(selenium.getWebElement(carLoanFrontMenuLable), "车贷贷前菜单");
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldAssign),"实地分配");
				
		processMuiltISameIframe(selenium,"分配状态","实地分配");

		searchAssignOperat(selenium,applyNum,loginRole);
		
	}
	
	/**实地调查信息填写和调查结果的提交**/
	private static void wirteFieldSurveyInfo(SeleniumUtil selenium,String applyNum,TestDataModel testDataBean,String loginRole){
		
		LoginBase loginBase = new LoginBase();
		
		loginBase.carLoanSysLoginOut(selenium);
		
		loginBase.carLoanNodeNameLogin(selenium,loginRole,false);
		
		selenium.click(selenium.getElement(carLoanFrontMenuLable), "车贷贷前");
		
		String testData = testDataBean.getTestData();
		String checkPoint = testDataBean.getCheckPoint();

		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurvey),"实地调查");
		
		selenium.switchFrameExec(iframeProperty.getproperValue("car_iframe_112103"));
		
		selenium.input(selenium.getWebElement(carLoanFrontSearchInputBox), applyNum, "订单搜索框："+applyNum);
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldProcess), "实地处理");

		selenium.clickInput(selenium.getWebElement(carLoanFrontFieldCompanyDescribe), ReadJsonBase.readJson(testData, "$.data.unit_descri"), "单位情况描述");
		selenium.clickInput(selenium.getWebElement(carLoanFrontFieldLiveDescribe), ReadJsonBase.readJson(testData, "$.data.live_descri"), "居住情况描述");
		selenium.clickInput(selenium.getWebElement(carLoanFrontFieldHouseDescribe), ReadJsonBase.readJson(testData, "$.data.house_descri"), "房产情况描述");
		
		selenium.jsClick(selenium.getWebElement(carLoanFrontFieldSurveySave), "实地调查保存按钮");
		
		String assignResult = ReadJsonBase.readJson(testData, "$.data.survey_result");
		
		SeleniumUtil.sleep(1);
		
		if("提交".equals(assignResult)){
			
			selenium.loopClickAssertion(selenium.getWebElement(carLoanFrontEvaluateSubmit), "实地调查-"+assignResult+" 按钮", "操作确认");
			
			selenium.loopClickAssertion(selenium.getWebElement(carLoanFrontTrialPassConfirm), "确认按钮", checkPointProperty.getproperValue("check_submit_sucess"));
		}
		else if("取消".equals(assignResult)){
			
			selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue("cance")), "实地调查-"+assignResult+" 按钮");
			canceRejectOperat(selenium,ReadJsonBase.readJson(testData, "$.data.main_reason"),ReadJsonBase.readJson(testData, "$.data.sub_cause"));
		}
		else if("拒绝".equals(assignResult)){
			
			selenium.jsClick(selenium.getWebElement(carElementProperty.getproperValue("reject")), "实地调查-"+assignResult+" 按钮");
			canceRejectOperat(selenium,ReadJsonBase.readJson(testData, "$.data.main_reason"),ReadJsonBase.readJson(testData, "$.data.sub_cause"));
		}
		else {
			
			MyAssert.myAssertFalse("测试数据里没有找到订单是提交还是取消还是拒绝");
		}
		
		processQueueSearch(selenium,applyNum,"already_search_order_no");
		selenium.loopClickAssertion(selenium.getElement(carLoanFrontSearchBtn),"搜索按钮", ReadJsonBase.readJson(checkPoint, "$.search_result"));
	}

	private static void execLoanRepayment(BasicTestDataBean basic,String propertiesFileName){
		
		FileProcessUtils.createPropertiesFile(basic, propertiesFileName);
		
		new SqlUtil().carLoanrepayment(basic.getApplyNum());
	}
}
