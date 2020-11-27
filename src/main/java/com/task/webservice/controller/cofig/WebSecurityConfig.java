package com.task.webservice.controller.cofig;

import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

        User admin = new User("admin@isp.net", "{noop}P@ssword1");
        admin.setRole("ADMIN");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setAddress("address");

        userService.saveNewUser(admin);

        User user = new User("user", "{noop}user");
        user.setRole("USER");
        user.setFirstName("user");
        user.setLastName("user");
        user.setAddress("address");

        userService.saveNewUser(user);

        auth.jdbcAuthentication()
                .dataSource(dataSource);

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin-**").hasRole("ADMIN")
                .antMatchers("/login*").permitAll()
                .antMatchers("/user*").permitAll()
                .antMatchers("/registration*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html", true)
                .failureUrl("/login.html?error=true")
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID");

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());

        return authProvider;
    }
}
