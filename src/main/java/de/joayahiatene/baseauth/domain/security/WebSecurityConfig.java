package de.joayahiatene.baseauth.domain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
    }

    /**
     * This method sets our password encoder.
     *
     * @param userService     takes user service to use user specific methods.
     * @param passwordEncoder takes the specific password encoder.
     * @param auth            takes auth object to set password encoder.
     * @throws Exception throws generic exception.
     */
    @Autowired
    public void configureGlobal(final UserDetailsService userService,
                                final PasswordEncoder passwordEncoder,
                                final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

    }

    /**
     * This method sets our cors settings for different requests.
     *
     * @return returns our cors config source object.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
