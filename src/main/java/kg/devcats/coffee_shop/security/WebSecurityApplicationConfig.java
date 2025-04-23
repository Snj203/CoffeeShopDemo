package kg.devcats.coffee_shop.security;

import kg.devcats.coffee_shop.security.filter.CustomAccessDeniedHandler;
import kg.devcats.coffee_shop.security.filter.CustomAuthenticationFilter;
import kg.devcats.coffee_shop.security.filter.CustomAuthorizationFilter;
import kg.devcats.coffee_shop.security.filter.CustomJwtHelper;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpiration;

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final    CustomJwtHelper jwtHelper;
    private final UserRepositoryJPA userService;

    public WebSecurityApplicationConfig(AuthenticationConfiguration authenticationConfiguration,
                                        CustomAccessDeniedHandler accessDeniedHandler,
                                        @Lazy CustomJwtHelper jwtHelper, UserRepositoryJPA userService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtHelper = jwtHelper;
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService(@Qualifier("h2DataSource") DataSource dataSource) {

        JdbcUserDetailsManager provider = new JdbcUserDetailsManager(dataSource);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(accessTokenExpiration,
                refreshTokenExpiration, authenticationConfiguration, jwtHelper, userService);
        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtHelper);

        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**","/login")
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.headers(headers -> headers.frameOptions().disable());

        http.authorizeHttpRequests(request -> request


                        .requestMatchers("/","/login-fail","/registration","/not-enough-permissions","/css/**", "/images/**",
                                "/api/auth/**").permitAll()

                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                        .requestMatchers("/api/coffee/buy","/coffee/view","/coffee/buy").hasAnyRole("ADMIN","USER","VIEWER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/[A-Za-z0-9-]+/delete")).hasRole("ADMIN")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/[A-Za-z0-9-]+/delete")).hasRole("ADMIN")
                        .requestMatchers("/api/city/**","/city/**","/api/state/**","/state/**").hasRole("ADMIN")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/[A-Za-z0-9-]+/.*")).hasAnyRole("ADMIN","USER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/[A-Za-z0-9-]+/.*")).hasAnyRole("ADMIN","USER")

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
