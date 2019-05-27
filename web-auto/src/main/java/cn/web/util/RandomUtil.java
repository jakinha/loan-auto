package cn.web.util;

import java.util.Random;


public class RandomUtil {
	
	static final String  strPool="天,地,玄,黄,宇,宙,洪,荒,日,月,盈,辰,宿,列,张,寒,来,暑,往,秋,收,冬,藏,闰,馀,成,岁,律,吕,调,阳,云,腾,致,雨,露,结,为,霜,金,生,丽,水,玉,出,昆,冈,剑,号,巨,阙,珠,称,夜,光,果,珍,李,柰,菜,重,芥,姜,海,咸,河,淡,鳞,潜,羽,翔,龙,师,火,帝,鸟,官,人,皇,始,制,文,字,乃,服,衣,裳,推,位,让,国,有,虞,陶,唐,吊,民,伐,罪,周,发,殷,汤,坐,朝,问,道,垂,拱,平,章,爱,育,黎,首,臣,伏,戎,羌,遐,迩,一,体,率,宾,归,王,鸣,凤,在,竹,白,驹,食,场,化,被,草,木,赖,及,万,方"; 
	
	static final String numberPool = "1,2,3,4,5,6,7,8,9,0";
	
	static final String charsPool = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M";
	/**
	 * 产生0-9的随机字符串
	 * @param len	生产的字符串长度
	 * @return
	 */
	public static String randomNum(int len){

		String arryStr[]=numberPool.split(",");
		int strLen=arryStr.length;
		String finalRandom=random(len,strLen,arryStr);
		return finalRandom;
	}
	
	/**
	 * 产生   文字  的随机字符串
	 * @param len	生产的字符串长度
	 * @return
	 */
	public static String randomStr(int len){

		
		String arryStr[]=strPool.split(",");
		int strLen=arryStr.length;
		String finalRandom=random(len,strLen,arryStr);
		return finalRandom;
	}
	
	/**
	 * 产生  a-z 的随机字符串
	 * @param len	生产的字符串长度
	 * @return
	 */
	public static String randomChar(int len){

		String arryStr[]=charsPool.split(",");
		int strLen=arryStr.length;
		String finalRandom=random(len,strLen,arryStr);
		return finalRandom;
	}
	
	/**
	 * 产生 文字和数字  的随机字符串
	 * @param len	生产的字符串长度
	 * @return
	 */
	public static String randomStrAndNum(int strLen,int numLen){

		String arryStr[]=strPool.split(",");
		int strLens=arryStr.length;
		String arryNum[]=numberPool.split(",");
		int numLens=arryNum.length;
		String finalRandom=random(strLen,strLens,arryStr)+random(numLen,numLens,arryNum);
		return finalRandom;
	}
	
	
	private static String random(int len,int arryLen,String arry[]){
		int i=0;
		int con=0;
		Random random=new Random();
		StringBuffer stringBuffer=new StringBuffer("");
		while(con<len){
			i=random.nextInt(arryLen);
			stringBuffer.append(arry[i]);
			con++;
		}
		return stringBuffer.toString();
	}
}
