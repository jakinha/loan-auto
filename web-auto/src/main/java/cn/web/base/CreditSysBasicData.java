package cn.web.base;

import org.testng.annotations.BeforeClass;
import cn.web.service.credit_service.ApprovalProcessService;
import cn.web.util.InitSSHInfo;

/**
 * 信贷系统
 * @author huangjun
 *
 */
public class CreditSysBasicData extends TestngBase{
	
	public ApprovalProcessService approvalFunction  =  new ApprovalProcessService();
	
	@BeforeClass
	public void beforClass(){
		
		if(System.getProperty("isRestartOrderServer").equalsIgnoreCase("yes")){
			InitSSHInfo.init();
		}
	}
	
	/**使用ie浏览器**/
	@Override
	public void setBrower(){
		System.setProperty("browerType", "ie_browser");
	}
}
