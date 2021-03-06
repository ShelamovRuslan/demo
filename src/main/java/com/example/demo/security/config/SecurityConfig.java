package com.example.demo.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().
                antMatchers("/all-products").authenticated().
                antMatchers("/edite").authenticated().
                antMatchers("/user-page").authenticated().
                antMatchers("/add-product").authenticated().
                antMatchers("/edite").authenticated().
                antMatchers("/edite-line").authenticated().
                antMatchers("/shopping-list").authenticated().
                antMatchers("/sing-up").permitAll()
                .and()
                .formLogin()
                .loginPage("/sing-in")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/user-page")
                .failureForwardUrl("/incorrect-password")
                .permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
