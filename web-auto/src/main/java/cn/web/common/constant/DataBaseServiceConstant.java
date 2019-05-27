package cn.web.common.constant;

/**
 * mysql服务器和数据库
 * 
 * 对应着mysql_connect.xml配置文件里的node节点
 * 
 * @author huangjun
 *
 */
public class DataBaseServiceConstant {

	/**SIT 信 贷 数 据 库 服 务 **/
	public static final String sitCreditSystemService = "mysql_sit";
	public static final String sit2CreditSystemService = "mysql_sit2";
	/**UAT 信 贷 数 据 库 服 务 **/
	public static final String uatCreditSystemService="mysql_credit_203";
	//信贷 数据库
	public static final String creditSysDBName = "credit_sys";
	//车贷资金库
	public static final String huaXiaCtmCarDBName = "huaxia_ctm_car";
	

	
	/**UAT  huaxia_cai_app等 数 据 库 的 数 据 库 服 务 **/
	public static final String huaXiaCaiService = "mysql_huaxia_cai_3315";
	//数据库
	public static final String huaxiaCaiAppDBName = "huaxia_cai_app";

	
	/**SIT 车 贷 数 据 库 服 务 **/
	public static final String sitCarSystemService = "mysql_carloan_3307";
	/**UAT 车 贷 数 据 库 服 务 **/
	public static final String uatCarSystemService = "mysql_carloan_3308";
	//车贷数据库
	public static final String carLoanSysDBName = "credit_sys_carloan";
	//车贷还款数据库
	public static final String carLoanRepaymentDBName = "huaxia_post_carloan";
	
	
	/**UAT 资 金 系 统   数 据 库 服 务 **/
	public static final String fundSystemService = "mysql_fundsys_3307";
	//资金系统 数据库
	public static final String fundSysDBName = "fundsystem";
	
	/** SIT 征信2.0 数 据 库 服 务 **/
	public static final String sitCreditService = "mysql_credit_3306";
	/** UAT 征信2.0 数 据 库 服 务 **/
	public static final String uatCreditService = "mysql_credit_3308";
	//征信2.0 数 据 库
	public static final String creditDBName = "huaxia_datacenter_collect";
}
