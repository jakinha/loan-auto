

package cn.web.main;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import cn.web.util.CreatTestngXml;

public class ExcuteMain{
	
	public static void main(String[] args){
		
		/**信 贷**/
//		System.setProperty("testCases","creditsys.LoanApply");
//		System.setProperty("sellPerson","孙定毅");//不同测试环境所选择的销售人员不同;sit 个贷达州业务01,郑小岸,黄慧, uat2 孙定毅、李军军
//		System.setProperty("productType","精英汇");//精英汇,信托精英汇,信托华楼宝,华楼通
//		System.setProperty("productData","12");//12,36
//		System.setProperty("isRestartOrderServer", "no");
//		System.setProperty("roleType", "wcx");

//		DelectFile.delectFile();
		
		/**车 贷**/
		String methodKeys = "CarLoanApply,ExpeditedItem,ContractCance,LocalEvaluateCanceOrder,SettleOverdueCarFrameRule,RepaidNotSettleCarFrameRule,CarLoanFieldSurvey,ChangeBankCard,FinalTrialReject,GoBack,LocalEvaluateCar300Logic,"
						+ "ProcessMonitoringAssign,ReconsiderationPass,ReconsiderationReject,StoreEntryModifyInfo,StoreSigningUpdateLoanInfo";
		
		System.setProperty("testEnv","uat-02");//测试环境 sit-01,uat-02
		System.setProperty("className","carloan.CarLoanWebCases");
		System.setProperty("methodKeys","CarLoanApply");//,SettleOverdueCarFrameRule,RepaidNotSettleCarFrameRule
		System.setProperty("productType","分期24期A");//GPS3期-1.98,分期36期D,移交3期-1.90,分期24期A(不做放款还款操作)
		


		/**车 贷 展 期**/	
//		System.setProperty("testEnv","uat-02");//测试环境 sit-01,uat-02
//		System.setProperty("className","carloan.CarLoanWebCases");
//		System.setProperty("methodKeys","CarLoanExtensionReviewFlow");
//		System.setProperty("productType","GPS3期-1.98");

		List<String> suit=new ArrayList<String>();
		
		new CreatTestngXml().creatTestNGXml(System.getProperty("className"),System.getProperty("methodKeys"));
		
		suit.add("testng.xml");
		
		TestNG testng=new TestNG();
		
		testng.setTestSuites(suit);
			
		testng.run();
	}	
}
