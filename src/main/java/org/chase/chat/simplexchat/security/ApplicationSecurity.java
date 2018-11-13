package org.chase.chat.simplexchat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static java.util.Objects.requireNonNull;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    private final ChatUserDetailService chatUserDetailService;

    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    public ApplicationSecurity(ChatUserDetailService chatUserDetailService) {
        this.chatUserDetailService = requireNonNull(chatUserDetailService,"chatUserDetailService");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(chatUserDetailService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
               .antMatchers("/ressources/**", "/signup").permitAll()
               .anyRequest().authenticated()
               .and()
               .formLogin().loginPage("/login").permitAll()
               .failureUrl("/login-error")
               .and().logout().logoutSuccessUrl("/index.html");
        security.csrf().disable();
        security.headers().frameOptions().disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }
}