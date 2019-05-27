package cn.web.common.constant;


import cn.web.util.PropertyUtil;

/**
 * 获取 PropertyUtil 对象 ，该对象加载了元素定位的文件
 * 
 * @author huangjun
 *
 */
public class GetElemFilePropertyUtil {
	
	/**车贷 贷前元素定位文件**/
	public static final PropertyUtil carSysElePro = new PropertyUtil(WebPlatformConstant.CAR_LOAN_SYS_ELE_PATH);
	
	/**车贷 展期元素定位文件**/
	public static final PropertyUtil carExtensionElePro = new PropertyUtil(WebPlatformConstant.CAR_LOAN_EXTENSION_ELE_PATH);
	
	/**信贷元素定位文件**/
	public static final PropertyUtil creditSysElePro = new PropertyUtil(WebPlatformConstant.LOAN_SYS_ELE_PATH);
	
	/**信托系统元素定位文件**/
	public static final PropertyUtil trustSysElePro = new PropertyUtil(WebPlatformConstant.TRUST_ELE_PATH);
	
	/**公共的iframe的元素定位文件**/
	public static final PropertyUtil ifmpro = new PropertyUtil(WebPlatformConstant.IFRAME_ELE_PATH);
	
	/**公共的检查点元素定位文件**/
	public static final PropertyUtil checkPointPro = new PropertyUtil(WebPlatformConstant.CHECK_POINT);

}
