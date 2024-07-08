package com.o2dent.lib.accounts;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.o2dent.lib")
@EnableJpaRepositories(value="com.o2dent.lib")
public class Configurations {

}
