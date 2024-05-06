package com.o2dent.lib.accounts;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value="com.o2dent.lib.accounts")
public class Configurations {
}
