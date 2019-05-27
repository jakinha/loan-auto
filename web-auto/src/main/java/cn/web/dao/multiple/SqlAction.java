package cn.web.dao.multiple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;

import cn.web.util.MyAssert;
import cn.web.util.SeleniumUtil;


public  class SqlAction{
	
	static Logger logger = Logger.getLogger(SqlAction.class);

	/**
	 * 更新、删除、插入 数据
	 * 
	 * @param
	 */
	public static void updata(String[] sqlarry, Map<String, String> jdbc_map, String database) {

		PreparedStatement presta = null;
		DruidPoolConnect db = new DruidPoolConnect(jdbc_map, database);
		Connection con = db.getConnection();
		int i=0;
		try {
			for (; i < sqlarry.length; i++) {
				
				SeleniumUtil.sleep();
				logger.info("执行第  "+(i+1)+" 条sql:"+sqlarry[i]);
				presta = con.prepareStatement(sqlarry[i]);
				presta.executeUpdate();
				logger.info("第  "+(i+1)+" 条sql:"+sqlarry[i]+"---执行成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MyAssert.myAssertFalse(sqlarry[i]+"---sql执行失败。。。。");
		} finally {
			close(presta,con);
		}
	}

	/**
	 * 查询某条数据是否存在
	 * 
	 * @param
	 * @return 返回查询的记录个数
	 */
	public static int queryData(String sql, Map<String, String> jdbc_map, String database) {
		PreparedStatement presta = null;
		DruidPoolConnect db = new DruidPoolConnect(jdbc_map, database);
		Connection con = db.getConnection();
		ResultSet rest = null;
		int count = 0;
		try {
			presta = con.prepareStatement(sql);
			rest = presta.executeQuery();
			while (rest.next()) {
				count = rest.getRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			close(rest,presta,con);
		}
		return count;
	}

	/**
	 * 查询数据
	 * 
	 * @param
	 * @param fieldname
	 *            字段
	 * @return 返回某一个字段值
	 */
	public static String query(String sql, String fieldname, Map<String, String> jdbc_map, String database) {
		PreparedStatement presta = null;
		DruidPoolConnect db = new DruidPoolConnect(jdbc_map, database);
		Connection con = db.getConnection();
		String fieldValue = null;
		ResultSet rest = null;
		try {
			presta = con.prepareStatement(sql);
			rest = presta.executeQuery();
			while (rest.next()) {
				fieldValue = rest.getString(fieldname);
				if (fieldValue == null) {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			close(rest,presta,con);
		}
		return fieldValue;
	}

	/**
	 * 查询【一条sql查询多条记录,rest.next()就是索引到一个字段的一条记录】
	 * 注：若要查询多个字段的值，那每个字段都必须有各自的一条记录，若每个字段的都是只有同样的记录，则只有一条
	 * 
	 * @param sql
	 *            sql语句
	 * @param fieldname
	 *            字段名
	 * @return 返回多个字段值存在list集合
	 */
	public static List<String> moreQuery(String sql, String[] fieldname, Map<String, String> jdbc_map,
			String database) {
		List<String> list_values = new ArrayList<String>();
		PreparedStatement presta = null;
		DruidPoolConnect db = new DruidPoolConnect(jdbc_map, database);
		Connection con = db.getConnection();
		String fieldValue = null;
		ResultSet rest = null;
		try {
			presta = con.prepareStatement(sql);
			rest = presta.executeQuery();
			for (int i = 0; i < fieldname.length; i++) {
				while (rest.next()) {
					fieldValue = rest.getString(fieldname[i]);
					list_values.add(fieldValue);
				}
			}
			if (list_values.size() == 0) {
				return null;
			}
		} catch (SQLException e) {
			Assert.fail(e.getMessage());
		} finally {
			close(rest,presta,con);
		}
		return list_values;
	}
	
	
	/**
	 * 查询【一条sql查询多条记录,rest.next()就是索引到一个字段的下一条记录】
	 * 注：若要查询多个字段的值，那每个字段都必须有各自的一条记录，若每个字段的都是只有同样的记录，则只有一条
	 * 
	 * @param sql
	 *            sql语句
	 * @param fieldname
	 *            字段名
	 * @return 返回一个list集合，集合元素是一个Object 数组，每个数组里存放该行的所有列的数据
	 */
	public static List<Object[]> moreQueryObj(String sql, String[] fieldname, Map<String, String> jdbc_map,String database) {
			
		List<Object[]> list_values = new ArrayList<Object[]>();
		PreparedStatement presta = null;
		DruidPoolConnect db = new DruidPoolConnect(jdbc_map, database);
		Connection con = db.getConnection();
		String fieldValue = null;
		ResultSet rest = null;
		try {
			presta = con.prepareStatement(sql);
			rest = presta.executeQuery();
	        
//	        rest.last();
//	        int rows=rest.getRow();
			for (int i = 0; i < 1; i++) {
				while (rest.next()) {
					Object[] value= new Object [1];
					fieldValue = rest.getString(fieldname[i]);
					value[0]=fieldValue;
					list_values.add(value);
				}
			}
			if (list_values.size() == 0) {
				return null;
			}
		} catch (SQLException e) {
			Assert.fail(e.getMessage());
		} finally {
			close(rest,presta,con);
		}
		return list_values;
	}
	
	
	
	//关闭连接
	private static void close(ResultSet rest,PreparedStatement presta,Connection con){
		try {
			if (null != rest) {
				rest.close();
			}
			if (null != presta) {
				presta.close();
			}
			if (null != con) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	private static void close(PreparedStatement presta,Connection con){
		try {
			if (null != presta) {
				presta.close();
			}
			if (null != con) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
