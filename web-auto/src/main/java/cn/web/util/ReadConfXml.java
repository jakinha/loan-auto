package cn.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.web.dto.TestDataModel;


/**
 * 读 取 xml 文 件
 * 
 * @author Administrator
 *
 */
public class ReadConfXml {

	private static ReadConfXml testData = new ReadConfXml();
	/** 当前项目路径 **/
	private static final String dir = System.getProperty("user.dir");
	private Document doc;

	private ReadConfXml() {
	}

	public static ReadConfXml getInstence() {
		return testData;
	}

	/**
	 * 读取xml文件里的单个节点的值,路径配置工程目录---文件路径
	 * 
	 * @param path
	 *            配置文件路径
	 * @param node
	 *            xml文件里任意一个节点名称
	 * @return 返回节点值
	 */
	public String getFilePath(String path, String node) {

		Element root = getDoc(path);
		Element targNode = root.element(node);
		return dir + targNode.getText();

	}

	/**
	 * 返回XML文件的测试数据具体某一个节点值
	 * 
	 * @param path
	 *            配置文件路径
	 * @param node
	 *            xml文件里任意一个节点名称
	 * @return 返回节点值
	 */
	public String getSingleValue(String path, String node) {

		Element root = getDoc(path);
		Element targNode = root.element(node);
		return targNode.getText();

	}

	/**
	 * 遍历加载配置XML文件的测试数据
	 * 
	 * @param path
	 *            配置文件路径
	 *            
	 * @param parentNode   
	 * 					node节点得父节点        
	 *            
	 * @param node
	 *            xml文件里任意一个目标节点名称
	 * @return 返回多个值--返回一个map
	 */
	public Map<String, String> getMultValue(String path,String parentNode, String goalNode) {
		
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		Element root = null;
		Element targNode = null;

		root = getDoc(path);
		
		if(null == parentNode){
			
			targNode = root.element(goalNode);
		}
		else {
			
			Element element = root.element(parentNode);
			targNode = element.element(goalNode);
		}
		
		@SuppressWarnings("unchecked")
		List<Element> elements = targNode.elements();
		for (Element el : elements) {
			map.put(el.getName(), el.getText());
			// map.put(el.attributeValue("name"), el.getText());
		}

		return map;
	}

	/**
	 * 读取xml配置文件的测试用例数据
	 * 
	 * @param path
	 *            测试数据的配置文件路径
	 * @param node
	 *            场景用例
	 * @return
	 */
	public Iterator<Object[]> getData(String path, String node) {

		List<Object[]> iList = new ArrayList<Object[]>();

		List<TestDataModel> geTestDataBeans = getCaseBeans(path, node);

		for (Object object : geTestDataBeans) {
			iList.add(new Object[] { object });
		}
		return iList.iterator();
	}

	@SuppressWarnings("unchecked")
	private List<TestDataModel> getCaseBeans(String path, String node) {

		List<TestDataModel> list = new ArrayList<TestDataModel>();
		Map<String, String> map = new ConcurrentHashMap<String, String>();

		Element root = getDoc(path);

		List<Element> listnode = root.elements(node);
		for (Element snode : listnode) {

			map.put("describe", snode.attributeValue("name"));
			List<Element> elements = snode.elements();

			for (Element el : elements) {
				map.put(el.getName(), el.getText());
			}
			TestDataModel testDataBean = new TestDataModel();
			testDataBean.setDescription(map.get("describe"));
			testDataBean.setTestData(map.get("testData"));
			testDataBean.setCheckPoint(map.get("checkPoint"));
			list.add(testDataBean);
		}
		return list;
	}

	/**获取文档root节点**/
	private Element getDoc(String path) {
		
		SAXReader reader = new SAXReader();
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		InputStream resourceAsStream = contextClassLoader.getResourceAsStream(path);
		try {
			doc = reader.read(resourceAsStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return doc.getRootElement();
	}

}
