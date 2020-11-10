package com.myz.npp.api.user.enums;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/9 21:16
 * @email 2641007740@qq.com
 */
public enum UserTypeEnum {
    PHONE_REG_TYPE((short)1,"手机号注册类型"),
    H5_WECHAT_AUTH((short)2,"h5微信授权"),
    ;

    private Short type;
    private String msg;

    UserTypeEnum(Short type,String msg){
        this.type = type;
        this.msg = msg;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UserTypeEnum{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                '}';
    }
}
