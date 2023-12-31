package com.github.taurus366.security;

import com.github.taurus366.views.client.IndexPageView;
import com.github.taurus366.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll()

                .requestMatchers("/home").permitAll()
                .requestMatchers("/").permitAll();



        super.configure(http);
        setLoginView(http, LoginView.class);

//        setLoginView(http, IndexPageView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
