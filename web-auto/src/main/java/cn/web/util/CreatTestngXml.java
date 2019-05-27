package cn.web.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CreatTestngXml {
	
	final static Logger logger = Logger.getLogger(CreatTestngXml.class);
	
	private PropertyUtil property = new PropertyUtil(Constant.MODULE_NAME);
	
	String casePackage = "cn.web.cases.";
	
	/**
	 * 通过jenkins配置选择要执行的用例,创建testng.xml文件
	 */
	public void creatTestNGXml(String className,String methodKeys) {
		
		if (StringUtils.isNotBlank(methodKeys)) {
			
			String methodKeysArray[] = methodKeys.trim().split(",");

			Document doc = DocumentHelper.createDocument();
			Element root = DocumentHelper.createElement("suite");
			doc.setRootElement(root);
			root.addAttribute("name", "华夏信财");
			root.addAttribute("preserve-order", "true");
			root.addAttribute("parallel", "tests");
			root.addAttribute("thread-count","1");// String.valueOf(className.length)
			
			Element listenersNode = root.addElement("listeners");
			
			Element myListenerNode = listenersNode.addElement("listener");
			myListenerNode.addAttribute("class-name", "cn.web.util.MyListener");
			
//			Element retryListenerNode = listenersNode.addElement("listener");
//			retryListenerNode.addAttribute("class-name", "cn.itcast.util.RetryListener");
			
			Element HTMLReporter = listenersNode.addElement("listener");
			HTMLReporter.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
			Element JUnitXMLReporter = listenersNode.addElement("listener");
			JUnitXMLReporter.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
			
			for (int i = 0; i < methodKeysArray.length; i++) {
				
				String testMethods = property.getproperValue(methodKeysArray[i].toString()).trim();
				String testMethodsArray[] = testMethods.split(",");

				Element test = root.addElement("test");
				test.addAttribute("name",property.getproperValue(methodKeysArray[i]+".Name"));
				test.addAttribute("preserve-order","true");
				
				Element parameterNode = test.addElement("parameter");
				parameterNode.addAttribute("name","modulesCase");
				parameterNode.addAttribute("value",property.getproperValue(methodKeysArray[i]+".Name"));
				
				Element classes = test.addElement("classes");
				Element classNodes = classes.addElement("class");
				
				classNodes.addAttribute("name", casePackage+className);
				Element methodsNode = classNodes.addElement("methods");
				
				for(int j = 0;j<testMethodsArray.length;j++){
					Element include = methodsNode.addElement("include");
					include.addAttribute("name", testMethodsArray[j].trim());
				}
			}
				
			OutputFormat format = new OutputFormat("    ", true);
			format.setEncoding("UTF-8");
			XMLWriter xw = null;
			try {
				xw = new XMLWriter(new FileOutputStream(new File("testng.xml")),format);
				xw.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (xw != null) {
					try {
						xw.close();
						logger.info("testng.xml文件创建成功");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			logger.info("爱心提示,请选择要执行的用例！！！！！！！！！！！！！！！！！");
		}

	}

}