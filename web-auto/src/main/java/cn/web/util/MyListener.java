package cn.web.util;


import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import cn.web.base.TestngBase;


public class MyListener extends TestListenerAdapter{//  implements ITestListener
	
	Logger logger = Logger.getLogger(MyListener.class);

	@Override
	public void onTestSuccess(ITestResult tr) {
		
		super.onTestSuccess(tr);
		logger.info(tr.getMethod().getRealClass().getSimpleName()+"."+tr.getMethod().getMethodName() + "用例	 Success");
//		OverrideIReTry retry = (OverrideIReTry) tr.getMethod().getRetryAnalyzer();
//	    retry.reset();
	}
	
	@Override
	public void onTestFailure(ITestResult tr) {
		
		super.onTestFailure(tr);
		logger.debug(tr.getMethod().getRealClass().getSimpleName()+"."+tr.getMethod().getMethodName() + "用例	  Failure");
		
//		OverrideIReTry retry = (OverrideIReTry) tr.getMethod().getRetryAnalyzer();
//	    retry.reset();
	    
		TestngBase testBase = (TestngBase) tr.getInstance();
		String order = testBase.getBasicTestDataBean().getApplyNum();
		testBase.getSelenium().catchExceptions(tr,System.getProperty("project"),order);
	}
	
	@Override
	public void onTestSkipped(ITestResult tr) {
		
		super.onTestSkipped(tr);
		logger.info(tr.getMethod().getRealClass().getSimpleName()+"."+tr.getMethod().getMethodName() + "用例	 Skipped");
	}
	
	@Override
	public void onTestStart(ITestResult tr) {
//		super.onTestStart(tr);
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		
		super.onConfigurationFailure(itr);
		logger.debug(itr.getMethod().getRealClass().getSimpleName()+"."+itr.getMethod().getMethodName() + "用例	  Failure");

		TestngBase testBase = (TestngBase) itr.getInstance();
		String order = testBase.getBasicTestDataBean().getApplyNum();
		testBase.getSelenium().catchExceptions(itr,System.getProperty("project"),order);
	}

	@Override
	public void onFinish(ITestContext testContext) {
		
/*		Iterator<ITestResult> listOfSkipTests = testContext.getSkippedTests().getAllResults().iterator();
//		Iterator<ITestResult> listOfPassTests = testContext.getPassedTests().getAllResults().iterator();

		List<String> skipedList = new ArrayList<String>();
//		List<String> passList = new ArrayList<String>();
//		List<Integer> failTestIds = new ArrayList<Integer>();
		
		while (listOfPassTests.hasNext()) {

  			 ITestNGMethod passMethod = listOfPassTests.next().getMethod();
  
  			 passList.add(passMethod.getMethodName());
  		}

		
		for (ITestResult failTest : testContext.getFailedTests().getAllResults()) {

			 
			 int id = failTest.getTestClass().getName().hashCode();

			  id = id + failTest.getMethod().getMethodName().hashCode();

			  id = id + (failTest.getParameters() != null ? Arrays.hashCode(failTest.getParameters()) : 0);
			 
			  failTestIds.add(id);

			 }
		
		
		 while (listOfSkipTests.hasNext()) {

   			 ITestNGMethod skipMethod = listOfSkipTests.next().getMethod();
   			
   			 if (testContext.getSkippedTests().getResults(skipMethod).size() > 1) {
   	            	listOfSkipTests.remove();
   	            	skipedList.add(skipMethod.getMethodName());
   	          }
   			 else if(skipedList.contains(skipMethod.getMethodName())){
   				listOfSkipTests.remove();
   			 }
   			 if(passList.contains(skipMethod.getMethodName())){
   				listOfSkipTests.remove();
   			 }
   		}*/
	}
}
