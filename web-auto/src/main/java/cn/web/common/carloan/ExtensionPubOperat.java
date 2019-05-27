package cn.web.common.carloan;

import cn.web.base.PageBase;
import cn.web.util.SeleniumUtil;

/**
 * 展期申请公共的操作
 * @author huangjun
 *
 */
public class ExtensionPubOperat extends PageBase{
	
	public ExtensionPubOperat(){
		
	}
	

	/**左侧导航栏操作**/
	public void leftNavigation(SeleniumUtil seleniumUtil,String leftSubMenuElement,String leftSubMenuName){
		
		seleniumUtil.refreshPage();
		
		seleniumUtil.jsClick(seleniumUtil.getElement(carExtensionElementProperty.getproperValue("car_loan_extension_menu")), "车贷贷前展期");
		
		seleniumUtil.jsClick(seleniumUtil.getElement(leftSubMenuElement), leftSubMenuName);

		//跳转iframe
		LoanFrontPubOperat.processMuiltISameIframe(seleniumUtil, leftSubMenuName+"-待处理", "跳转 "+leftSubMenuName+" 的iframe");

	}
}
