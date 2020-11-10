package com.myz.npp.api.user.dto;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/5 18:18
 * @email 2641007740@qq.com
 */
public class UserUpdateParam extends UserInsertParam {

    /**
     * 主键
     */
//    private Long id;

    /**
     * npp userId，多个平台的UserId对应同一个人，需要用户绑定
     */
    private String userId;

    /**
     * 开放平台id 微信openId 支付宝alipayUserId
     */
    private String platformId;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", platformId=").append(platformId);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
