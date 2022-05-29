package com.callbus.community.api;

import com.callbus.community.dto.common.MemberInfo;
import com.callbus.community.dto.heart.HeartDto;
import com.callbus.community.service.heart.HeartService;
import com.callbus.community.util.common.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 좋아요 관련 컨트롤러
 */
@Api(tags = {"게시판 좋아요 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class HeartController {

    private final HeartService heartService;


    /**
     * 좋아요 등록
     *
     * @param heartDto
     * @param request
     * @return
     */
    @PostMapping("/hearts")
    @ApiOperation(value = "게시글 좋아요", notes = "HTTP Header에 Authorization 값이 있는 회원만 가능하며 글에 좋아요는 한 계정이 한 글에 한 번만 할 수 있습니다. ex) Realtor 1")
    public ResponseEntity<?> insertHeart(@RequestBody @Valid HeartDto heartDto, HttpServletRequest request) {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        heartService.insertHeart(memberInfo.getMemberId(), heartDto.getBoardId());

        return CommonUtils.setObjectResponseEntity(null, "정상적으로 등록 하셨습니다.");
    }

}
