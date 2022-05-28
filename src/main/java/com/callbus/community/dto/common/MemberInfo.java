package com.callbus.community.dto.common;

import com.callbus.community.domain.AccountType;
import lombok.Getter;
import lombok.Setter;

/**
 * 권한 및 멤버 id
 */
@Getter
@Setter
public class MemberInfo {
    private Long memberId;
    private AccountType accountType;
    private String accountId;
}
