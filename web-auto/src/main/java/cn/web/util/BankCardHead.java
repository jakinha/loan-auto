package cn.web.util;

public enum BankCardHead {
    ICBC("621226410001133","中国工商银行"),
    BOC("621785200001893","中国银行"),
    CCB("622700720136004","中国建设银行"),
    ABC("622848003082069","中国农业银行");

    private String code;
    private  String desc;

    BankCardHead() {
    }

    BankCardHead(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }




}
