package cn.web.dao.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class ConnectRedis {
	
	static Jedis jedis =RedisPool.getInstence();
	
	/**set单个key值**/
	public static void setKeyValue(String key,String value){
		jedis.set(key, value);
	}
	/**get单个key值**/
	public static String getKeyValue(String key){
		String value= jedis.get(key);
		return value;
	}
	
	/**set一个map集合key值**/
	public static void setMapValue(String key,Map<String ,String> map){
		jedis.hmset(key,map);
	}
	
	/**get一个map集合key值**/
	public static List<String> getMapValue(String key){
		List<String> list=jedis.hvals("key");
		return list;
	}
	
	/**get一个map集合key 的某一个key的值**/
	public static List<String> getMapKyeValue(String key,String c_key){
		List<String> list=jedis.hmget(key,c_key);
		return list;
		
	}
	
	/**set一个list集合的key值**/
	public static void setListValue(String key,List<String> list){
		for(int i=0;i<list.size();i++){
			 jedis.lpush(key,list.get(i));
		}
	//	RedisPool.closeRedisResouce(jedis);
	}
	/**get list集合的全部值**/
	public static void getListValue(){
		jedis.lrange("listDemo", 0, -1);
//		jedis.lrange("listDemo", 0, 1);
	}
}
