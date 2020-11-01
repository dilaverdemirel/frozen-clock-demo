package com.dilaverdemirel.frozenclock.config;

import com.dilaverdemirel.frozenclock.clock.FrozenClock;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dilaverdemirel
 * @since 10/31/20
 */
@EnableWebMvc
@Configuration
public class FrozenClockConfig implements WebMvcConfigurer {

    @RefreshScope
    @Bean
    @Override
    public org.springframework.validation.Validator getValidator() {
        return new LocalValidatorFactoryBean() {
            @Override
            protected void postProcessConfiguration(javax.validation.Configuration<?> configuration) {
                configuration.clockProvider(FrozenClock.getClockProvider());
            }
        };
    }
}
