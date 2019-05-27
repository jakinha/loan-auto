package cn.web.dto;

/**
 * 动态的测试数据
 * @author huangjun
 *
 */
public class BasicTestDataBean {
	
	/**客户姓名**/
	private String clientName;
	
	/**客户身份证号**/
	private String idCard;
	
	/**客户手机号**/
	private String phoneNum;
	
	/**辅助的登录角色**/
	private String loginRule;

	/**订单号**/
	private String applyNum;
	
	/**crm的合同号**/
	private String contractNum;
	
	/**产品名称 **/
	private String productName;
	
	/**审批金额、融资金额**/
	private String trialMoney;
	
	/**车架号**/
	private String carFrameNumber;
	
	public String getCarFrameNumber() {
		return carFrameNumber;
	}
	public void setCarFrameNumber(String carFrameNumber) {
		this.carFrameNumber = carFrameNumber;
	}
	public String getTrialMoney() {
		return trialMoney;
	}
	public void setTrialMoney(String trialMoney) {
		this.trialMoney = trialMoney;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getLoginRule() {
		return loginRule;
	}
	public void setLoginRule(String loginRule) {
		this.loginRule = loginRule;
	}
	
	
	@Override
	public String toString() {
		return "BasicTestDataBean [clientName=" + clientName + ", idCard=" + idCard + ", phoneNum=" + phoneNum
				+ ", loginRule=" + loginRule + ", applyNum=" + applyNum + ", contractNum=" + contractNum
				+ ", productName=" + productName + ", trialMoney=" + trialMoney + ", carFrameNumber=" + carFrameNumber
				+ "]";
	}

}