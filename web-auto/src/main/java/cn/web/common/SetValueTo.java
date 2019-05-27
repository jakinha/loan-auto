package cn.web.common;

import cn.web.dto.BasicTestDataBean;
import cn.web.util.ElementModle;
import cn.web.util.SeleniumUtil;

public class SetValueTo {
	

	 /**
	 * 设置<订单编号>值,订单号是从页面html的标签属性中获得，并单击该记录
	 * @param selenium
	 * @param element
	 * @param basic
	 */
	public static void setValueToBean(SeleniumUtil selenium ,String element,BasicTestDataBean basic){

		selenium.displayWait(ElementModle.getEleModle(element));
		String	value=selenium.getPropertyValue(element, "value");
		selenium.click(selenium.getWebElement(element),"订单号选择项");
		selenium.switchPrentFrame("F");
		basic.setApplyNum(value);//订单编号关联
	}
	
}
