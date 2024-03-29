package com.callbus.community.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonAwareAudit implements AuditorAware<Long> {


    public Optional<Long> getCurrentAuditor() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        Long memberId = null;
        if (authorization == null) {
            return Optional.of(0L);
        } else if (authorization.startsWith("Realtor ")) {
            memberId = Long.valueOf(authorization.substring("Realtor ".length()));
        } else if (authorization.startsWith("Lessor ")) {
            memberId = Long.valueOf(authorization.substring("Lessor ".length()));
        } else {
            memberId = Long.valueOf(authorization.substring("Lessee ".length()));
        }
        return Optional.of(memberId);
    }
}