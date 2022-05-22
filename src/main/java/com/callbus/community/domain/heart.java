package com.callbus.community.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 좋아요 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class heart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

}
