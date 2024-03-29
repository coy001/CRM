package com.congyuan.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("securityDataSource")
    private DataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Allow manager & admin to create / update
                .antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
                // Allow admin to delete
                .antMatchers("/employees/delete").hasRole("ADMIN")
                // Allow employee do the rest
                .antMatchers("/employees/**").hasRole("EMPLOYEE")
                // Other urls can be accessed publically
                .antMatchers("/resources/**").permitAll().and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll().and()
                .logout().permitAll().and()
                .exceptionHandling().accessDeniedPage("/denied");
    }
}
