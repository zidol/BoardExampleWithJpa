package com.callbus.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "commonAwareAudit")
public class JpaAuditingConfiguration {

}
