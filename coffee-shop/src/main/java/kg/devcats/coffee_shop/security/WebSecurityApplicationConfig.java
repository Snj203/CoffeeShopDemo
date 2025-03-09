package kg.devcats.coffee_shop.security;

import kg.devcats.coffee_shop.filter.CustomAccessDeniedHandler;
import kg.devcats.coffee_shop.filter.CustomAuthenticationFilter;
import kg.devcats.coffee_shop.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityApplicationConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public WebSecurityApplicationConfig(AuthenticationConfiguration authenticationConfiguration, CustomAccessDeniedHandler accessDeniedHandler) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        JdbcUserDetailsManager provider = new JdbcUserDetailsManager(dataSource);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationConfiguration);
        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter();

        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**","/login")
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.headers(headers -> headers.frameOptions().disable());

        http.authorizeHttpRequests(request -> request


                        .requestMatchers("/","/registration","/not-enough-permissions","/css/**", "/images/**").permitAll()

                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                        .requestMatchers("/api/coffee/buy","coffee/buy").hasAnyRole("ADMIN","USER","VIEWER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/[A-Za-z0-9]+/delete")).hasRole("ADMIN")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/[A-Za-z0-9]+/delete")).hasRole("ADMIN")
                        .requestMatchers("/api/city/**","/city/**","/api/state/**","/state/**").hasRole("ADMIN")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/[A-Za-z0-9]+/.*")).hasAnyRole("ADMIN","USER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/[A-Za-z0-9]+/.*")).hasAnyRole("ADMIN","USER")

                        .anyRequest().authenticated()
                );
        http.exceptionHandling(exception -> exception
                .accessDeniedHandler(accessDeniedHandler));

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("jwt")
                .permitAll()
        );

        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
