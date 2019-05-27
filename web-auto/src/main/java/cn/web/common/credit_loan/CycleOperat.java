package cn.web.common.credit_loan;

import cn.web.dao.sql.SqlUtil;
import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;

/**
 * 循环判断操作
 * @author Administrator
 *
 */
public class CycleOperat {
	
	/**
	 * 对flowTask 表进行订单状态的跟踪
	 * @param orderNo	订单号
	 * @param filed	PHASENAME字段（订单流入到的节点）
	 */
	public static void flowTaskTableQuery(String orderNo,String filed){
		SqlUtil sql = new SqlUtil();
		int queryResult ;
		int count=1;
		do{
			SeleniumUtil.sleep(5);
			queryResult = sql.getTelReserveOrder(orderNo,filed);
			count++;
			if(count>36){
				MyAssert.myAssertFalse("wealth服务挂了!! 导致订单超时显示,flowTask表里经过3分钟都还没有显示订单流入到电话预约");
			}
		}
		while(queryResult==0);
	}
}
