package cn.web.dao.single;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;

import cn.web.util.Constant;
import cn.web.util.PropertyUtil;
import cn.web.util.ReadConfXml;


public class DruidPoolConnect {

	private final DruidDataSource dataSource = new DruidDataSource();

	private final static DruidPoolConnect me = new DruidPoolConnect();

	private final ReadConfXml configReader=ReadConfXml.getInstence();
	
	private final PropertyUtil pro1=new PropertyUtil(Constant.DRUID_POOL_PATH);
	/**
	 * 连接池配置
	 */
	private DruidPoolConnect() {
		Map<String, String> map = configReader.getMultValue(Constant.MYSQL_CONNECT_INFO_PATH,null,"mysql_sit");
		dataSource.setDriverClassName(map.get("jdbc_driver"));
		dataSource.setUrl(map.get("jdbc_url")+"huaxia_cai_app");
		dataSource.setUsername(map.get("name"));
		dataSource.setPassword(map.get("pwd"));
		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataSource.setMaxActive(Integer.parseInt(pro1.getproperValue("MaxActive")));//最大连接池数量
		dataSource.setInitialSize(Integer.parseInt(pro1.getproperValue("InitialSize")));//初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
		dataSource.setMaxWait(Integer.parseInt(pro1.getproperValue("MaxWait")));//获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁
		dataSource.setMinIdle(Integer.parseInt(pro1.getproperValue("MinIdle")));//最小连接池数量
		dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(pro1.getproperValue("TimeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(pro1.getproperValue("MinEvictableIdleTimeMillis")));
		dataSource.setTestWhileIdle(Boolean.parseBoolean(pro1.getproperValue("TestWhileIdle")));//建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		dataSource.setTestOnBorrow(Boolean.parseBoolean(pro1.getproperValue("TestOnBorrow")));//申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
		dataSource.setTestOnReturn(Boolean.parseBoolean(pro1.getproperValue("TestOnReturn")));//归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
		dataSource.setPoolPreparedStatements(Boolean.parseBoolean(pro1.getproperValue("PoolPreparedStatements")));//是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
		dataSource.setMaxOpenPreparedStatements(Integer.parseInt(pro1.getproperValue("MaxOpenPreparedStatements")));//要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
		try {
			dataSource.init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getC() {
		return me.getConnection();
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DruidPoolConnect getIm() {
		return me;
	}
	
	public DruidDataSource getDataSource() {
		return this.dataSource;
	}
	
}
