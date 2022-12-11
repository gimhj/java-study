package tutoring.Project.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tutoring.Project.auth.common.SignInAuthenticationEntryPoint;
import tutoring.Project.auth.filter.CustomAuthFilter;
import tutoring.Project.auth.filter.SingUpProcessingFilter;
import tutoring.Project.auth.handler.CustomAuthenticationFailureHandler;
import tutoring.Project.auth.handler.CustomAuthenticationSuccessHandler;
import tutoring.Project.auth.provider.CustomAuthenticationProvider;
import tutoring.Project.member.service.MemberService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final CustomAuthenticationProvider authProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final MemberService memberService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**"
        );
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(singUpProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(authProvider)
            .exceptionHandling()
            .authenticationEntryPoint(new SignInAuthenticationEntryPoint())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf()
            .disable()
            .cors()
            .disable();

        return http.build();
    }

    @Bean
    public SingUpProcessingFilter singUpProcessingFilter() throws Exception {
        SingUpProcessingFilter singUpProcessingFilter = new SingUpProcessingFilter();
        singUpProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        singUpProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        singUpProcessingFilter.setAuthenticationManager(this.authenticationManager(
            authenticationConfiguration));

        return singUpProcessingFilter;
    }

    @Bean
    public CustomAuthFilter customAuthFilter() {
        return new CustomAuthFilter(memberService);
    }

    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
