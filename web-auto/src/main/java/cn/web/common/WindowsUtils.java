package cn.web.common;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cn.web.util.ElementModle;
import cn.web.util.KeyBoard;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;

public class WindowsUtils {

	private final Logger log = Logger.getLogger(WindowsUtils.class);

	private WebDriver driver;
	private String mainWindow = null;
	private Set<String> windows = null;
	private SeleniumUtil selenium;

	public WindowsUtils(SeleniumUtil selenium) {
		driver = selenium.getDriver();
		this.selenium = selenium;
	}

/*	*//**
	 * 获取当前所有窗口的
	 * 
	 * @param selenium
	 * @param windowCount
	 *            窗口数
	 * @return 返回窗口对象
	 *//*
	public void getWindows(int windowCount) {

		setWindows(windowCount);
	}*/

	/**
	 * 切换窗口后不做任何操作就关闭，然后返回到主窗口界面进行操作
	 * 
	 * @param driver
	 * @param checkWinTitle
	 *            断言窗口标题
	 */
	public void backMainWindow(String checkWinTitle) {
		SeleniumUtil.sleep(2);
		WebDriver driver = selenium.getDriver();
		String mainWin = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String win : windows) {
			driver.switchTo().window(win);
			String title = driver.getTitle();
			if (title.contains(checkWinTitle)) {
				try {
					driver.findElement(By.xpath("//a[text()='关闭']")).click();
					log.info("成功关闭窗口！！！！！");
					break;
				} catch (Exception e) {
					e.printStackTrace();
					MyAssert.myAssertFalse("没有单击到关闭按钮");
				}
			}
		}
		driver.switchTo().window(mainWin);
	}

	/**
	 * 切换窗口后不做任何操作,关闭所有窗口
	 * 
	 * @param driver
	 * @param checkWinTitle
	 *            断言窗口标题
	 */
	public void closeAllWin(String checkWinTitle) {
		SeleniumUtil.sleep(2);
		WebDriver driver = selenium.getDriver();
		Set<String> windows = driver.getWindowHandles();
		for (String win : windows) {
			driver.switchTo().window(win);
			String title = driver.getTitle();
			if (title.contains(checkWinTitle)) {
				try {
					driver.findElement(By.xpath("//a[text()='关闭']")).click();
					log.info("成功关闭窗口！！！！！");
					SeleniumUtil.sleep(0.5);
					KeyBoard.enter();
					break;
				} catch (Exception e) {
					e.printStackTrace();
					MyAssert.myAssertFalse("没有单击到关闭按钮");
				}
			}
		}
	}

	/**
	 * 切换到主窗口界面
	 */
	public void getMainWindow() {

		try {
			driver.switchTo().window(mainWindow);
			log.info("成功跳转到主窗口");
		} catch (Exception e) {
			e.printStackTrace();
			;
			Assert.fail("跳转主窗口失败");
		}
	}

	/**
	 * 跳转只有两个窗口的，并且目标窗口是无窗口标题的 完全标题比较
	 **/
	public void switchNullTitle() {

		if (null == windows) {
			Assert.fail("窗口的数量未进行初始化操作");
		}
		if (windows.size() > 1) {
			int cont = 1;
			for (String window : windows) {

				driver.switchTo().window(window);

				if (driver.getTitle().equals("")) {
					log.info("窗口切换成功！！，窗口标题是：" + driver.getTitle());
					break;
				}
				cont++;
			}
			if (cont > windows.size()) {
				MyAssert.myAssertFalse("没有找到目标窗口！！！！");
			}
		} else {
			MyAssert.myAssertFalse("窗口的数量存在问题,窗口数：" + windows.size() + "个");
		}
	}

	/**
	 * 跳转所有不存在标题相同的窗口 包含比较标题
	 **/
	public void switchTitleDiffrent(String checkTitle) {

		if (null == windows) {
			Assert.fail("窗口的数量未进行初始化操作");
		}
		if (windows.size() > 1) {
			int cont = 1;

			for (String window : windows) {

				driver.switchTo().window(window);
				String winTitle = driver.getTitle();

				if (winTitle.contains(checkTitle)) {// 使用contains主要是因为标题中有空格
					log.info("窗口切换成功！！,窗口标题是：" + winTitle);
					break;
				}
				System.out.println("窗口标题是:[" + winTitle.trim() + "] 目标窗口找的不对！！！！重新再找一次！！！！！！！！！！");
				cont++;
			}
			if (cont > windows.size()) {

				MyAssert.myAssertFalse("没有找到目标窗口！！！！");
			}

		} else {

			MyAssert.myAssertFalse("窗口的数量存在问题,窗口数：" + windows.size() + "个");
		}
	}

	/** 跳转存在标题相同的窗口,并且做了跳转iframe操作 **/
	public void switchTitleExistSame(String checkTitle, String iframe, String pageText) {

		if (null == windows) {
			Assert.fail("窗口的数量未进行初始化操作");
		}
		if (windows.size() > 1) {
			int cont = 1;
			for (String window : windows) {
				driver.switchTo().window(window);
				String winTitle = driver.getTitle();
				if (winTitle.contains(checkTitle)) {// 使用contains主要是因为标题中有空格
					try {
						switchFrame(iframe);
						SeleniumUtil.sleep(1);
						if (selenium.getDriver().getPageSource().contains(pageText)) {
							log.info("窗口切换成功！！,窗口标题是：" + winTitle);
							break;
						} else {
							cont++;
							System.out.println("页面内容不包含预期页面文本内容:[" + pageText + "]目标窗口重新再找一次！！！！！！！！！！");
						}

					} catch (Exception e) {
						cont++;
						System.out.println("窗口标题是:" + winTitle + " ,目标窗口找的不对！！！！重新再找一次！！！！！！！！！！");
					}
				} else {
					cont++;
				}
			}
			if (cont > windows.size()) {

				MyAssert.myAssertFalse("没有找到目标窗口！！！！");
			}
		} else {

			MyAssert.myAssertFalse("窗口的数量存在问题,窗口数：" + windows.size() + "个");
		}
	}
	
	/** 初始化主窗口句柄和所有窗口句柄 **/
	public Set<String> initWindows(int windwosCount) {
		SeleniumUtil.sleep(2);
		int i = 0;
		mainWindow = driver.getWindowHandle();
		windows = driver.getWindowHandles();
		log.info("当前窗口总数是：" + windows.size());
		while (windows.size() != windwosCount) {
			SeleniumUtil.sleep(1);
			windows = driver.getWindowHandles();
			i++;
			if (i > 5) {
				selenium.exceptionAlert("找了5次都没有出现目标窗口,有alert异常提示");
			}
		}
		return windows;
	}

	private void switchFrame(String iframes) throws Exception {

		String iframeArry[] = iframes.split(",");
		WebElement frame;

		SeleniumUtil.sleep(3);
		for (int i = 0; i < iframeArry.length; i++) {
			SeleniumUtil.sleep(1);
			frame = driver.findElement(ElementModle.getEleModle(iframeArry[i]));
			driver.switchTo().frame(frame);
		}
		log.info("iframe 跳 转 成 功:" + iframes);
	}

}
