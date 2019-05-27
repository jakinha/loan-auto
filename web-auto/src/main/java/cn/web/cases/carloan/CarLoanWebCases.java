package cn.web.cases.carloan;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.web.base.ReadJsonBase;
import cn.web.base.TestngBase;
import cn.web.common.TaskSchedulingCenter;
import cn.web.common.carloan.FileProcessUtils;
import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.dao.sql.SqlUtil;
import cn.web.data_provider.car_loan.CarLoanExtensionDataProvider;
import cn.web.data_provider.car_loan.CarLoanFrontDataProvider;
import cn.web.dto.TestDataModel;
import cn.web.service.car_loan_extension_service.ApplyExtensionService;
import cn.web.service.car_loan_service.ChangBankCardService;
import cn.web.service.car_loan_service.ContractReviewService;
import cn.web.service.car_loan_service.DataReviewService;
import cn.web.service.car_loan_service.FinallyTrialService;
import cn.web.service.car_loan_service.FirstTrialService;
import cn.web.service.car_loan_service.GPSInstallService;
import cn.web.service.car_loan_service.HeadquartersEvaluateService;
import cn.web.service.car_loan_service.IntegratedQueryService;
import cn.web.service.car_loan_service.LocalEvaluateService;
import cn.web.service.car_loan_service.PendingLoanCanceService;
import cn.web.service.car_loan_service.ProcessMonitoringService;
import cn.web.service.car_loan_service.ReconsiderationService;
import cn.web.service.car_loan_service.StoreEntryService;
import cn.web.service.car_loan_service.StoreSigningService;
import cn.web.util.MyAssert;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;


/**
 * 车 贷 用 例
 * @author huangjun
 *
 */
public class CarLoanWebCases extends TestngBase{
	
	public Logger logger = Logger.getLogger(CarLoanWebCases.class);
	
	@Test(description="门店录入",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void storeEntry(TestDataModel testDataBean){

		StoreEntryService storeEntryService = new StoreEntryService(testDataBean,selenium,basic);

		storeEntryService.carSysStoreEntry();
		storeEntryService.storeSubmitOpera( basic.getApplyNum(),"提交");
		

	}
	
	@Test(description="门店录入-加急",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void expeditedItem(TestDataModel testDataBean){

		StoreEntryService storeEntryService = new StoreEntryService(testDataBean,selenium,basic);

		storeEntryService.carSysStoreEntry();
		storeEntryService.storeSubmitOpera( basic.getApplyNum(), "加急");

	}
	
	@Test(description="门店录入-未婚状态单子更新验证",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void storeEntryNoMarryCheck(TestDataModel testDataBean){

		StoreEntryService storeEntryService = new StoreEntryService(testDataBean,selenium,basic);

		storeEntryService.carSysStoreEntryUnMarry("未婚");
		storeEntryService.storeSubmitOpera( basic.getApplyNum(), "取消");
		
	}
	
	@Test(description="门店录入-车架号在途件拦截",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void carFrameAtWayFilter(TestDataModel testDataBean){
		
		new SqlUtil().carLoanrepayment(basic.getApplyNum());//放款、还款
		
		StoreEntryService storeEntryService = new StoreEntryService(testDataBean,selenium,basic);

		storeEntryService.checkCarFrameAtWay();

	}
	
	@Test(description="门店录入-车架号不被拦截",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void carFrameAtWayNotFilter(TestDataModel testDataBean){

		StoreEntryService storeEntryService = new StoreEntryService(testDataBean,selenium,basic);

		storeEntryService.checkCarFrameAtWayNotFilter();

	}
	

	@Test(description="本地评估",dataProvider="localEvaluateData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void localEvaluate(TestDataModel testDataBean){

		LocalEvaluateService localEvaluateService = new LocalEvaluateService(testDataBean,selenium,basic);

		localEvaluateService.carSysLocalEvaluatePendingQueue();
		localEvaluateService.localEvaluateCarInfo("y");
		localEvaluateService.localEvaluateReport();
		
		localEvaluateService.localEvaluateSubmitOpera();
	}
	
	@Test(description="本地评估车300估价逻辑",dataProvider="car300LogicData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void car300Logic(TestDataModel testDataBean){

		LocalEvaluateService localEvaluateService = new LocalEvaluateService(testDataBean,selenium,basic);

		localEvaluateService.carSysLocalEvaluatePendingQueue();
		localEvaluateService.localEvaluateCarInfo("n");
		localEvaluateService.localEvaluateReport();
		
		localEvaluateService.localEvaluateSubmitOperaFailPrompt(basic.getApplyNum(),"prompt_info");//未进行车300估价
		
		localEvaluateService.car300Logic();
	
		localEvaluateService.localEvaluateSubmitOperaFailPrompt(basic.getApplyNum(),"prompt_info1");//本地估价大于车300估价

	}
	
	@Test(description="本地评估取消",dataProvider="localEvaluateCanceData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void localEvaluateCance(TestDataModel testDataBean){

		LocalEvaluateService localEvaluateService = new LocalEvaluateService(testDataBean,selenium,basic);

		localEvaluateService.carSysLocalEvaluatePendingQueue();
		
		localEvaluateService.localEvaluateCanceOpera();

	}
	
	@Test(description="总部评估",dataProvider="headquartersEvaluateData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void headquartersEvaluate(TestDataModel testDataBean){

		HeadquartersEvaluateService headquartersEvaluateService = new HeadquartersEvaluateService(testDataBean,selenium,basic);

		headquartersEvaluateService.carSysHeadquartersEvaluate(testDataBean.getTestData());
		
		headquartersEvaluateService.headquartersEvaluateSubmitOpera(basic.getApplyNum());
	}
	
	//总部回退->本地评估->本地提交->总部提交
	@Test(description="总部评估回退本地评估",dataProvider="headquartersEvaluateData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void headquartersEvaluateGoBack(TestDataModel testDataBean){
		
		HeadquartersEvaluateService headquartersEvaluateService = new HeadquartersEvaluateService(testDataBean, selenium, basic);
		LocalEvaluateService localEvaluateService = new LocalEvaluateService(selenium, basic);

		headquartersEvaluateService.carSysHeadquartersEvaluate(testDataBean.getTestData());
		
		LoanFrontPubOperat.goBackOpera(localEvaluateService,selenium,basic.getApplyNum(),testDataBean.getTestData());

		logger.info("总部评回退到本地评估成功,本地评估提交完成,总部评估再次进行提交操作");
		LoanFrontPubOperat.goBackAfterReSubmit(headquartersEvaluateService,selenium,testDataBean,basic);

	}
	
	@Test(description="初审",dataProvider="firstTrialData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void firstTrial(TestDataModel testDataBean){
		
		FirstTrialService firstTrialService = new FirstTrialService(testDataBean,selenium,basic);

		firstTrialService.carSysFirstTrialPendingQueue();
		firstTrialService.carSysFirstTrial();
		
		LoanFrontPubOperat.firstFinalTrialPass(selenium, basic.getApplyNum());
		
	}
	
	@Test(description="初审实地调查",dataProvider="firstTrialFieldSurveyData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void firstTrialFieldSurvey(TestDataModel testDataBean){
		
		FirstTrialService firstTrialService = new FirstTrialService(testDataBean, selenium, basic);
		
		firstTrialService.carSysFirstTrialPendingQueue();
			
		LoanFrontPubOperat.firstTrialFieldSurvey(selenium, basic.getApplyNum(), testDataBean,basic.getLoginRule());
		
		//初审实地调查结束后，初审提交走
		loginBase.carLoanSysLoginOut(selenium);
		loginBase.carLoanLogin(selenium,basic);
		
		firstTrialService.carSysFirstTrialPendingQueue();
		SeleniumUtil.sleep(1);
		firstTrialService.carSysFirstTrial();
		LoanFrontPubOperat.firstFinalTrialPass(selenium, basic.getApplyNum());

	}
	
	@Test(description="初审拒绝",dataProvider="firstTrialRejectData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void firstTrialReject(TestDataModel testDataBean){

		FirstTrialService firstTrialService = new FirstTrialService(testDataBean, selenium, basic);
		
		firstTrialService.carSysFirstTrialPendingQueue();
		firstTrialService.carSysFirstTrial();
		
		LoanFrontPubOperat.rejectOpera(selenium, basic.getApplyNum(),testDataBean.getDescription(),"");
		
	}
	
	@Test(description="初审回退总部评估",dataProvider="firstTrialBackData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void firstTrialGoBack(TestDataModel testDataBean){

		FirstTrialService firstTrialService = new FirstTrialService(testDataBean, selenium, basic);
		HeadquartersEvaluateService headquartersEvaluateService = new HeadquartersEvaluateService(selenium, basic);
		
		firstTrialService.carSysFirstTrialPendingQueue();
		firstTrialService.carSysFirstTrial();
		
		LoanFrontPubOperat.goBackOpera(headquartersEvaluateService,selenium,basic.getApplyNum(),testDataBean.getTestData());
		
		logger.info("初审回退到总部评估成功,总部评估提交完成,初审再次进行提交操作");
		LoanFrontPubOperat.goBackAfterReSubmit(firstTrialService,selenium,testDataBean,basic);

	}
	
	@Test(description="流程监控分配-初审提交",dataProvider="firstTrialData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void processMonitoringAssign(TestDataModel testDataBean){

		FirstTrialService firstTrialService = new FirstTrialService(testDataBean, selenium, basic);
		
		new ProcessMonitoringService(testDataBean, selenium, basic).processMonitoringReAssign(basic.getApplyNum(), basic.getLoginRule());
		
		//退出- 登陆被分配人进行初审提交
		loginBase.carLoanSysLoginOut(selenium);
		loginBase.carLoanNodeNameLogin(selenium,basic.getLoginRule(),false);
	
		firstTrialService.carSysAssignFirstTrialPendingQueue();
		firstTrialService.carSysFirstTrial();
		
		LoanFrontPubOperat.firstFinalTrialPass(selenium, basic.getApplyNum());
		
	}
	
	@Test(description="终审",dataProvider="finallyTrialData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void finallyTrial(TestDataModel testDataBean){

		FinallyTrialService finallyTrialService = new FinallyTrialService(testDataBean,selenium,basic);

		finallyTrialService.carSysFinalTrialPendingQueue();
		finallyTrialService.carSysFinallyTrial();
		
		LoanFrontPubOperat.firstFinalTrialPass(selenium, basic.getApplyNum());
	}

	@Test(description="终审实地调查",dataProvider="finalTrialFieldSurveyData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void finalTrialFieldSurvey(TestDataModel testDataBean){
		
		FinallyTrialService finallyTrialService = new FinallyTrialService(testDataBean, selenium, basic);

		finallyTrialService.carSysFinalTrialPendingQueue();

			
		LoanFrontPubOperat.firstTrialFieldSurvey(selenium, basic.getApplyNum(), testDataBean,basic.getLoginRule());

		//终审实地调查结束后，终审提交走
		loginBase.carLoanSysLoginOut(selenium);
		loginBase.carLoanLogin(selenium,basic);
		
		finallyTrialService.carSysFinalTrialPendingQueue();
		SeleniumUtil.sleep(1);
		finallyTrialService.carSysFinallyTrial();
		LoanFrontPubOperat.firstFinalTrialPass(selenium, basic.getApplyNum());
	}
	
	@Test(description="终审拒绝",dataProvider="finallyTrialData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void finalTrialReject(TestDataModel testDataBean){

		FinallyTrialService finallyTrialService = new FinallyTrialService(testDataBean, selenium, basic);
		
		finallyTrialService.carSysFinalTrialPendingQueue();
		
		LoanFrontPubOperat.rejectOpera(selenium, basic.getApplyNum(),testDataBean.getDescription(),ReadJsonBase.readJson(testDataBean.getTestData(), "$.data.final_approval_reject"));

	}
	
	@Test(description="终审复议拒绝",dataProvider="finallyTrialData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void finalTrialReconsiderationReject(TestDataModel testDataBean){

		FinallyTrialService finallyTrialService = new FinallyTrialService(testDataBean, selenium, basic);

		finallyTrialService.carSysFinalTrialPendingQueue();
		
		LoanFrontPubOperat.rejectOpera(selenium, basic.getApplyNum(),ReadJsonBase.readJson(testDataBean.getTestData(), "$.data.final_approval_reject"),"复议拒绝");
	}

	@Test(description="终审回退初审再提交",dataProvider="finallyTrialBackData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void finallyTrialGoBcakAndSubmit(TestDataModel testDataBean){

		FinallyTrialService finallyTrialService = new FinallyTrialService(testDataBean, selenium, basic);
		FirstTrialService firstTrialService = new FirstTrialService(testDataBean, selenium, basic);

		finallyTrialService.carSysFinalTrialPendingQueue();
		finallyTrialService.carSysFinallyTrial();
		
		LoanFrontPubOperat.goBackOpera(firstTrialService,selenium,basic.getApplyNum(),testDataBean.getTestData());
		
		logger.info("终审准备对回退又提交回来的订单再次进行提交操作");
		
		LoanFrontPubOperat.goBackAfterReSubmit(finallyTrialService,selenium,testDataBean,basic);

	}
	
	@Test(description="GPS安装",dataProvider="installGPSData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void installGPS(TestDataModel testDataBean){

		GPSInstallService gpsInstallService = new GPSInstallService(testDataBean,selenium,basic);
		
		gpsInstallService.gpsPendingQueue();
		gpsInstallService.wirteGPSInfoSubmit();
	}
	
	@Test(description="门店签约",dataProvider="storeSigningData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void storeSigning(TestDataModel testDataBean){
		
		StoreSigningService storeSigningService = new StoreSigningService(testDataBean,selenium,basic);

		storeSigningService.carSysStoreSigningPendingQueue();
		storeSigningService.carSysStoreSigningProcess();
		storeSigningService.storeSigningSubmitOpera(selenium, basic.getApplyNum());
		
	}
	
	@Test(description="门店签约-更改产品",dataProvider="storeSigningData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void updateProduct(TestDataModel testDataBean){

		StoreSigningService storeSigningService = new StoreSigningService(testDataBean,selenium,basic);

		storeSigningService.carSysStoreSigningPendingQueue();

		storeSigningService.updateSiginProduct();
		
		storeSigningService.storeSigningSubmitOpera(selenium, basic.getApplyNum());
		
		loginBase.carLoanLoginOutAngLogin(selenium,basic);
		firstTrial(testDataBean);
		
		loginBase.carLoanLoginOutAngLogin(selenium,basic);
		finallyTrial(testDataBean);
	}
	
	@Test(description="门店签约--降额度",dataProvider="storeSigningData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void updateLowerAmount(TestDataModel testDataBean){
		
		StoreSigningService storeSigningService = new StoreSigningService(testDataBean,selenium,basic);
		
		storeSigningService.carSysStoreSigningPendingQueue();

		storeSigningService.updateLoanAmount(testDataBean,"降额");
		
		storeSigningService.carSysStoreSigningProcess();
		
		storeSigningService.storeSigningSubmitOpera(selenium, basic.getApplyNum());
		
	}
	
	@Test(description="门店签约--提额度",dataProvider="storeSigningData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void updateHoistAmount(TestDataModel testDataBean){

		StoreSigningService storeSigningService = new StoreSigningService(testDataBean,selenium,basic);
		
		storeSigningService.carSysStoreSigningPendingQueue();

		storeSigningService.updateLoanAmount(testDataBean,"提额");
		
		storeSigningService.storeSigningSubmitOpera(selenium, basic.getApplyNum());
		
		loginBase.carLoanLoginOutAngLogin(selenium,basic);
		finallyTrial(testDataBean);
		
	}
	
	@Test(description="门店签约直接提交-处理页在前面已经填写过",dataProvider="storeSigningData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void storeSigningSubmit(TestDataModel testDataBean){
		
		StoreSigningService storeSigningService = new StoreSigningService(testDataBean,selenium,basic);
		
		storeSigningService.carSysStoreSigningPendingQueue();
		
		storeSigningService.storeSigningSubmitOpera(selenium, basic.getApplyNum());
	}
	
	@Test(description="资料审核",dataProvider="dataReviewData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void dataReview(TestDataModel testDataBean){
		
		DataReviewService dateReviewService = new DataReviewService(testDataBean,selenium,basic);
		
		dateReviewService.carSysDataReviewPendingQueue();
		
		dateReviewService.dataViewSubmitOpera(selenium, basic.getApplyNum());
	}
	
	@Test(description="资料审核回退",dataProvider="dataReviewData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void dataReviewBack(TestDataModel testDataBean){
		
		DataReviewService dataReviewService = new DataReviewService(testDataBean, selenium, basic);

		dataReviewService.carSysDataReviewPendingQueue();
		dataReviewService.dataViewBackOpera(selenium);
	}
	
	@Test(description="合同审核",dataProvider="contractReviewData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void contractReview(TestDataModel testDataBean){
		
		ContractReviewService contractReviewService = new ContractReviewService(testDataBean,selenium,basic);
		
		contractReviewService.contractReviewPendingQueue();
		
		contractReviewService.contractReviewSubmitOpera(selenium, basic.getApplyNum());

	}
	
	@Test(description="流程监控/综合查询/厦金中心查询-合同状态和详情页头信息",dataProvider="integratedQueryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void contractStatusHeadInfo(TestDataModel testDataBean){
		
		String nullStr = "null";
		String contractStatus = "0";
		
		ProcessMonitoringService processMonitoringService = new ProcessMonitoringService(testDataBean, selenium, basic);
		
		IntegratedQueryService integratedQueryService = new IntegratedQueryService(testDataBean, selenium, basic);
		
		//待放款之前
		processMonitoringService.processMonitoringContractStatusHeadInfo(nullStr);
		
		integratedQueryService.integratedQueryContractStatus(nullStr);
		
		integratedQueryService.xiaJinCenterQuery(nullStr);
		
		new SqlUtil().carLoanrepayment(basic.getApplyNum());//放款还款
		
		//放款之后
		processMonitoringService.processMonitoringContractStatusHeadInfo(contractStatus);
		
		integratedQueryService.integratedQueryContractStatus(contractStatus);
		
		integratedQueryService.xiaJinCenterQuery(contractStatus);
		
	}
	
	@Test(description="结清和逾期",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void settlementOverdue(TestDataModel testDataModel){
		
		StoreEntryService storeEntryService = new StoreEntryService(testDataModel,selenium,basic);
		
		ProcessMonitoringService processMonitoringService = new ProcessMonitoringService(testDataModel, selenium, basic);
		
		SqlUtil sql = new SqlUtil();
		
		
		String orderNo =  basic.getApplyNum();
		
		sql.carLoanrepayment(orderNo);//放款、还款

		for(int i = 0;i<6;i++){
			
			if(i == 0){//逾期
				
				sql.setContractStatus(orderNo,(i+1));
				processMonitoringService.processMonitoringContractStatusHeadInfo(String.valueOf(i+1));
				storeEntryService.checkCarFrameAtWay();//basic里订单号已经变了
				
				basic.setApplyNum(orderNo);
			}
			else {//结清
				if(i == 1){
					
					sql.setCarLoanRepaymentDataContractStatus("10");
					
					//车贷贷后任务调度,合同变更通知到贷前
					new TaskSchedulingCenter().carLaonAfterTaskScheduling(System.getProperty("testEnv"));
				}
				else {
					
					sql.setContractStatus(basic.getApplyNum(),(i*10));
				}
				
				processMonitoringService.processMonitoringContractStatusHeadInfo(String.valueOf((i*10)));
				storeEntryService.checkCarFrameAtWayNotFilter();
				
				basic.setApplyNum(orderNo);
				
				//删除录入单子,防止车架号在途
				sql.deleteEntryOrder(basic.getCarFrameNumber());
			}
		}
	}
	
	@Test(description="待放款-合同取消")
	public void pendLoanContractCance(){

		new PendingLoanCanceService(selenium,basic).contractCance();
	}
	
	@Test(description="更换银行卡",dataProvider="changBankCardData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void changBankCard(TestDataModel testDataBean){

		ChangBankCardService changBankCardService = new ChangBankCardService(testDataBean,selenium,basic);
		
		changBankCardService.changBankCardService(basic.getApplyNum());
	}
	
	@Test(description="门店复核",dataProvider="storeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void storeReconsideration(TestDataModel testDataBean){

		ReconsiderationService reconsiderationService = new ReconsiderationService(testDataBean,selenium,basic);

		reconsiderationService.carSysReconsideration();
	}
	
	@Test(description="初审/终审-回退申请录入",dataProvider="reviewBackStroeEntryData",dataProviderClass=CarLoanFrontDataProvider.class)
	public void reviewBackStroeEntry(TestDataModel testDataBean){

		FirstTrialService firstTrialService = new FirstTrialService(testDataBean, selenium, basic);
		StoreEntryService storeEntryService = new StoreEntryService(testDataBean,selenium,basic);
		FinallyTrialService finallyTrialService = new FinallyTrialService(testDataBean, selenium, basic);
		
		firstTrialService.carSysFirstTrialPendingQueue();
		firstTrialService.carSysFirstTrial();
		LoanFrontPubOperat.goBackOpera(storeEntryService,selenium,basic.getApplyNum(),testDataBean.getTestData());
		
		logger.info("初审回退到门店录入成功,门店录入提交完成,初审再次进行提交操作");
		LoanFrontPubOperat.goBackAfterReSubmit(firstTrialService,selenium,testDataBean,basic);
		
		finallyTrialService.carSysFinalTrialPendingQueue();
		LoanFrontPubOperat.goBackOpera(storeEntryService,selenium,basic.getApplyNum(),testDataBean.getTestData());
		logger.info("终审回退到门店录入成功,门店录入提交完成,初审再次进行提交操作");

	}

	
///////////////////////////////////////////// 展   期   /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test(description="申请展期-搜索",dataProvider="applyExtensionSearch",dataProviderClass=CarLoanExtensionDataProvider.class)
	public void applyExtensionSearch(TestDataModel testDataModel){
		
		basic.setApplyNum("H014201904120001");
		basic.setClientName("王信东");
		basic.setIdCard("451002199211147220");
		basic.setCarFrameNumber("KH123456789432061");
		basic.setTrialMoney("50000");
		
		ApplyExtensionService applyExtensionService = new ApplyExtensionService(testDataModel,selenium,basic);
		
		applyExtensionService.search(basic);
	}
	
	@Test(description="申请展期",dataProvider="applyExtensionSubmit",dataProviderClass=CarLoanExtensionDataProvider.class)
	public void applyExtensionSubmit(TestDataModel testDataModel){
		
		ApplyExtensionService applyExtensionService = new ApplyExtensionService(testDataModel,selenium,basic);
		
		applyExtensionService.applyExtension();
		
		applyExtensionService.search(basic);
	}
	
	

/********************************************************************************************************************************************************************/	
	@BeforeTest
	public void beforMethod(){
		
		basic.setCarFrameNumber("KH123456789"+RandomUtil.randomNum(6));
		
		selenium=new SeleniumUtil();
		
		loginBase.carLoanLogin(selenium,basic);

	}
	
	//放款、还款，并生产客户信息的properties文件
	@AfterTest
	@Parameters({"modulesCase"})
	public void afterClass(String module){
		
		if(StringUtils.isBlank(basic.getApplyNum())){
			
			MyAssert.myAssertFalse("申请录入-填写基本信息用例失败,订单号未生产,故放款还款操作停止操作");
		
		}
		
		logger.info("【"+module+"】"+" 功  能  用  例 全 部 执  行  完   成  ！！");
		
		selenium.getDriver().quit();
		
		LoanFrontPubOperat.loanRepayment(System.getProperty("productType"), basic, module);
		
	}
	
	@AfterSuite
	public void afterSuite() {
		
		FileProcessUtils.processTestngResultFile();

	}
}
