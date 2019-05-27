package cn.web.dto;

public class TestDataModel {

	private String description;
	
	private String testData;
	
	private String checkPoint;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTestData() {
		return testData;
	}
	public void setTestData(String testData) {
		this.testData = testData;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	
	@Override
	public String toString() {
		return "TestDataBean [description=" + description + ", testData=" + testData + ", checkPoint=" + checkPoint
				+ "]";
	}
	
}
