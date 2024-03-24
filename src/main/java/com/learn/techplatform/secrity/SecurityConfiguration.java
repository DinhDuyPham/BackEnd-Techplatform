package com.learn.techplatform.secrity;


import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.utils.AppValueConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    AuthEntryPointException unauthorizedHandler;

    @Autowired
    AppValueConfigure applicationValueConfigure;

    @Bean
    public AuthTokenFilter authenticationTokenFilterBean() {
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors().and()
            // we don't need CSRF because our token is invulnerable
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            // don't create session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                (authz) ->
                    authz
                        // Allow access public resource
                        .requestMatchers(
                            HttpMethod.GET,
                            "/",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/**/*.png",
                            "/**/*.gif",
                            "/public/**",
                            "/**/public",
                            "/**/public/**",
                            "/**/*.json",
                            "/**/*.jpg",
                            // enable swagger endpoints
                            "/swagger-resources/**",
                            "/swagger-ui.html**",
                            "/webjars/**",
                            "/v2/api-docs",
                            "/configuration/ui",
                            "/configuration/security",
                            "/manage/api-docs",
                            "/v3/api-docs/**"
                            // hecto process
                        )
                        .permitAll()
                        // allow CORS option calls
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**")
                        .permitAll()
                        .requestMatchers(
                            HttpMethod.POST,
                            ApiPath.AUTHENTICATE_API + ApiPath.SIGN_UP,
                            ApiPath.AUTHENTICATE_API + ApiPath.LOGIN,
                            ApiPath.AUTHENTICATE_API + ApiPath.SIGNUP_VERIFY
                        )
                        .permitAll()
                            .requestMatchers(HttpMethod.PUT,
                                ApiPath.AUTHENTICATE_API + ApiPath.FORGOT_PASSWORD,
                                ApiPath.AUTHENTICATE_API + ApiPath.RESET_PASSWORD
                            ).permitAll()
                        .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        // Custom JWT based security filter
        httpSecurity.addFilterBefore(
                authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        // disable page caching
        httpSecurity.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return httpSecurity.build();
    }
}


