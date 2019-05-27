package cn.web.util;

import java.io.File;

public class Constant {

	/**读取数据库连接池配置文件的路径**/
	public static final String DRUID_POOL_PATH="config"+File.separator+"druid-pool.properties";
	
	/***mysql数据库连接配置**/
	public static final String MYSQL_CONNECT_INFO_PATH="config"+File.separator+"mysql_connect.xml";
	
	/***公共配置路径**/
	public static final String PUBLIC_CONFIG = "config"+File.separator+"config.properties";
	
	/**用例场景功能配置**/
	public static final String MODULE_NAME= "config"+File.separator+"module-name.properties";
	
	
	/*********************ssh远程连接****************************/
	public static PropertyUtil sshProperties = new PropertyUtil("ssh.properties",1);

	public static String sit_01_testbed_order_center = sshProperties.getproperValue("sit_01_testbed_order_center");
	public static String sit_01_ssh_username_order_center = sshProperties.getproperValue("sit_01_ssh_username_order_center");
	public static String sit_01_ssh_password_order_center = sshProperties.getproperValue("sit_01_ssh_password_order_center");

	public static String uat_02_testbed_order_center = sshProperties.getproperValue("uat_02_testbed_order_center");
	public static String uat_02_ssh_username_order_center = sshProperties.getproperValue("uat_02_ssh_username_order_center");
	public static String uat_02_ssh_password_order_center = sshProperties.getproperValue("uat_02_ssh_password_order_center");
	


}
