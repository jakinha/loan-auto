package cn.web.dao.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池设置
 * @author huangjun
 */
public class RedisPool {
	
	//Redis服务器IP
	private static String ADDR="127.0.0.1" ;
	//端口号
	private static int PORT=6379;
	//访问密码
//	private static String AUTH="admin";
	//连接实例的最大数，默认为8，-1则不限制连接实例的最大数
	private static int  MAX_ACTIVE =1024;
	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值8
	private static int MAX_IDLE=200;
	//连接时长
	private static int MAX_WAIT = 100000;
	//超时
    private static int TIMEOUT = 10000;
    
    
  //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    static {

    		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    		jedisPoolConfig.setMaxTotal(MAX_ACTIVE);
    		jedisPoolConfig.setMaxIdle(MAX_IDLE);
    		jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
    		jedisPoolConfig.setTestOnBorrow(ON_BORROW);
    		jedisPool=new JedisPool(jedisPoolConfig,ADDR,PORT,TIMEOUT);

    }
    
    /**获取jedis实例**/
    public static  Jedis getInstence(){
    	if(jedisPool!=null){
    		return jedisPool.getResource();
    	}
		return null;
    }
    
    /**释放jedis资源**/
	public static void closeRedisResouce(Jedis jedis){
    	if(jedis!=null){
    		jedisPool.close(); 
    	}
    }
}
