package cn.web.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;


/**
 * 失败重试
 * @author huangjun
 * 
 */
public class RetryListener implements IAnnotationTransformer{
	
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation testannotation, Class testClass,
            Constructor testConstructor, Method testMethod) {
		
		IRetryAnalyzer iRetryAnalyzer= testannotation.getRetryAnalyzer();
		
		if(iRetryAnalyzer == null){
			testannotation.setRetryAnalyzer(OverrideIReTry.class);
		}
		
	}
}
