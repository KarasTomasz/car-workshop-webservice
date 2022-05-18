package pl.tkaras.carworkshopwebservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.tkaras.carworkshopwebservice.securities.auth.AuthUserDetailsService;
import pl.tkaras.carworkshopwebservice.securities.jwt.JwtTokenVerifier;
import pl.tkaras.carworkshopwebservice.securities.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import pl.tkaras.carworkshopwebservice.securities.jwt.JwtUtils;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthUserDetailsService authUserDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtUtils))
                .addFilterAfter(new JwtTokenVerifier(jwtUtils), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/api/v1/auth/registration", "/api/v1/auth/confirm", "api/v1/auth/login", "/login", "/api/v1/auth/**")
                    .permitAll()
                .antMatchers("/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**")
                    .permitAll()
                .anyRequest()
                .authenticated()
                //.and()
                //.headers().frameOptions().disable() //it fix h2 console problem
                .and()
                .csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authUserDetailsService);
        return provider;
    }
}
