package nobel.springsecurity.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                /**
                 * antMatchers order matters, the first antMatcher that hits the api endpoint will return the boolean
                 * @PreAuthorize can be used at method level in the Controller class, but antMatchers is preferred
                 * */
                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/students/**").hasRole(UserRole.ADMIN.name()) // Role based authentication
                .antMatchers(HttpMethod.POST, "/students/**").hasAuthority(UserPermission.STUDENT_WRITE.name())
                .antMatchers(HttpMethod.PUT, "/students/**").hasRole(UserRole.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/students/**").hasRole(UserRole.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/students/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/students", true);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                                    .username("student")
                                    .password(passwordEncoder.encode("123"))
//                                    .roles(UserRole.STUDENT.name())   // ROLE_STUDENT
                                    .authorities(UserRole.STUDENT.getGrantedAuthorities())
                                    .build();

        UserDetails ahmedDetails = User.builder()
                                    .username("ahmed")
                                    .password(passwordEncoder.encode("123"))
//                                    .roles(UserRole.ADMIN.name())     // ROLE_ADMIN
                                    .authorities(UserRole.ADMIN.getGrantedAuthorities())
                                    .build();

        UserDetails tomDetails = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("123"))
//                .roles(UserRole.ADMINTRAINEE.name())     // ROLE_ADMINTRAINEE
                .authorities(UserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(userDetails, ahmedDetails, tomDetails);
    }
}
