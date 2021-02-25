package nobel.springsecurity.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** Basic Authentication :
         * Basic authentication is a simple authentication scheme built into the HTTP protocol.
         * The client sends HTTP requests with the Authorization header that contains
         * the word Basic word followed by a space and a base64-encoded
            1- You cant logout while using Basic authentication.
            2- Each request, sends the username and password in the header of it.
         */
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
