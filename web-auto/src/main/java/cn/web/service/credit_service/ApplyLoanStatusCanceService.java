package cn.web.service.credit_service;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;

import cn.web.base.ReadJsonBase;
import cn.web.common.WindowsUtils;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.ElementModle;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;


/**
 * 信贷系统申请贷款的订单状态操作
 * @author Administrator
 *
 */
public class ApplyLoanStatusCanceService {
	
	static PropertyUtil iframe_pro = GetElemFilePropertyUtil.ifmpro;
	static PropertyUtil pro = GetElemFilePropertyUtil.creditSysElePro;
	
	static Logger logger = Logger.getLogger(ApplyLoanStatusCanceService.class);
	
	/**新增申请后，取消申请**/
	public static void applyLoanCance(TestDataModel testDataBean,SeleniumUtil selenium,BasicTestDataBean basic){
		
		Alert alert;
		ApplyOrderWriteInfoService.applyProcess(selenium,basic,"wait.process.apply","待处理申请");
		ApplyOrderWriteInfoService.search(selenium,pro.getproperValue("loan.name"), basic.getClientName(),"搜索贷款人姓名");

		selenium.click(selenium.getWebElement(pro.getproperValue("applyNum")),"订单号选择项");
		selenium.switchPrentFrame("F");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("cance.apply.loan")),"取消申请");
		
		WindowsUtils windowsUtils = new WindowsUtils(selenium);
		windowsUtils.initWindows(2);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(testDataBean.getCheckPoint(), "$.wintitle_submit"));
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交动作选择列表的提交按钮");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"包含断言","请选择提交原因");

		selenium.click(selenium.getWebElement(pro.getproperValue("main.why.list.select")), "主原因下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("main.why.list")), "主原因");
		selenium.click(selenium.getWebElement(pro.getproperValue("zi.why.list.select")), "子原因下拉");
		selenium.click(selenium.getWebElement(pro.getproperValue("zi.why.list")), "子原因");
		
		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交动作选择列表的提交");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"包含断言","确定提交");
		
		windowsUtils.getMainWindow();//回到主窗口界面
		
		selenium.switchFrameExec(iframe_pro.getproperValue("FirstFrame"));

		selenium.click(selenium.getWebElement(pro.getproperValue("already.process.apply")),"已处理申请");
		ApplyOrderWriteInfoService.search(selenium,pro.getproperValue("loan.name"), basic.getClientName(),"搜索贷款人姓名");
		
		if(selenium.displayWait(ElementModle.getEleModle(pro.getproperValue("applyNum")))){
			logger.info("断言新增申请取消成功！！");
		}
	}

}
