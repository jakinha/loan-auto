package cn.web.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;

import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.common.constant.WebPlatformConstant;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.Constant;
import cn.web.util.MyAssert;
import cn.web.util.PropertyUtil;
import cn.web.util.ReadConfXml;
import cn.web.util.SeleniumUtil;

/**
 * 登 陆
 * @author huangjun
 *
 */

public class LoginBase{
	
	private Logger logger =Logger.getLogger(LoginBase.class);
	
	private Map<String, String> map = new ConcurrentHashMap<String, String>();
	
	private PropertyUtil carElePro = new PropertyUtil(WebPlatformConstant.CAR_LOAN_SYS_ELE_PATH);
	
	private PropertyUtil carLoanLoginInfo = new PropertyUtil(Constant.PUBLIC_CONFIG);
	
	private String testEnv=System.getProperty("testEnv").trim();
	
	private SeleniumUtil selenium ;

	
	/**
	 * 信贷系统角色登录
	 * @param testDataBean
	 * @param selenium
	 */
	public void creditSysLogin(SeleniumUtil selenium,TestDataModel testDataBean){
		
		this.selenium = selenium;
		
		JSONObject loginInfo = getLoginInfo(testDataBean);
		
		prilogin(loginInfo.getString("url").toString(),loginInfo.getString("loginRole").toString());
	}
	
	/**车贷系统登陆**/
	public void carLoanLogin(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		this.selenium = selenium;
		
		Map<String, String> milieu = milieu();
		
		selenium.openBrower(milieu.get("url").toString());
		
		String accountManager = System.getProperty("accountManager");
		if(StringUtils.isNotBlank(accountManager)){
			
			selenium.cleanAndInput(selenium.getWebElement(carElePro.getproperValue("login_name")), accountManager, "用户名");
		}
		else{
			
			selenium.cleanAndInput(selenium.getWebElement(carElePro.getproperValue("login_name")), milieu.get("loginRole").toString(), "用户名");
		}

		selenium.cleanAndInput(selenium.getWebElement(carElePro.getproperValue("login_pwd")),  milieu.get("loginPwd").toString(), "密码");
		
		selenium.click(selenium.getWebElement(carElePro.getproperValue("login_but")),"华夏信财","登录按钮");
		
		basicTestDataBean.setLoginRule(milieu.get("rule2"));
		
		logger.info("系统登录成功");
	}
	
	/**
	 * 车贷重新打开浏览器进行指定的节点角色系统登陆
	 * @param selenium
	 * @param loginRole	角色用户
	 * @param isReOpenBrower 是否重新打开浏览器   true：重新打开，false:不需要重新打开     
	 */
	public void carLoanNodeNameLogin(SeleniumUtil selenium ,String loginRole,boolean isReOpenBrower){
		
		this.selenium = selenium;
		
		Map<String, String> milieu = milieu();
		
		if(true == isReOpenBrower){

			selenium.openBrower(milieu.get("url").toString());
		}
		
		selenium.cleanAndInput(selenium.getWebElement(carElePro.getproperValue("login_name")), loginRole, "用户名");
		
		selenium.cleanAndInput(selenium.getWebElement(carElePro.getproperValue("login_pwd")), milieu.get("loginPwd").toString(), "密码");
		
		selenium.click(selenium.getWebElement(carElePro.getproperValue("login_but")),"华夏信财","登录按钮");

		logger.info("系统登录成功");
	}
	
	/**
	 * 车贷系统-退出登陆
	 * @param selenium
	 */
	public void carLoanSysLoginOut(SeleniumUtil selenium){
		
		selenium.switchPrentFrame("D");
		
		selenium.jsClick(selenium.getWebElement(carElePro.getproperValue("login_user")), "登录用户");
		
		selenium.jsClick(selenium.getWebElement(carElePro.getproperValue("login_out")),GetElemFilePropertyUtil.checkPointPro.getproperValue("logout"),"退出按钮");
	}
	
	/**
	 * 车贷系统-先做  退出登陆,再重新登陆
	 * @param selenium
	 * @param basicTestDataBean
	 */
	public void carLoanLoginOutAngLogin(SeleniumUtil selenium,BasicTestDataBean basicTestDataBean){
		
		carLoanSysLoginOut(selenium);
		
		carLoanLogin(selenium, basicTestDataBean);
	}
	
	
	private JSONObject getLoginInfo(TestDataModel testDataBean){
		
		JSONObject strToMap = JSONObject.parseObject(testDataBean.getTestData());
		
		JSONObject loginInfo = (JSONObject)strToMap.get("login");
		
		return loginInfo;
	}
	
	private void prilogin(String urlKey,String loginNameKey){
		
		PropertyUtil pro=new PropertyUtil(WebPlatformConstant.LOAN_SYS_ELE_PATH);
		
		String roleType = System.getProperty("roleType");
		
		if(roleType.equals("ml")){
			map=milieu("creditsys_lj");
		}
		else{
			map=milieu("creditsys");
		}
		
		String  loginName = map.get(loginNameKey);
		
		selenium.openBrower(map.get(urlKey));
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("GD_userName")), loginName,"用户名");
		
		selenium.cleanAndInput(selenium.getWebElement(pro.getproperValue("GD_pwd")), "Hx000000","登录密码");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("GD_loginbut")),"登录按钮");
		
		SeleniumUtil.sleep(3);
		selenium.exceptionAlert("单击登录按钮时");
		
		if(MyAssert.myAssertTrue(selenium.getDriver(), WebPlatformConstant.LOGIN_SUCESS_CHECK_POINT ) == false){
			
			MyAssert.myAssertFalse("断言失败，么有出现期望值：["+WebPlatformConstant.LOGIN_SUCESS_CHECK_POINT+"]");
		}
		logger.info("系统登录成功");
	}
	
	/**信贷系统环境**/
	private Map<String, String> milieu(String goalNode){
		
		//环境配置文件路径<测试地址 和 各系统角色>
		String milieuPath=WebPlatformConstant.MILIEU_CONFIG;
		
		Map<String, String> map = new HashMap<String, String>();
		
		if("sit-01".equals(testEnv)){
			map= ReadConfXml.getInstence().getMultValue(milieuPath, "sit", goalNode);
		}
		else if("sit-02".equals(testEnv)){
			map= ReadConfXml.getInstence().getMultValue(milieuPath, "sit2" ,goalNode);
		}
		else if("uat-02".equals(testEnv)){
			map = ReadConfXml.getInstence().getMultValue(milieuPath, "uat2",goalNode);
		}
		else{
			MyAssert.myAssertFalse("登录地方的测试环境参数配置有问题！！！！！！！！！！！！");
		}
		return map;
	}
	
	/**车贷系统环境**/
	private Map<String, String> milieu(){
		
		Map<String, String> map = new HashMap<String, String>();
		
		String url = null;
		
		if("sit-01".equals(testEnv)){
			
			url = carLoanLoginInfo.getproperValue("car_credit_sit_url");
			
		}
		else if("uat-02".equals(testEnv)){
			
			url = carLoanLoginInfo.getproperValue("car_credit_uat_url");
			
		}
		else{
			MyAssert.myAssertFalse("登陆处没有传给一个测试环境的变量值");
		}
		
		String loginRole = carLoanLoginInfo.getproperValue("car_credit_login_rule");
		
		String loginRole2 = carLoanLoginInfo.getproperValue("rule2");
		
		String loginPwd = carLoanLoginInfo.getproperValue("car_loan_login_pwd");
		
		map.put("url", url);
		
		map.put("loginRole", loginRole);
		
		map.put("rule2", loginRole2);
		
		map.put("loginPwd", loginPwd);
		
		return map;
	}

}
