package com.callbus.community.domain;

import com.callbus.community.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 멤버 Entity
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    //닉네임
    private String nickname;

    //계정타입
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    //계정ID
    @Column(name = "account_id")
    private String accountId;

    //탈퇴 여부
    private boolean quit;


}
