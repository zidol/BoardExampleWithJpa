package com.callbus.community.domain;

import com.callbus.community.domain.common.BaseEntity;
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
public class Heart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false
            , foreignKey = @ForeignKey(name = "fk_heart_member_id"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false
            , foreignKey = @ForeignKey(name = "fk_heart_board_id"))
    private Board board;

}
