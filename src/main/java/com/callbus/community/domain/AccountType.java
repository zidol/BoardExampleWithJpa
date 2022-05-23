package com.callbus.community.domain;

public enum AccountType {
    LESSOR("임대인"), REALTOR("공인 중개사"), LESSEE("임차인");

    private String desc;
    AccountType(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}
