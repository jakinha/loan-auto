package cn.web.dao.single;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public  class SqlAction{
	/**
	 * 增删改
	 * @param 
	 */
	public static void updateData(String[] sqlarry){
		PreparedStatement presta = null;
		Connection con = DruidPoolConnect.getC();
			try{
				for (int i = 0; i < sqlarry.length; i++) {
					presta = con.prepareStatement(sqlarry[i]);
					presta.executeUpdate();
				}
			} catch(SQLException e){
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
			finally{
				 try {
					 if(null!=con){
						 con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Assert.fail(e.getMessage());
				}
	}
}


	/**
	 *查询数据
	 * @param 
	 * @param fieldname 字段
	 * @return
	 */
	public  static String query(String sql, String fieldname) {
		PreparedStatement presta = null;
		Connection con = DruidPoolConnect.getC();
		String fieldValue = null;
		try {
			presta = con.prepareStatement(sql);
			ResultSet rest = presta.executeQuery();
			while (rest.next()) {
				fieldValue = rest.getString(fieldname);
				if (fieldValue == null) {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		finally{
			 try {
				 if(null!=con){
					 con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
		return fieldValue;
	}
	

	/**
	 * 查询【一条sql查询多条记录,rest.next()就是索引到一个字段的一条记录】
	 * 注：若要查询多个字段的值，那每个字段都必须有各自的一条记录，若每个字段的都是只有同样的记录，则只有一条
	 * @param sql   sql语句
	 * @param fieldname  字段名
	 * @return
	 */
	public static List<String> moreQuery(String sql, String[] fieldname) {
		List<String> list_values = new ArrayList<String>();
		PreparedStatement presta = null;
		Connection con = DruidPoolConnect.getC();
		String fieldValue = null;
		ResultSet rest = null;
		try {
			presta = con.prepareStatement(sql);
			rest = presta.executeQuery();
			for(int i=0;i<fieldname.length;i++){
				while (rest.next()) {
					fieldValue = rest.getString(fieldname[i]);
					list_values.add(fieldValue);
				}
		}
		//	System.out.println("valuses size:" + list_values.size() + " | values :" + list_values);
			if (list_values.size() == 0) {
				return null;
			}
		} catch (SQLException e) {
			Assert.fail(e.getMessage());
		}
		finally{
			try {
				if(null!=rest){
					rest.close();
				}
				if(null!=presta){
					presta.close();
				}
				if(null!=con){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
		return list_values;
	}
}
