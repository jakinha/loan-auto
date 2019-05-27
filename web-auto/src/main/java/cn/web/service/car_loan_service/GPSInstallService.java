package cn.web.service.car_loan_service;

import cn.web.common.carloan.LoanFrontPubOperat;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.page.car_loan_page.GPSInstallPage;
import cn.web.util.SeleniumUtil;

public class GPSInstallService extends GPSInstallPage{

	public GPSInstallService(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}
	
	/**待安装队列**/
	public void gpsPendingQueue(){
		
		LoanFrontPubOperat.leftNavigation(selenium, "gps_manage", "car_iframe_112105", testDataModel.getDescription());
		
		SeleniumUtil.sleep();
		
		selenium.input(getSearchOrderNo(), basicTestDataBean.getApplyNum(), "待安装搜索框-订单号："+basicTestDataBean.getApplyNum());

		selenium.jsClick(getSearchBtn(), "搜索按钮");
		selenium.jsClick(getInitProcess(), "安装处理按钮");
		
	}
	
	/**填写安装gps的信息**/
	public void wirteGPSInfoSubmit(){
		
		selenium.input(getGpsnNo(), "gps1111", "gps编号");
		selenium.input(getInstallLocation(), "安装在一个地方", "安装位置");
		selenium.jsClick(getDeviceSelect(), "设备型号下拉");
		selenium.jsClick(getWired(), "有线");
		selenium.jsClick(getFactorySelect(), "厂家下拉");
		selenium.jsClick(getFactory(), "厂家");
		selenium.jsClick(getWhetherCompletePositionSelect(), "是否完成定位下拉");
		selenium.jsClick(getWhetherCompletePosition(), "完成定位选项");
		selenium.jsClick(getAddDevice(), "添加设备按钮");
		selenium.jsClick(getBlurDetermine(), "添加设备弹出的确定按钮");
		SeleniumUtil.sleep(1);
		selenium.jsClick(getEvaluateSubmit(),"提交按钮");
		selenium.dclick(getBlurDetermine(),GetElemFilePropertyUtil.checkPointPro.getproperValue("check_submit_sucess"),"提交按钮弹出的确定按钮");
	}

}
