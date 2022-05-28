package com.callbus.community.config;

import com.callbus.community.Exception.customException.NotFoundException;
import com.callbus.community.domain.AccountType;
import com.callbus.community.domain.Member;
import com.callbus.community.dto.common.MemberInfo;
import com.callbus.community.repository.member.MemberRepository;
import com.callbus.community.util.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorityCheckFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String method = request.getMethod();
        boolean isAuthenticated = false;

        MemberInfo memberInfo = null;

        if (method.equals("POST") || method.equals("PUT") || method.equals("PATCH") || method.equals("DELETE")) {
            if (authorization == null || !(authorization.startsWith("Realtor ")
                    || authorization.startsWith("Lessor ") || authorization.startsWith("Lessee "))) {
                log.info("인증 받지 못한 사용자입니다.");
                setErrorResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ERROR", request, response);
            } else {
                memberInfo = getMemberInfo(authorization);
                request.setAttribute("memberInfo", memberInfo);
                chain.doFilter(request, response);
            }
        } else {
            if (authorization != null) {
                memberInfo = getMemberInfo(authorization);
                request.setAttribute("memberInfo", memberInfo);
            }
            chain.doFilter(request, response);
        }
    }

    private MemberInfo getMemberInfo(String authorization) {
        MemberInfo memberInfo = new MemberInfo();
        Long memberId = null;
        //컨트롤러에서 사용하기 위해 멤버 id와 계정 타입 닮기
        if (authorization.startsWith("Realtor ")) {
            memberId = Long.valueOf(authorization.substring("Realtor ".length()));
            memberInfo.setMemberId(memberId);
            memberInfo.setAccountType(AccountType.REALTOR);
        } else if (authorization.startsWith("Lessor ")) {
            memberId = Long.valueOf(authorization.substring("Lessor ".length()));
            memberInfo.setMemberId(memberId);
            memberInfo.setAccountType(AccountType.LESSOR);
        } else {
            memberId = Long.valueOf(authorization.substring("Lessee ".length()));
            memberInfo.setMemberId(memberId);
            memberInfo.setAccountType(AccountType.LESSEE);
        }
        return memberInfo;
    }

    //인증 받지 못한 사용자의 요청시 응답 메소드
    public void setErrorResponse(HttpStatus status, String message, HttpServletRequest request,
                                 HttpServletResponse response) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(message);
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setStatus(status.value());
        errorResponse.setTimestamp(LocalDateTime.now());

        // response에 넣기
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
