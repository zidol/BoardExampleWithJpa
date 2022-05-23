package com.callbus.community.service.common;

import com.callbus.community.domain.Member;
import com.callbus.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonAwareAudit implements AuditorAware<String> {

    private final MemberRepository memberRepository;

    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        Long userId = null;
        if (authorization == null) {
            return Optional.of("Anonymous");
        } else if (authorization.startsWith("Realtor ")) {
            userId = Long.valueOf(authorization.substring("Realtor ".length()));
        } else if (authorization.startsWith("Lessor ")) {
            userId = Long.valueOf(authorization.substring("Lessor ".length()));
        } else {
            userId = Long.valueOf(authorization.substring("Lessee ".length()));
        }
        try {
            Member member = memberRepository.findById(userId).orElse(null);
            return Optional.of(member.getAccountId());
        } catch (Exception e) {
            return Optional.of("Anonymous");
        }
    }
}