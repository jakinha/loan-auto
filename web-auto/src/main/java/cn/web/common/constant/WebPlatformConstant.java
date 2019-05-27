package cn.web.common.constant;

import java.io.File;


/**
 * 配置文件路径
 * @author huangjun
 *
 */
public class WebPlatformConstant {
	
	/**src/main/resources目录**/
	public static final String RESOURCES = "src"+File.separator+"main"+File.separator+"resources"+File.separator;
	
	/**测试机ip和测试机上得tomcatwebapp路径**/
	public static final String TEST_NODE_IP = "192.168.14.129:8099";
	public static final String TEST_NODE_TOMCAT_WEBAPP_PATH = "E:"+File.separator+"apache-tomcat-8.5.15"+File.separator+"webapps"+File.separator+"ROOT"+File.separator;
	public static final String IE_TEST_NODE_IP = "192.168.15.62:8088";
	public static final String IE_TEST_NODE_TOMCAT_WEBAPP_PATH = "C:"+File.separator+"Program Files (x86)"+File.separator+"apache-tomcat-8.5.15-2"+File.separator+"webapps"+File.separator+"ROOT"+File.separator;
	/**信托产品部分名字**/
	public static final String PRODUCT = "信托";
	/**信托产品进行签约绑卡的接口地址**/
	public static final String trustProductTieCardApiUrl = "http://192.168.11.133:8888/";
	
	//信贷登录成功后的检查点
	public static final String LOGIN_SUCESS_CHECK_POINT="安全退出";
	//车贷登录成功后的检查点
	public static final String CAR_LOAN_LOGIN_SUCESS_CHECK="首页";
	
	/**公共断言  文件路径**/
	public static final String CHECK_POINT = "element"+File.separator+"check-point.properties";
	
	/**个贷系统    元素文件路径**/
	public static final String LOAN_SYS_ELE_PATH= "element"+File.separator+"p2p"+File.separator+"loanSys-element.properties";
	
	/**车贷贷前   元素文件路径**/
	public static final String CAR_LOAN_SYS_ELE_PATH= "element"+File.separator+"car-loan"+File.separator+"car-loan-element.properties";
	
	/**车贷展期   元素文件路径**/
	public static final String CAR_LOAN_EXTENSION_ELE_PATH= "element"+File.separator+"car-loan"+File.separator+"car-loan-extension-element.properties";
	
	/**信托系统    元素文件路径**/
	public static final String TRUST_ELE_PATH= "element"+File.separator+"trust-sys"+File.separator+"trust-sys-element.properties";
	
	/**任务调度中心    元素文件路径**/
	public static final String SCHEDU_CENTER_PATH= "element"+File.separator+"schedu-center"+File.separator+"schedu-center-element.properties";
	
/***********************************************************************************************************************************/	

	/**iframe元素的文件路径**/
	public static final String IFRAME_ELE_PATH= "element"+File.separator+"iframe-element.properties";
	
	
/***********************************************************************************************************************************/

	/**信贷 用例数据**/
	public static final String APPLY_ORDER_DATA = "cases"+File.separator+"credit-sys"+File.separator+"apply-order-data.xml";

	/**车贷 贷前  用例数据**/
	public static final String CAR_LOAN_FRONT_DATA = "cases"+File.separator+"car-loan-sys"+File.separator+"car-loan-front-data.xml";
	
	/**车贷 展期  用例数据**/
	public static final String CAR_LOAN_EXTENSION_DATA = "cases"+File.separator+"car-loan-sys"+File.separator+"car-loan-extension-data.xml";

/***********************************************************************************************************************************/
	
	/**测试环境 sit 、uat 地址和角色用用户配置文件--针对 信贷系统 **/
	public static final String MILIEU_CONFIG = "config"+File.separator+"p2p-role-config.xml";
}
