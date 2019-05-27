package cn.web.common;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import cn.web.util.SeleniumUtil;

/**
 * 此类解决单击时间控件的“今天”需要跳转frame，所以为方便，直接输入当前时间的问题
 * @author huangjun
 *
 */
	
public class InputDate {

	final static Logger log = Logger.getLogger(InputDate.class);
	
	//输入当前时间
	public static void  inputDateNow(SeleniumUtil selenium, String expression){
		Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow=sdf.format(date);
        selenium.cleanAndInput(selenium.getWebElement(expression), dateNow,"当天时间");

	}
	
	//输入明天的时间
	public static void inputTomorrowDate(SeleniumUtil selenium, String expression){
		Date date = new Date();
		Calendar  ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, +1);
		date = ca.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		String dateTime=sdf.format(date);
		selenium.cleanAndInput(selenium.getWebElement(expression), dateTime,"明天时间");
	}

}
