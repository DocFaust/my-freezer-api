package de.docfaust.myfreezerapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration for JPA Auditing.
 * Enables automatic timestamp management for entities with @EntityListeners(AuditingEntityListener.class).
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // Configuration class - no additional code needed
}
