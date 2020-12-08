package com.myz.npp.service.accountbook.enums;

import com.myz.common.exception.MyzBizException;

import java.math.BigDecimal;

/**
 * @author yzMa
 * @desc
 * @date 2020/12/4 15:25
 * @email 2641007740@qq.com
 */
public enum NetTypeEnum {

    LU_YAN_ZI(1,(short)1,(short)1,160,"撸延子160(分)"),

    ;
    private int typeId;
    private Short type;
    private Short subType;
    private int price;
    private String desc;

    NetTypeEnum(int typeId,Short type,Short subType,int price,String desc){
        this.typeId = typeId;
        this.type = type;
        this.subType = subType;
        this.price = price;
        this.desc = desc;
    }

    public static NetTypeEnum match(int typeId){
        for (NetTypeEnum netTypeEnum : NetTypeEnum.values()){
            if (netTypeEnum.getTypeId() == typeId){
                return netTypeEnum;
            }
        }
        throw new MyzBizException("20201204","typeId"+typeId+" not support");
    }

    public int getTypeId() {
        return typeId;
    }

    public Short getType() {
        return type;
    }

    public Short getSubType() {
        return subType;
    }

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }
}
