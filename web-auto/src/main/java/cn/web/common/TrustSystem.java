package cn.web.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.common.credit_loan.CycleOperat;
import cn.web.dto.BasicTestDataBean;
import cn.web.service.credit_service.ApprovalProcessService;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.RandomUtil;
import cn.web.util.SeleniumUtil;


/**
 * 信托系统
 * @author Administrator
 *
 */
public class TrustSystem {
	
	static PropertyUtil pro = GetElemFilePropertyUtil.trustSysElePro;
	static PropertyUtil iframe_pro = GetElemFilePropertyUtil.ifmpro;
	static Logger logger = Logger.getLogger(TrustSystem.class);
	
	/**创建一个信托计划，默认 发行规模 一百万**/
	public static void createTrustPlan(SeleniumUtil selenium){
		
		trustSystemLogin(selenium);
	
		String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		selenium.click(selenium.getWebElement(pro.getproperValue("product.manage")), "产品管理");
		selenium.click(selenium.getWebElement(pro.getproperValue("trust.plan")), "信托计划");
		selenium.switchFrameExec(iframe_pro.getproperValue("iframe2"));
		selenium.click(selenium.getWebElement(pro.getproperValue("add")), "添加");
		selenium.switchFrameExec(iframe_pro.getproperValue("layui.layer.iframe2"));
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("product.name")), "自动化"+RandomUtil.randomStr(3)+"信托计划", "产品名称");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("product.code")), "zdh_"+time, "合同编号");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("begin.date")),new SimpleDateFormat("YYYY-mm-dd").format(new Date()), "匹配开始时间");
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("end.date")), "2025-01-01", "匹配结束时间");
		selenium.switchPrentFrame("F");
		selenium.click(selenium.getWebElement(pro.getproperValue("define")), "确定");
		selenium.getDriver().quit();
	}
	
	/**
	 *信贷终审后,中粮信托 进行   待审核债权
	 * @param seleniumNew
	 */
	public static void trustPlanToDebtReview(SeleniumUtil seleniumNew,BasicTestDataBean basic){
		
		trustSystemLogin(seleniumNew);
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("claims.manage")), "债权管理");
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("wait.audit.claims")), "待审核债权");
		
		seleniumNew.switchFrameExec(iframe_pro.getproperValue("iframe5"));
		
		CycleOperat.flowTaskTableQuery(ApprovalProcessService.orderNoResource(basic), "信托终审");
		
		seleniumNew.cleanAndInput(seleniumNew.getWebElement(pro.getproperValue("claims.id")),ApprovalProcessService.orderNoResource(basic),"债权ID");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("query")), "查询按钮");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("check.box")), "选中目标记录");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("pass")), "通过按钮");
		
		seleniumNew.switchPrentFrame("D");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("confim")), "确认按钮");
		
		seleniumNew.switchFrameExec(iframe_pro.getproperValue("iframe5"));
		

		if(MyAssert.myAssertTrue(seleniumNew.getDriver(), "操作成功!") == false){
			
			MyAssert.myAssertFalse("断言失败，么有出现期望值：操作成功");
		}	
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("keep.trial")),"继续审核");
		seleniumNew.getDriver().quit();
	}
	
	/**信托合同审核**/
	public static void trustContractTrial(SeleniumUtil seleniumNew,BasicTestDataBean basic){
		
		trustSystemLogin(seleniumNew);
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("order.manage")), "订单管理");
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("wait.contract.trial")), "待合同审核");
		
		seleniumNew.switchFrameExec(iframe_pro.getproperValue("iframe8"));
		
		seleniumNew.cleanAndInput(seleniumNew.getWebElement(pro.getproperValue("order.id")), ApprovalProcessService.orderNoResource(basic), "订单编号");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("query")), "查询按钮");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("check.box")), "选中目标记录");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("wait.contract")), "合同审核");
		
		seleniumNew.switchPrentFrame("F");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("confim")), "确认按钮");
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("define")), "确定");

		if(MyAssert.myAssertTrue(seleniumNew.getDriver(), "审核成功 ") ==  false){
			
			MyAssert.myAssertFalse("断言失败，么有出现期望值：审核成功");
		}
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("keep.trial.contract")), "继续审核");
		seleniumNew.switchPrentFrame("D");
		lending(seleniumNew,basic);
	}
	
	/**放款**/
	public static void lending(SeleniumUtil seleniumNew,BasicTestDataBean basic){

		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("wait.lending")), "待放款");
		seleniumNew.switchFrameExec(iframe_pro.getproperValue("iframe9"));
		
		seleniumNew.cleanAndInput(seleniumNew.getWebElement(pro.getproperValue("order.id")), ApprovalProcessService.orderNoResource(basic), "订单编号");
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("query")), "查询按钮");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("check.box")), "选中目标记录");
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("lending")), "放款");
		
		seleniumNew.switchPrentFrame("F");
		
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("confim")), "确认按钮");
		seleniumNew.click(seleniumNew.getWebElement(pro.getproperValue("define")), "确定");

		if(MyAssert.myAssertTrue(seleniumNew.getDriver(), "xxx ") == false){
			
			MyAssert.myAssertFalse("断言失败，么有出现期望值：["+"xxx "+"]");
		}
		
		seleniumNew.getDriver().quit();
	}
	
	/**信托系统登录**/
	public static void trustSystemLogin(SeleniumUtil selenium){
		
		String url = null;
		String pwd =null;
		if(System.getProperty("testEnv").equals("uat-02")){
			url = "http://192.168.11.219:8090/trust/a";
			pwd = "admin";
		}
		else if(System.getProperty("testEnv").equals("sit-01")){
			url = "http://192.168.11.100:8080/trust/a";
			pwd = "huaxia2017";
		}
		
		selenium.openBrower(url);
		SeleniumUtil.sleep(2);
		if(!selenium.getDriver().getPageSource().contains("管理员")){
			
			selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("login.name")), "admin", "用户名");
			selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("login.pwd")), pwd, "登录密码");
			selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("login.validate.code")), "abcd", "图形验证码");
			selenium.click(selenium.getWebElement(pro.getproperValue("login.but")),"登录按钮");
			
			int i= 0;
			while(!selenium.getDriver().getPageSource().contains("退出")){
				SeleniumUtil.sleep(1);
				i++;
				if(i>10){

					MyAssert.myAssertFalse("登录失败");
				}
			}
		}
	}
}
