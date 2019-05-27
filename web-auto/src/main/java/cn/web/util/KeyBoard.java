package cn.web.util;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class KeyBoard{

	final static  Logger logger =Logger.getLogger(KeyBoard.class);

	// 模拟键盘按enter键
	public static  void enter() {
		SeleniumUtil.sleep(0.7);
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			logger.info("模拟键盘Enter键成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 模拟键盘按tab键
	public static void table(){
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void tableAndShfit(){
		try {
			Robot robot = new Robot();
			
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *复制粘贴
	 * @param string	要上传的文件路径
	 * @throws Exception
	 */
	public static void setAndctrlVClipboardData(String LoadFilePath){
		SeleniumUtil.sleep(1.5);
		try {
			Robot robot = new Robot();
			StringSelection stringselection = new StringSelection(LoadFilePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			SeleniumUtil.sleep(1.5);
			enter();
			SeleniumUtil.sleep(1);
			logger.info("文件上传成功:" + LoadFilePath );
		} catch (Exception e) {
			Assert.fail("文件上传失败: " + LoadFilePath + " 错误信息:" + e.getMessage());
		}
	}
}
