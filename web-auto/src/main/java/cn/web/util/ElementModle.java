package cn.web.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ElementModle {
	
	final static	Logger logger=Logger.getLogger(ElementModle.class);
	// 返回元素定位类型
	public static By getEleModle(String elementExpression){
		
		String locatorType = elementExpression.split("<")[0];
		String locatorValue = elementExpression.split("<")[1];
		
		if (locatorType.toLowerCase().equals("xpath") || locatorType.toLowerCase().equals(""))
			return By.xpath(locatorValue);

		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);

		else if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);

		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);

		else if ((locatorType.toLowerCase().equals("linkText")) || (locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);

		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);

		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);

		else{
			Assert.fail("没有找到符合要求的元素定位方式");
			}
		return null;
	}
}
