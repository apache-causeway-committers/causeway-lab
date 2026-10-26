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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) {
        http
            //.csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/login", "/logout", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")           // Unified entry point
                    .loginProcessingUrl("/authenticate")  // Actual POST target
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                //.deleteCookies("JSESSIONID", "rememberMe")
                //.invalidateHttpSession(true)
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL)))
            );
        return http.build();
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            System.err.println("failure: %s".formatted(exception));
            exception.printStackTrace();
            };
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
    	return new SavedRequestAwareAuthenticationSuccessHandler(); // Auto-redirects to original URL

        //return (request, response, authentication) -> System.err.println("success: %s".formatted(authentication));
    }

    @Bean
    UserDetailsService userDetailsService(final PasswordEncoder encoder) {
        UserDetails user = User
                .withUsername("sven")
                .password(encoder.encode("pass"))
                .roles("USER")
                .build();
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
