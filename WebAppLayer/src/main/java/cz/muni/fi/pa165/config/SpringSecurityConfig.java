package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.InitialDataConfig;
import cz.muni.fi.pa165.facade.UserSystemFacade;
import cz.muni.fi.pa165.security.AuthenticationFilter;
import cz.muni.fi.pa165.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Karel Vaculik
 */

@Configuration
@EnableWebSecurity
@Order(2)
@ComponentScan(basePackages = {"cz.muni.fi.pa165.security"})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    public SpringSecurityConfig() {
        super(true); // enable the default configuration
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .exceptionHandling().and()
                .anonymous().and()

                .authorizeRequests()
                //permissions for ADMIN
                .antMatchers(HttpMethod.DELETE, "/rest/creatures/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/rest/areas/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/rest/weapons/**").hasRole("ADMIN")
                //permissions for all users (role: USER | ADMIN)
                .antMatchers(HttpMethod.DELETE, "/rest/users/**").authenticated()
                .antMatchers(HttpMethod.POST, "/rest/users/edit/**").authenticated()
                .antMatchers(HttpMethod.POST, "/rest/creatures/**").authenticated()
                .antMatchers(HttpMethod.POST, "/rest/areas/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/rest/weapon-efficiencies/**").authenticated()
                .antMatchers(HttpMethod.POST, "/rest/weapon-efficiencies/create").authenticated()
                .antMatchers(HttpMethod.POST, "/rest/weapons/**").authenticated()
                //anonymous users have permissions for any other requests
                .anyRequest().permitAll().and()

                .formLogin().loginPage("/login.html").and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
