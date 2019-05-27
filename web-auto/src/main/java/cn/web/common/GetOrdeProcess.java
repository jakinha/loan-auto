package cn.web.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.testng.Assert;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;

public class GetOrdeProcess {
	
	static Logger logger = Logger.getLogger(GetOrdeProcess.class);

	/**
	 *信贷  多线程时多用户同时获单场景处理
	 * @param driver	
	 * @param parmJudgment	
	 * @param check	
	 */
	public static void getOrderAlert(SeleniumUtil selenium, String element) {
		
		selenium.click(selenium.getWebElement(element),"获单");
		AlertDefinExcute(selenium);
	}

	/**
	 * 获单
	 * @param selenium	
	 * 
	 * @param getOrderEle	
	 * 					获单按钮元素
	 * @param orderNo	
	 * 					订单号
	 * @param taskPoolNumElement
	 * 					任务池数
	 * @return
	 */
	public static void  getOrder(SeleniumUtil selenium,String getOrderEle,String orderNo,String waitQueueNumElement,String taskPoolNumElement,String todayProcessNumElement){

		String waitQueueNum = null;
		
		String todayProcessNum = null;
		
		String beforGetOrderTaskPoolNum = null;
		
		int i = 1;
		
		do{
			
			SeleniumUtil.sleep(2);

			//获单前的  任务池数量
			beforGetOrderTaskPoolNum = selenium.getText(selenium.getWebElement(taskPoolNumElement));
			
			selenium.jsClick(selenium.getWebElement(getOrderEle), "获单按钮");
			
			SeleniumUtil.sleep();
			
			//获取待处理数
			waitQueueNum = selenium.getText(selenium.getWebElement(waitQueueNumElement));
			
			//获取当日已处理数
			todayProcessNum = selenium.getText(selenium.getWebElement(todayProcessNumElement));
			
			i++;
			
			if(i>20){
	
				MyAssert.myAssertFalse("等待了40s,待处理数和已处理数还是同时为0,业务处理有问题！！");
			}
		}
		while(waitQueueNum.equals("0") && todayProcessNum.equals("0"));

		logger.info("待处理数和当日已处理件数正常,待处理数为："+waitQueueNum+",当日已处理数为："+todayProcessNum);
		
		SeleniumUtil.sleep(2);
		
		if(LoanFrontPubOperat.pageQuery(selenium,orderNo) == false){
		
			//获单后的  任务池数量
			String afterGetOrderTaskPoolNum = selenium.getText(selenium.getWebElement(taskPoolNumElement)).trim();
			
			if(beforGetOrderTaskPoolNum.equals("0")){
				
				MyAssert.myAssertFalse("单子没有进任务池,也就是单子就没有进到这个节点");
			}
			
			else if(beforGetOrderTaskPoolNum.equals(afterGetOrderTaskPoolNum)){
				
				MyAssert.myAssertFalse("单击获单按钮，单子获取不下来");
			}	
		}
	}

	private static void AlertDefinExcute(SeleniumUtil selenium) {
		
		Alert alert = null;
		
		try{
			SeleniumUtil.sleep(2);
			
			alert = selenium.getDriver().switchTo().alert();
			
			logger.info("成功跳转到Alert框里");
			
			alert.accept();
			
			logger.info("成功单击Alert的|确定|取消|的按钮");
			
		}catch(Exception e){
			
			MyAssert.myAssertFalse("跳转到Alert框失败"+e.getMessage());
		}
		
		try{//添加这个捕获异常，是为了再多线程执行的时候，防止被优先用户获单的账户先全部获单了，
			//其他用户再获单就会弹出一个alert提示没有单子可获取
			SeleniumUtil.sleep(0.5);
			
			alert = selenium.getDriver().switchTo().alert();
			
			if(alert.getText().contains("风险定价引擎调用失败")){
				
				Assert.fail("系统的异常错误提示:"+alert.getText());
			}
			
			alert.accept();
			
			logger.info("成功单击任务池没有单子可捞的 Alert的|确定|取消|的按钮");
			
		}catch(Exception e){
			
			System.out.println("没有出现--【任务池没有单子可捞的alert弹出提示】----------------------");
		}
	}
	
}
