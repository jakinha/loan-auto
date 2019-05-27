package cn.web.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;

import cn.web.base.ReadJsonBase;
import cn.web.common.constant.GetElemFilePropertyUtil;
import cn.web.util.KeyBoard;
import cn.web.util.PropertyUtil;
import cn.web.util.SeleniumUtil;


public class ImgUploadUtils {
	
    private static final Logger logger = Logger.getLogger(ImgUploadUtils.class.getName());
    
    static PropertyUtil iframe_pro = GetElemFilePropertyUtil.ifmpro;
    
	static PropertyUtil pro = GetElemFilePropertyUtil.creditSysElePro;
    
    /**业务流程--影像上传**/
    public static void imgUpload(SeleniumUtil selenium,String checkPoint){
    	
    	WindowsUtils windowsUtils = new WindowsUtils(selenium);
    	windowsUtils.initWindows(2);

		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(checkPoint, "$.wintitle_ch"));
		selenium.switchFrameExec(iframe_pro.getproperValue("ObjectList"));

		if(!System.getProperty("productType").contains("中粮") && !System.getProperty("productType").contains("信托")){
			//影像上传
			selenium.click(selenium.getWebElement(pro.getproperValue("imgPlatform")),"影像平台");
			selenium.exceptionAlert("影像平台");
			
			SeleniumUtil.sleep(10);
			ImgUploadUtils.uploadImg("hxxc-image-upload");//信贷的影像上传的exe
			SeleniumUtil.sleep(40);
		}
		
		Alert alert = selenium.clickDisplayAlert(selenium.getWebElement(pro.getproperValue("fullSubmit")),11,"提交按钮");

		selenium.alertConfirm(alert,"完全断言", ReadJsonBase.readJson(checkPoint, "$.saveSucess"));

		windowsUtils.initWindows(3);
		windowsUtils.switchTitleDiffrent(ReadJsonBase.readJson(checkPoint, "$.wintitle_submit"));

		selenium.click(selenium.getWebElement(pro.getproperValue("fullSubmit2")),"提交列表选择的提交按钮");
		alert = selenium.alertIsDisplay();
		selenium.alertConfirm(alert,"-1", null);
		SeleniumUtil.sleep(1.5);
		KeyBoard.enter();
	}

    
    /**影像上传
     * @param uploadExe  autoIt生成的可执行文件
     * 
     * **/
    public static void uploadImg(String uploadExe) {
		
		String path = System.getProperty("user.dir");
		String dir = File.separator+"img-upload"+File.separator;
		String param1 = path + dir + "id1.png";
		String param2 = path + dir + "id2.png";
		logger.info("path:"+path);
		logger.info("apram1:"+param1);
		String command = path + dir + uploadExe+".exe " + param1 + " " + param2;

		ImgUploadUtils.exec(command, "Huaxia.ECM", 15);

	}
    
    /**影像上传
     * 
     * @param uploadExe autoIt生成的可执行文件
     * @param processNode 流程节点
     */
    public static void uploadImg(String uploadExe,String processNode) {
		
    	String path = System.getProperty("user.dir");
		String dir = File.separator+"img-upload"+File.separator;
		String param1 = path + dir + "id1.png";
		String param2 = path + dir + "id2.png";
		String command = path + dir + uploadExe+".exe " + processNode +" " + param1 + " " + param2;

		ImgUploadUtils.exec(command, "Huaxia.ECM", 15);

	}
    
    
    /**
     * Execute the specified string command
     *
     * @param command command
     * @param timeout
     * @return
     */
    private static void exec(String command, String process, int timeout) {
        killProcess(process);
        try {
            Runtime.getRuntime().exec(command);
            logger.info("Executed the command: " + command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        	 SeleniumUtil.sleep(2*timeout);
        	 killProcess(process);
        }
/*        int count = 0;
        while (getPID(process).size() > 0 && count < timeout) {
        	
            SeleniumUtil.sleep(2000);
           
            if (count == timeout) {
                killProcess(process);
            }
            
            count++;
        }*/
    }

    /**
     * Kill the specified process
     *
     * @param processName
     * @return
     */
    private static void killProcess(String processName) {
        List<String> pids = getPID(processName);
        for (String pid : pids) {
            try {
                Runtime.getRuntime().exec("tskill " + pid);
                logger.info("Kill the pid " + pid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the specified process
     *
     * @param processName
     * @return
     */
    @SuppressWarnings("resource")
	private static List<String> getPID(String processName) {
        List<String> pids = new ArrayList<String>();
        String cmd = "tasklist /nh /fi \" imagename eq " + processName + " \" ";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            Scanner in = new Scanner(new InputStreamReader(process.getInputStream()));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(processName)) {
                    int end = line.indexOf("Console");
                    String pid = line.substring(end - 7, end).trim();
                    pids.add(pid);
                    logger.info("Get the pid: " + pid);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pids;
    }
}
