package alkemy.challenge.Challenge.Alkemy.security.config;

import alkemy.challenge.Challenge.Alkemy.exception.CustomDeniedAccessHandler;
import alkemy.challenge.Challenge.Alkemy.security.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomDeniedAccessHandler();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                //ENDPOINTS PARA REGISTRO Y LOGIN
                .antMatchers("/authenticate", "/auth/**", "/deny", "/register", "/", "/api/**", "/login").permitAll()
                //ENDPOINTS PUBLICOS
                .antMatchers(HttpMethod.GET,"/news", "/activities", "/organization/public", "/v2/api-docs", "/returnme", "/slides").permitAll()
                //ENDPOINTS DE USER
                .antMatchers("/comments", "/comments/**", "/backoffice/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/contacts").hasAnyRole("USER","ADMIN")
                //ENDPOINTS DE ADMIN
                .antMatchers("/activities/**", "/categories/**", "/news/**", "/organization/**", "/user/**", "/slides/**", "/testimonials/**", "/members/**", "/contacts").hasRole("ADMIN")
                .anyRequest().authenticated().and().
                formLogin().loginPage("/login").permitAll()
                .and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());//Handling error 403.
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //JWT Filter
    }

}
