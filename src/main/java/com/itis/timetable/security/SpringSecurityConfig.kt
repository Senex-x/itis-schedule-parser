package com.itis.timetable.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
open class SpringSecurityConfig: WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var handler: AccessDeniedHandler

    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/groups").hasAnyRole("User")
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedHandler(handler)
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("User").password("password").roles("User")
    }
}