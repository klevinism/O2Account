package com.o2dent.lib.accounts;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@ComponentScan
@Configuration
@EnableJpaRepositories(value="com.o2dent.lib.accounts")
public class Configurations {

}
