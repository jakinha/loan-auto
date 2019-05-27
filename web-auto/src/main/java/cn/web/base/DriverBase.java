package cn.web.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cn.web.util.Constant;
import cn.web.util.PropertyUtil;

/**
 * 初始化driver
 * @author Administrator
 *
 */
public class DriverBase {
	
	private WebDriver driver;
	
	public WebDriver installDriver() {
		
		PropertyUtil pro = new PropertyUtil(Constant.PUBLIC_CONFIG);
		String browerType = System.getProperty("browerType");
		if(!StringUtils.isNotBlank(browerType)){
			browerType = "chrome_browser";
		}
		
		if(browerType.equals("ie_browser")){
			System.setProperty("webdriver.ie.driver",pro.getproperValue(browerType).replace("*", "\\"));
//			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//	        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//	        capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com");
	        driver = new InternetExplorerDriver();

		}
		else if(browerType.equals("firefox_brower")){

			// 火狐浏览器
			 System.setProperty("webdriver.firefox.bin",pro.getproperValue(browerType).replace("*", "\\")); 
			 ProfilesIni allProfiles = new ProfilesIni();
			 FirefoxProfile profile = allProfiles.getProfile("default");
			 driver= new FirefoxDriver(profile);

		}
		else{
			
			 System.setProperty("webdriver.chrome.driver",pro.getproperValue(browerType).replace("*", "\\"));
			 ChromeOptions options = new ChromeOptions();
			 options.addArguments("disable-infobars");
			 driver=new ChromeDriver(options);
		}
		 driver.manage().window().maximize();
		 return driver;

	}
}
