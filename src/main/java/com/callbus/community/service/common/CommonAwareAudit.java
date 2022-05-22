package com.callbus.community.service.common;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommonAwareAudit implements AuditorAware<String> {
    public CommonAwareAudit() {
    }

    public Optional<String> getCurrentAuditor() {
        //TODO : 유저 id 들어가도록 로직 작성해야함
        return Optional.of("Anonymous");
    }
}