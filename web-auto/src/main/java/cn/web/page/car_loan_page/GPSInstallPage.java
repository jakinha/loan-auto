package cn.web.page.car_loan_page;

import org.openqa.selenium.WebElement;

import cn.web.base.PageBase;
import cn.web.dto.BasicTestDataBean;
import cn.web.dto.TestDataModel;
import cn.web.util.SeleniumUtil;


public class GPSInstallPage extends PageBase{

	protected GPSInstallPage(TestDataModel testDataModel, SeleniumUtil selenium,
			BasicTestDataBean basicTestDataBean) {
		
		super(testDataModel, selenium, basicTestDataBean);

	}

	//gps管理菜单
	protected WebElement getGPSManage(){
		return selenium.getWebElement(carElementProperty.getproperValue("gps_manage"));
	}
	//待处理订单号搜索框
	protected WebElement getSearchOrderNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("search_order_no"));
	}

	//安装处理按钮
	protected WebElement getInitProcess(){
		return selenium.getWebElement(carElementProperty.getproperValue("init_process"));
	}
	//gps编号
	protected WebElement getGpsnNo(){
		return selenium.getWebElement(carElementProperty.getproperValue("gpsn_no"));
	}
	//安装位置
	protected WebElement getInstallLocation(){
		return selenium.getWebElement(carElementProperty.getproperValue("install_location"));
	}
	//设备号下拉
	protected WebElement getDeviceSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("device_select"));
	}
	//有线
	protected WebElement getWired(){
		return selenium.getWebElement(carElementProperty.getproperValue("wired"));
	}
	//厂家下拉
	protected WebElement getFactorySelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("factory_select"));
	}
	//厂家
	protected WebElement getFactory(){
		return selenium.getWebElement(carElementProperty.getproperValue("factory"));
	}
	//是否完成定位下拉
	protected WebElement getWhetherCompletePositionSelect(){
		return selenium.getWebElement(carElementProperty.getproperValue("whether_complete_position_select"));
	}
	//完成定位
	protected WebElement getWhetherCompletePosition(){
		return selenium.getWebElement(carElementProperty.getproperValue("whether_complete_position"));
	}
	//添加设备
	protected WebElement getAddDevice(){
		return selenium.getWebElement(carElementProperty.getproperValue("add_device"));
	}

}
