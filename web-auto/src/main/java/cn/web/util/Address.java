package cn.web.util;

/**
 * 定义城市地区枚举
 * @author Administrator
 *
 */
public enum Address {

	SHANGHAI(1,"上海"), BEIJING(2,"北京"), HEFEI(3,"合肥"),SHANGHAIADDR(4,"徐汇区"), BEIJINGADDR(5,"朝阳区"), HEFEADDR(6,"瑶海区");
	
	private String name;
	private int index;

	private Address(int index,String name) {
		this.index = index;	
		this.name = name;
	}

	public static String getIndex(int dex) {
		for (Address address : Address.values()) {
			if(address.getIndex()==dex){
				return address.getName();
			}
		}
		return null;
	}

	private String getName() {
		return name;
	}

	private int getIndex() {
		return index;
	}


}
