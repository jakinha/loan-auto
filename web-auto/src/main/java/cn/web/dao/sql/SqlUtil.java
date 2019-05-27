package cn.web.dao.sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import cn.web.common.TaskSchedulingCenter;
import cn.web.common.constant.DataBaseServiceConstant;
import cn.web.dao.multiple.SqlAction;
import cn.web.util.Constant;
import cn.web.util.RandomUtil;
import cn.web.util.ReadConfXml;


public class SqlUtil {
	
	private String testEnv = System.getProperty("testEnv");//判断执行哪个测试环境
	
	private Logger logger = Logger.getLogger(SqlUtil.class);
	
	/**从数据库-huaxia_datacenter_collect 查询表rh_white_list 是否存在生产的身份证**/
	public int idCardIsExist(String idCard){
		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCreditService,DataBaseServiceConstant.uatCreditService);

		String sql="select * from rh_white_list where idcard='"+idCard+"'";
		return SqlAction.queryData(sql, dbServerInfo, DataBaseServiceConstant.creditDBName);
	}
	
	/**
	 * 进单之前，在表中插入客户意向信息
	 * @param name	姓名
	 * @param idCard	身份证
	 * @param phone	手机号
	 */
	public void customerApplicationIntentions(String name,String phone,String idCard){
	
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCreditSystemService,DataBaseServiceConstant.huaXiaCaiService);
		
		long id = Long.parseLong(RandomUtil.randomNum(7));
		
		String sql[] ={"INSERT INTO front_customer_intention (id,customer_name,customer_id_card,source_type,delete_flag,customer_phone) VALUES('"+id+"','"+name+"','"+idCard+"','2','1','"+phone+"')"}; 
		
		SqlAction.updata(sql, dbServerInfo, DataBaseServiceConstant.huaxiaCaiAppDBName);

	}
	
	/**
	 * 插入解决使用华夏助手app的提示和用户是黑名单的问题 --3315
	 */
	public void hxHelperApp(String ClientName, String idCard, String state) {
		
		String sql_hx_appHelper[] = { "INSERT INTO rh_white_list (name,idcard,state) VALUES('" + ClientName + "','"
				+ idCard + "'," + "'" + state + "')" };
		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCreditService,DataBaseServiceConstant.uatCreditService);
		
		SqlAction.updata(sql_hx_appHelper, dbServerInfo, DataBaseServiceConstant.creditDBName);
	}

	
	/**
	 * 处理目前初审获取不到单子问题
	 */
	public void firstViewGetOrder(String orderNo){
		
		String sql[] ={"update business_apply set isVerify='1' where SERIALNO='"+orderNo+"'"};
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCreditSystemService,DataBaseServiceConstant.uatCreditSystemService);

		SqlAction.updata(sql,dbServerInfo, DataBaseServiceConstant.creditSysDBName);
	}
	
	/**等待订单跑定时器到电话预约处**/
	public int getTelReserveOrder(String order,String phaseName){
		
		String sql ="SELECT * FROM flow_task WHERE OBJECTNO ='"+order+"' AND PHASENAME ='"+phaseName+"'";
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCreditSystemService,DataBaseServiceConstant.uatCreditSystemService);

		return SqlAction.queryData(sql, dbServerInfo, DataBaseServiceConstant.creditSysDBName);
	}
	
	
	/**车架号重复使用**/
	public void carFrame(String carFrame){
		
		String sql[] ={"UPDATE business_apply ba,business_car_info bci SET ba.del_flag=1 WHERE ba.business_no = bci.business_no AND bci.delete_flag = 0 AND bci.car_frame_number = '"+carFrame+"' AND ba.workflow_node_id != 'End';"};
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
		SqlAction.updata(sql, dbServerInfo, DataBaseServiceConstant.carLoanSysDBName);
	}
	
	/**
	 * 查询新增修改的联系人信息
	 * @param orderNo	订单号
	 * @param contactType	要查询的联系人关系类型
	 * @return 新增的信息
	 */
	public String getAddModify(String orderNo,String contactType,String phoneNum){
		
		String sql = "SELECT ci.contact_mobile FROM contact_info ci WHERE ci.relation_id IN ("+
			 "SELECT cri.id FROM contact_relation_info cri WHERE cri.business_no = '"+orderNo+"' AND cri.contact_relation='"+contactType+"' AND contact_mobile='"+phoneNum+"')";

		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
	
		return SqlAction.query(sql,"contact_mobile" , dbServerInfo, DataBaseServiceConstant.carLoanSysDBName);
	}
	/**
	 * 查询直系亲属的被修改后的名字和配偶的名字和身份证
	 * @param orderNo	订单号
	 * @param spouseName	配偶姓名
	 * @return
	 */
	public List<String> getAddModify(String orderNo,String spouseName){
		
		String sql = "SELECT contact_name,cert_no FROM contact_relation_info WHERE business_no='"+orderNo+"'";
		String sqlCertNo = "SELECT cert_no FROM contact_relation_info WHERE business_no='"+orderNo+"' and contact_name='"+spouseName+"'";

		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
	
		String field[] = {"contact_name","cert_no"};
		
		List<String> listInfo = SqlAction.moreQuery(sql,field, dbServerInfo, DataBaseServiceConstant.carLoanSysDBName);
		String certNo = SqlAction.query(sqlCertNo, "cert_no", dbServerInfo, DataBaseServiceConstant.carLoanSysDBName);
		listInfo.add(certNo);
		return listInfo;
	}
	
	/**
	 * 车贷资金放款
	 * @param orderNo
	 * @param loanTime 放款时间
	 */
	private void fundLending(String orderNo,String loanTime){
		
//		String sql[] ={"update tm_contractloan  set loan_date=DATE_SUB(curdate(),INTERVAL 0 DAY),loan_status=1 where loan_contract_id='"+orderNo+"'"};
		
		String sql[] ={"update tm_contractloan  set loan_date='"+loanTime+"',loan_status=1 where loan_contract_id='"+orderNo+"'"};
	
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCreditSystemService,DataBaseServiceConstant.fundSystemService);
		
		SqlAction.updata(sql,dbServerInfo,DataBaseServiceConstant.huaXiaCtmCarDBName);
	}
	
	/**车贷 放款、还款 sql**/
	public void carLoanrepayment(String orderNo){
		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
	
		//找到日切时间
		String dayCutTimeSql = "SELECT cur_deduct_date FROM task_ploan_setup";
		
		String dayCutTime = SqlAction.query(dayCutTimeSql,"cur_deduct_date" , dbServerInfo, DataBaseServiceConstant.carLoanRepaymentDBName);
		
		String loanTime = formatDateProcess(dayCutTime,-2);//放款时间

		fundLending(orderNo,loanTime);
		
		String thirdPeriodLoanTime = formatDateProcess(loanTime,3);//第三期还款时间

		//放款通知
		new TaskSchedulingCenter().carFundsTaskScheduling(testEnv);
		
		logger.info("准备执行车贷还款操作......");
		
		String sql[] ={"update acct_loan set surplus_term='1' , business_date='"+dayCutTime+"', next_paydate='"+thirdPeriodLoanTime+"',loan_status='0', overdue_days='0',overdue_balance='0',interest_balance='0',fineinte_balance='0' where serialno='"+orderNo+"'",
						"update acct_payment_schedule  set  real_interest='990', finish_date='2019-03-15',`status`='010',count_penalty_flag='1' where objectno='"+orderNo+"' limit 2"
					};

		SqlAction.updata(sql,dbServerInfo,DataBaseServiceConstant.carLoanRepaymentDBName);
	
		logger.info("车贷还款操作完成.");
	}
	
	/**设置车贷贷后的还款表的合同状态为：10,并推送给贷前**/
	public void  setCarLoanRepaymentDataContractStatus(String orderNo){
		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
		
		String sql[] = {"update acct_loan set loan_status='10', is_caloverdue='2' where serialno='"+orderNo+"'"};
		
		SqlAction.updata(sql,dbServerInfo,DataBaseServiceConstant.carLoanRepaymentDBName);
		
		logger.info("车贷贷后的还款合同状态推送给贷前完成！");
	}
	
	public static void main(String[] args) {

		new SqlUtil().carLoanrepayment("H014201904080009");
		
	}
	
	/**
	 * 设置合同状态
	 * @param orderNo
	 * 				订单号
	 * @param contractStatus
	 * 				合同状态code
	 */
	public void setContractStatus(String orderNo,int contractStatus){

		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
		
		String sql[] = {"UPDATE business_apply SET contract_status= '"+String.valueOf(contractStatus)+"' WHERE business_no='"+orderNo+"'"};
		
		SqlAction.updata(sql,dbServerInfo,DataBaseServiceConstant.carLoanSysDBName);
	}
	
	/**
	 * 获取车贷订单状态
	 * @param orderNo
	 * 				订单号
	 * @param fieldName
	 * 				要查询的字段
	 */
	public String getOrderStatusName(String orderNo,String fieldName){

		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
		
		String sql = "SELECT "+fieldName+" FROM business_apply WHERE business_no='"+orderNo+"'";
		
		return SqlAction.query(sql, fieldName, dbServerInfo, DataBaseServiceConstant.carLoanSysDBName);
		
	}
	
	/**
	 * 删除订单,设置 del_flag =1 
	 * 
	 * @param carFrameNum
	 * 					车架号
	 */
	public void deleteEntryOrder(String carFrameNum){

		
		Map<String, String> dbServerInfo = getDBServerInfo(DataBaseServiceConstant.sitCarSystemService,DataBaseServiceConstant.uatCarSystemService);
		
		String sql[] = {"UPDATE business_apply ba SET ba.del_flag = 1 WHERE ba.business_no in(select car.business_no from business_car_info car where car.car_frame_number='"+carFrameNum+"') and ba.order_status_name ='录入'"};

		SqlAction.updata(sql,dbServerInfo,DataBaseServiceConstant.carLoanSysDBName);
	}
	
	/**获取不同环境的数据库服务信息,参数依次是代表环境：sit01/sit02,uat02**/
	private Map<String, String> getDBServerInfo(String... dbserver){
		
		Map<String, String> jdbc_map =new ConcurrentHashMap<String, String>();

		if("sit-01".equals(testEnv)){
			jdbc_map = ReadConfXml.getInstence().getMultValue(Constant.MYSQL_CONNECT_INFO_PATH,null,dbserver[0]);
		}
		else if("uat-02".equals(testEnv)){
			jdbc_map = ReadConfXml.getInstence().getMultValue(Constant.MYSQL_CONNECT_INFO_PATH,null,dbserver[1]);
		}
		else{

			jdbc_map = ReadConfXml.getInstence().getMultValue(Constant.MYSQL_CONNECT_INFO_PATH,null,dbserver[0]);
		}
		if(jdbc_map.get("pwd").toString().contains("*")){
			jdbc_map.put("pwd", jdbc_map.get("pwd").toString().replace("*", "&"));
		}
		
		return jdbc_map;
	}
	
	/**
	 * 日期时间处理
	 * @param dateTime	日期
	 * @param Month		月份往前或往后的份数 ,正数是往后推,负数是往前推
	 * @return
	 */
	private String formatDateProcess(String dateTime,int Month){

		try {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse(dateTime);
			
			Calendar calstart = Calendar.getInstance();
			calstart.setTime(date);
			calstart.add(Calendar.MONTH, Month);
			Date time = calstart.getTime();
			return simpleDateFormat.format(time);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
