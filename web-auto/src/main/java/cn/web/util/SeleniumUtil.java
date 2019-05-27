package cn.web.util;

import java.awt.Robot;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import cn.web.base.DriverBase;
import cn.web.common.constant.WebPlatformConstant;


public class SeleniumUtil {
	
	private final static Logger logger = Logger.getLogger(SeleniumUtil.class);

	private WebDriver driver;

	public SeleniumUtil() {
		driver = new DriverBase().installDriver();
	}


	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * 打开浏览器
	 * @param url
	 */
	public void openBrower(String url) {
		try {
			logger.info("测试环境地址是:"+url);
			driver.get(url);
			sleep(0.8);
		} catch (Exception e) {
			e.printStackTrace();
			MyAssert.myAssertFalse("浏览器打开出现异常");
		}
	}
	
	/**
	 *刷新页面
	 *
	 */
	public void refreshPage() {
		
		driver.navigate().refresh();
		sleep();
	}
	
	/**
	 * 获取所有相同元素的WebElement
	 * @param element 元素表达式(字符串)
	 * @return 返回List<WebElement>
	 */
	public List<WebElement> getMuiltWebElements(String element){

			By by = ElementModle.getEleModle(element);
			List<WebElement> elementsList = null;
		
			if(displayWait(by)){
				elementsList = driver.findElements(by);
			}
			else {
				MyAssert.myAssertFalse("等待超时，当前页没有出现要操作的元素：" + element);
			}
			return elementsList;
	}
	
	/**
	 * 获取WebElement,找到元素则返回WebElement,没找到直接用例失败
	 * @param element 元素表达式(字符串)
	 * @return 返回 WebElement,
	 */
	public WebElement getWebElement(String element){

		By by = ElementModle.getEleModle(element);
		WebElement webElement = null;
	
		if(displayWait(by)){
			webElement = driver.findElement(by);
		}
		else {

			MyAssert.myAssertFalse("等待超时，当前页没有出现要操作的元素：" + element);
		}
		return webElement;
	}
	
	/**
	 * 获取WebElement,找到元素则返回WebElement,没找到返回null
	 * @param element 元素表达式(字符串)
	 * @return 返回 WebElement,
	 */
	public WebElement getElement(String element){

		By by = ElementModle.getEleModle(element);
		WebElement webElement = null;
	
		if(displayWait(by)){
			webElement = driver.findElement(by);
		}
		else {

			return null;
		}
		return webElement;
	}
	/**
	 *  获取By类型对象
	 * @param element
	 * @return Boolean
	 */
	public Boolean getByType(String element){

		if(displayWait(ElementModle.getEleModle(element))){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 正常单击,不需要断言
	 * @param element
	 * @param logInfo
	 */
	public void click(WebElement element,String logInfo){
		
		element.click();
		logger.info("成功单击到:["+logInfo+"]");
	}
	
	/**
	 * 正常单击,需要断言
	 * @param element
	 * @param checkPoint
	 * @param logInfo
	 */
	public void click(WebElement element,String checkPoint,String logInfo){
		
		click(element,logInfo);
		
		if(MyAssert.myAssertTrue(driver, checkPoint) == false){
			
			MyAssert.myAssertFalse("断言失败，么有出现期望值：["+checkPoint+"]");
		}
	}
	
	/**
	 * JS单击,不需要断言
	 * @param element
	 * @param logInfo
	 */
	public void jsClick(WebElement element,String logInfo){
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		logger.info("成功进行js的单击到:["+logInfo+"]");
	}
	
	/**
	 *  JS单击,需要断言
	 * @param element
	 * @param checkPoint	
	 * @param logInfo
	 */
	public void jsClick(WebElement element,String checkPoint,String logInfo){
		
		jsClick(element,logInfo);

		if(MyAssert.myAssertTrue(driver, checkPoint) == false){
			
			MyAssert.myAssertFalse("断言失败，么有出现期望值：["+checkPoint+"]");
		}
	}

	/**
	 * 双击操作,不需要断言
	 * @param element
	 * @param logInfo
	 */
	public void dclick(WebElement element,String logInfo) {

		Actions action = new Actions(driver);
		action.doubleClick(element).build().perform();
		logger.info("成功双击到:["+logInfo+"]");
	}
	
	/**
	 * 双击操作,需要断言
	 * @param element
	 * @param logInfo
	 */
	public void dclick(WebElement element,String checkPoint,String logInfo) {

		dclick(element,logInfo);

		if(MyAssert.myAssertTrue(driver, checkPoint) == false){
			MyAssert.myAssertFalse("断言失败，么有出现期望值：["+checkPoint+"]");
		}
	}

	
	/**
	 * 断言界面包含指定文本,do里面添加了断言前的一个单击操作，防止单击没生效，继续单击,默认睡眠时间 2秒
	 * 
	 * @param webElement
	 * 					WebElement 类型元素
	 * @param elementName
	 * 					元素的文本
	 * @param assertText
	 * 					断言文本
	 */
	public void loopClickAssertion(WebElement webElement,String elementName,String assertText){
		
		MyAssert.cyclingClickAssert(this,webElement,elementName,assertText,2);
	}
	
	/**
	 * 断言界面包含指定文本,do里面添加了断言前的一个单击操作，防止单击没生效，继续单击
	 * 
	 * @param webElement
	 * 					WebElement 类型元素
	 * @param elementName
	 * 					元素的文本
	 * @param assertText
	 * 					断言文本
	 * 
	 * @param sleepTime 
	 * 					自定义睡眠时间
	 */
	public void loopClickAssertion(WebElement webElement,String elementName,String assertText,double sleepTime){
		
		MyAssert.cyclingClickAssert(this,webElement,elementName,assertText,sleepTime);
	}
	
	/**
	 * 输入操作
	 * @param element
	 * @param content
	 * @param logInfo
	 */
	public void input(WebElement element,String content,String logInfo){

		element.sendKeys(content);
		logger.info("成功输入["+logInfo+"]:" + content);
	}
	
	public void clickInput(WebElement element,String content,String logInfo){
		
		element.click();

		sleep(0.5);

		input(element,content,logInfo);
	}
	
	/**
	 * 输入操作(先单击做清空操作再输入)
	 * @param element
	 * @param content
	 * @param logInfo
	 */
	public void cleanAndInput(WebElement element,String content,String logInfo){
		
		element.click();
		element.clear();
		sleep(0.4);
		input(element,content,logInfo);
	}

	/** 模拟鼠标左键单击 **/
	public void keyBoardClick(String ele,String logInfo) {
		
		By by = ElementModle.getEleModle(ele);
		Actions action = new Actions(driver);
		
		if(displayWait(by)){
			action.click(driver.findElement(by)).build().perform();
			logger.info("成功进行鼠标左键的单击元素:" + logInfo);
		}
		else{
			MyAssert.myAssertFalse("等待元素超时了了了：" + by);
		}
	}

	/**
	 * 分页查找 目标订单,找到之后便单击选中
	 * @param element	要查找的订单元素
	 * @param pageInputEle	输入的页号的元素定位
	 */
	public void pageQuery(String element,String pageInputEle,String logInfo){
		
		By by = ElementModle.getEleModle(element);
		int j=1;
		
		if (displayWait(by,5)) {
			click(getWebElement(element),logInfo);
		}
		else{
			
			logger.info("当前也没有找到目标订单："+element+",准备输入页码查找");
			WebElement inputElement = getWebElement(pageInputEle);
			
			for(int i = 1;i<8;i++){
				
				logger.info("第"+i+"页没有找到目标订单号"+element);
				input(inputElement, String.valueOf(i), "分页搜索");
				KeyBoard.enter();
				
				if(displayWait(by,5)){
					driver.findElement(by).click();
					break;
				}
				if(i==8){
					i=0;
				}
				j++;
				if(j>8){
					MyAssert.myAssertFalse("没找到元素：" + by);
				}
				else{
					continue;
				}
			}
		}
	}

	/**
	 * Alert弹框操作(确定)
	 * @param isCheck 
	 * 				判断走那个断言方式(完全断言、包含断言、-1 不作断言)
	 * @param check
	 *            检查点，检查弹框里的文本
	 */
	public void alertConfirm(Alert alert,String isCheck,String check) {

		if(!"-1".equals(isCheck)){

			if (isCheck.equals("完全断言")) {

				MyAssert.myAssertEquals(alert.getText(), check);
			}
			else if(isCheck.equals("包含断言")){
				if(!alert.getText().contains(check)){

					MyAssert.myAssertFalse("alert里的提示功能操作结果有问题,提示信息是："+alert.getText());
				}
				else{
					logger.info("Alert里的内容正确："+alert.getText());
				}
			}
		}
		alert.accept();
		logger.info("成功单击Alert的|确定|取消|的按钮");

	}
	
	/**对alert做取消操作**/
	public void AlertCance(Alert alert) {

		alert.dismiss();
		logger.info("成功单击Alert的|确定|取消|的按钮");
}
	
	/**
	 * 判断Aleart弹出是否出现了
	 * @return 返回 Alert实体
	 */
	public Alert alertIsDisplay() {
		try {
			sleep(3);
			Alert alert = driver.switchTo().alert();
			logger.info("Alert出现了,并跳转到alert !!!!!!!!!!!!!!");
			return alert;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 判断Aleart弹出是否出现了,出现alert说明有异常，没有出现，说明正常
	 * 
	 * @param logInfo 哪个阶段的操作的信息
	 */
	public void exceptionAlert(String logInfo) {
		try {
			sleep(3);
			Alert alert = driver.switchTo().alert();
			MyAssert.myAssertFalse(logInfo+" 弹出异常提示内容："+alert.getText());		
		} catch (Exception e) {
			logger.info("执行正常,没有弹出异常提示的alert。");
		}
	}
	
	/**
	 *  处理 <单击某元素/或者不做单击操作> 是否出现alert
	 * @param element	要操作的元素
	 * @param queryNum  自定义查询次数
	 */
	public Alert clickDisplayAlert(WebElement element,int queryNum,String logInfo){
		
		Alert alert = null ;
		int i = 0;
		
		do{
			if(null!=element){
				click(element,logInfo);
			}
			alert = alertIsDisplay();
			if(null!= alert){
				return alert;
			}
			if(i>queryNum){

				MyAssert.myAssertFalse("单击了"+queryNum+"次,共等了30秒,"+logInfo+"的alert弹框没有出现...");
			}
			i++;
		}
		while(null == alert);
		return alert;
	}
	
	/**
	 * 将一个元素拖拽到另一个元素上
	 * 
	 * @param resourceGoal
	 *            源元素
	 * @param goal
	 *            目的元素
	 */
	public void dragDropEleExcute(String resourceGoal, String goal) {
		try {

			WebElement dragEles1 = driver.findElement(ElementModle.getEleModle(resourceGoal));
			WebElement dragEles2 = driver.findElement(ElementModle.getEleModle(goal));
			new Actions(driver).dragAndDrop(dragEles1, dragEles2).perform();

		} catch (Exception e) {
			e.printStackTrace();
			MyAssert.myAssertFalse(e.getMessage());
		}
	}

	/**
	 * 判断 元素是可操作
	 * 
	 * @param element
	 */
	public void isEnable(String element) {

		WebElement ele = isDisplay(element);

		if (ele.isEnabled()) {
			logger.info("元素可操作是正确的");
		} else {

			MyAssert.myAssertFalse("元素不可操作不是期望结果");
		}
	}

	/**
	 * 判断元素是不可操作
	 * 
	 * @param element
	 */
	public void unIsEnable(String element) {
		WebElement ele = isDisplay(element);

		if (ele.isEnabled() == false) {
			logger.info("元素不可操作是正确的");
		} else {
			MyAssert.myAssertFalse("元素可操作不是期望结果");
		}
	}

	// 元素是否找到
	public WebElement isDisplay(String element) {

		if (displayWait(ElementModle.getEleModle(element))) {
			
			return driver.findElement(ElementModle.getEleModle(element));
		} else {

			MyAssert.myAssertFalse("元素等待超时,没找到！！：" + element);
		}
		return null;
	}

	/**
	 * 获取html标签文本并做断言 <用于 页面存在很多字段需要断言,需要获取所有字段值>
	 * 
	 * @param element
	 * @param assertText
	 */
	public void getTextAndAssert(String element[], String assertText[]) {
		
		if (element.length == assertText.length) {
			
			for (int j = 0; j < element.length; j++) {
				
				if (displayWait(ElementModle.getEleModle(element[j]))) {
					String str = driver.findElement(ElementModle.getEleModle(element[j])).getText();
					MyAssert.myAssertEquals(str, assertText[j]);
				} 
				else {
					MyAssert.myAssertFalse("元素等待超时：" + element[j]);
				}
			}
		} 
		else {
			MyAssert.myAssertFalse("表达式数量和要断言的文本个数不一致");
		}
	}

	/**
	 * 获取页面文本
	 * @param element
	 * @return 返回文本值
	 */
	public String getText(WebElement element) {

		return element.getText().trim();
	}

	/**
	 * 获取html标签属性的值并做断言 <用于 页面存在很多字段需要断言,需要获取所有字段值>
	 * @param property  属性名
	 * @param element
	 * @param assertText
	 */
	public void getAttributesExcute(String property, String element[], String assertText[]) {

		if (element.length == assertText.length) {
			
			for (int i = 0; i < element.length; i++) {
				
				if (displayWait(ElementModle.getEleModle(element[i]))) {
					
					if (property.equals("value")) {
						
						MyAssert.myAssertEquals(driver.findElement(ElementModle.getEleModle(element[i])).getAttribute("value"), assertText[i]);
					
					} else if (property.equals("class")) {
						
						MyAssert.myAssertEquals(driver.findElement(ElementModle.getEleModle(element[i])).getAttribute("class"), assertText[i]);
					}
				} else {
					MyAssert.myAssertFalse("等待元素超时:" + element[i]);
				}
			}

		} else {
			MyAssert.myAssertFalse("元素定位表达式数量和要断言的文本的数量不一致");
		}
	}

	/**
	 * 获取 html标签的属性值
	 * @param element
	 * @param property
	 *            属性名称
	 * @return 属性值
	 */
	public String getPropertyValue(String element, String property) {

		return driver.findElement(ElementModle.getEleModle(element)).getAttribute(property);
	}

	/**
	 * 鼠标移动到电脑的左上角
	 * @param element
	 */
	public void moveToElement(String element) {
		try {
			Robot rt = new Robot();
			rt.mouseMove(0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		WebElement ele = driver.findElement(ElementModle.getEleModle(element));
		if (null != ele) {
			try {
				Actions builder = new Actions(driver);
				builder.moveToElement(ele).perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 鼠标移动到指定元素，联想出下级菜单
	 * @param expression
	 *            一级菜单元素
	 * @param element
	 *            二级菜单目标元素
	 * @return
	 */
/*	public Boolean moverElement(String expression, String element) {
		int i = 1;
		implicitlyWait();
		By by = ElementModle.getEleModle(expression);
		By by1 = ElementModle.getEleModle(element);
		WebElement ele = null;
		Actions action = new Actions(driver);
		try {
			while (i < 5) {
				ele = driver.findElement(by);
				action.moveToElement(ele).perform();
				sleep(1500);
				if (isEleExist(by1)) {
					logger.info("鼠标悬停显示元素成功");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return false;
	}*/


	/**
	 * 移除html标签readOnly属性
 	 * @param ele
	 * @param removeAttribute
	 * 		readOnly
	 */
	public void readOnlyRemove(WebElement ele, String removeAttribute) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute(arguments[1],arguments[2])", ele, removeAttribute);
	}

	/**
	 * 设置html标签属性
 	 * @param ele
	 * @param attribute  属性
	 * @param newAttributeValue 新标签值
	 */
	public void setAttribute(WebElement ele, String attribute, String newAttributeValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1],arguments[1])", ele, attribute, newAttributeValue);

	}

	/**
	 * 多层iframe跳转
	 * 
	 * @param iframes
	 */
	public void switchFrameExec(String iframes) {
		
		String iframeArry[] = iframes.split(",");
		WebElement frame;
		try {
			SeleniumUtil.sleep(2);
			for (int i = 0; i < iframeArry.length; i++) {
				sleep(0.5);
				frame = driver.findElement(ElementModle.getEleModle(iframeArry[i]));
				driver.switchTo().frame(frame);
			}
			logger.info("iframe 跳 转 成 功:"+iframes);
		} catch (Exception e) {
			e.printStackTrace();
			MyAssert.myAssertFalse("跳转iframe失败:"+e.getMessage());
		}
	}

	/**
	 * 跳转默认 或 跳转到上级..上上级iframe
	 * @param frame
	 *  		"F":跳转父iframe
	 *  
	 *  		"D":跳转到初始iframe
	 *  
	 *  		"frames":跳转多层iframe
	 */
	public void switchPrentFrame(String frame) {
		
		try{
			if (frame.equals("F")) {
				driver.switchTo().parentFrame();
			} 
			else if (frame.equals("D")) {
				driver.switchTo().defaultContent();
			} else {
				int times = Integer.parseInt(frame.split("F")[1]);
				for (int i = 1; i <= times; i++) {
					driver.switchTo().parentFrame();
					sleep(1);
				}
			}
			logger.info("iframe 跳 转 成 功:"+frame);
		}catch(Exception e){
			e.printStackTrace();
			MyAssert.myAssertFalse(e.getMessage());
		}
	}

	/**
	 * 智能隐式等待
	 *
	 */
	public void implicitlyWait() {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	/**
	 * 智能自定义显示等待,默认等待超时时间为20秒
	 * @param by
	 */
	public boolean displayWait(By by) {
		
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {

			return false;
		}
	}
	
	/**
	 * 智能自定义显示等待 ,自定义等待超时时间
	 * @param by
	 */
	public boolean displayWait(By by, int timeout) {
		
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 线程死等1.5秒
	 */
	public static void sleep() {
		
		try {
			Thread.sleep((int)(1.5*1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 死 等 (自定义等待时间)
	 * @param time 
	 */
	public static void sleep(double time){
		
		try {
			Thread.sleep((int)(time*1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**截图放在服务**/
	public void catchException(ITestResult result,String project,String order) {

		SimpleDateFormat formater = new SimpleDateFormat("ddHHmmss");
		String runtime = formater.format(new Date());
		SimpleDateFormat formater1 = new SimpleDateFormat("MM");
		String runtimeFile = formater1.format(new Date());
		String	tomcatPath;
		String	ip;
		
		if("信贷项目".equals(project)){//目前新贷项目的测试机是单独的
			tomcatPath = WebPlatformConstant.IE_TEST_NODE_TOMCAT_WEBAPP_PATH;
			ip = WebPlatformConstant.IE_TEST_NODE_IP;
		}
		else{
			tomcatPath = WebPlatformConstant.TEST_NODE_TOMCAT_WEBAPP_PATH;
			ip = WebPlatformConstant.TEST_NODE_IP;
		}
        if (!result.isSuccess()) {
     
            Reporter.setCurrentTestResult(result);

            String picName = result.getMethod().getRealClass().getSimpleName()+"."+result.getMethod().getMethodName()+runtime;
            String escapePicName=escapeString(picName);
            String html = "<img src=http://" + ip + "/" +runtimeFile+"/"+ picName+".png onclick='window.open(\"http://" + ip + "/"+runtimeFile+"/"+escapePicName+".png\")' hight='100' width='100' />";
            Reporter.log("该bug的订单号是："+order);
            Reporter.log(html);
			try {
				File srcFile = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(tomcatPath + File.separator+runtimeFile+File.separator + picName + ".png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
	
	/**截图本地**/
	public void catchExceptions(ITestResult result,String project,String order) {

		SimpleDateFormat formater = new SimpleDateFormat("ddHHmmss");
		String runtime = formater.format(new Date());
		
        if (!result.isSuccess()) {
     
            Reporter.setCurrentTestResult(result);

     		File file = new File("snapshot");      
  			Reporter.log(file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            String dest = result.getMethod().getRealClass().getSimpleName()+"."+result.getMethod().getMethodName();
            
            String picName=filePath+File.separator+dest+runtime;
            String escapePicName=escapeString(picName);

            String html="<img src='"+picName+".png' onclick='window.open(\""+escapePicName+".png\")'' hight='100' width='100'/>";

            Reporter.log("该bug的订单号是："+order);
            Reporter.log(html);
            
            try {
				File srcFile = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(picName +".png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
	
    /**
     * 替换字符串
     * @param s 待替换string
     * @return 替换之后的string
     */
	private String escapeString(String s)
    {
        if (s == null)
        {
            return null;
        }

        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < s.length(); i++)
        {
            buffer.append(escapeChar(s.charAt(i)));
        }
        return buffer.toString();
    }
    
    /**
     * 将\字符替换为\\
     * @param character 待替换char
     * @return 替换之后的char
     */
    private String escapeChar(char character)
    {
        switch (character)
        {
            case '\\': return "\\\\";
            default: return String.valueOf(character);
        }
    }

}
