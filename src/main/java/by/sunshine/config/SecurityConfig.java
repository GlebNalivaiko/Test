package by.sunshine.config;

import by.sunshine.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/security/registration_check", "/security/authorization_check", "/cart/delete/{product_id}", "/error**", "/security/check_code", "/security/registration", "/security/authorization", "/cart/{id}", "/cart", "/products/{id}", "/security", "/products/{id}/{activePage}", "/currency/{id}", "/resources/**", "/static/**", "/flag-icon-css/**", "/**.css", "/**.js", "/img/**", "/sunshine.by")
                        .permitAll()
                        .requestMatchers("/comment", "/orderCart", "/orderCart/confirm/{id}", "/orderCart/cancel/{orderId}/{productId}")
                        .hasRole("USER")
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated().and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class))
                .formLogin()
                .loginPage("/security/authorization")
                .successForwardUrl("/sunshine.by")
                .and()
                .logout().invalidateHttpSession(true).deleteCookies("token")
                .logoutUrl("/logout").deleteCookies("token")
                .logoutSuccessUrl("/sunshine.by")
                .permitAll();
        return httpSecurity.build();
    }
}
