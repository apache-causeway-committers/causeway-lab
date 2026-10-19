package org.apache.causeway.lab.experiment.wicket_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/login", "/logout", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")           // Unified entry point
                    .loginProcessingUrl("/authenticate")  // Actual POST target
                    .usernameParameter("username")
                    .passwordParameter("password")
                 //   .successHandler(causewayAuthenticationSuccessHandler())
                 //   .failureHandler(customAuthenticationFailureHandler())
                    .permitAll()
                )

//            .formLogin(form -> form
//                .loginPage("/login")
//                .permitAll()
//            )

//            .logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .deleteCookies("JSESSIONID", "remember-me")
//                .invalidateHttpSession(true)
//                .permitAll()
//            )
            ;

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(final PasswordEncoder encoder) {
        String password = encoder.encode("password");
        UserDetails user = User.withUsername("user").password(password).roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return authentication -> authentication;
    }
}
