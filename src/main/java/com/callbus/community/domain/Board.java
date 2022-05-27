package com.callbus.community.domain;

import com.callbus.community.domain.common.BaseEntity;
import com.callbus.community.dto.board.BoardUpdateForm;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 Entity
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    //제목
    @Column(length = 50, nullable = false)
    @Setter
    private String subject;

    //내용
    @Column(length = 500, nullable = false)
    @Setter
    private String contents;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 1", length = 1)
    @Setter
    private Boolean isUse = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey =@ForeignKey(name = "fk_board_member_id"))
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Heart> hearts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.isUse = this.isUse == null || this.isUse;
    }

    //변경 감지 수정
    public void changeBoard(BoardUpdateForm boardForm) {
        this.setContents(boardForm.getContents());
        this.setSubject(boardForm.getSubject());
    }

    public void deleteBoard(BoardUpdateForm boardForm) {
        this.setIsUse(boardForm.getIsUse());
    }

}
