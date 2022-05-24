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
    @Column(unique = true)
    private String nickname;

    //계정타입
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    //계정ID
    @Column(name = "account_id", unique = true)
    private String accountId;

    //탈퇴 여부
    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0", length = 1)
    private Boolean quit = false;

    @PrePersist
    public void prePersist() {
        this.quit = this.quit != null && this.quit;
    }


}
