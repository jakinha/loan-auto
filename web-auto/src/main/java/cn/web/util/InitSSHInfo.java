package cn.web.util;

/**
 * 初始化ssh远程连接信息并执行
 * @author Administrator
 *
 */
public class InitSSHInfo {

	public static void init() {
		
		String testEnv;
		String testbed_order_center;
		String username_order_center;
		String password_order_center;
		
		testEnv = System.getProperty("testEnv");
		
		if ("sit-01".equals(testEnv)) {
			testbed_order_center = Constant.sit_01_testbed_order_center;
			username_order_center = Constant.sit_01_ssh_username_order_center;
			password_order_center = Constant.sit_01_ssh_password_order_center;

		} else if ("uat-02".equals(testEnv)) {
			testbed_order_center = Constant.uat_02_testbed_order_center;
			username_order_center = Constant.uat_02_ssh_username_order_center;
			password_order_center = Constant.uat_02_ssh_password_order_center;

		} else {
			testbed_order_center = Constant.sit_01_testbed_order_center;
			username_order_center = Constant.sit_01_ssh_username_order_center;
			password_order_center = Constant.sit_01_ssh_password_order_center;

		}

		SSHClient client = new SSHClient(testbed_order_center, username_order_center, password_order_center);
		try {
			client.execute("/bin/jenkins-consumer.sh");
			SeleniumUtil.sleep(2*60);
			System.out.println("order_center服务重启完成!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
