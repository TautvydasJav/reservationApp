package springframework.reservationApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import springframework.reservationApp.services.UserLoginDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserLoginDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and().authorizeRequests().antMatchers("/reservation/**").permitAll()
                .and().authorizeRequests().antMatchers("/search/**").permitAll()
                .and().authorizeRequests().antMatchers("/delete/**").permitAll()
                .and().authorizeRequests().antMatchers("/default/**").permitAll()
                .and().authorizeRequests().antMatchers("/specialist/**").hasAuthority("SPECIALIST")
                .and().authorizeRequests().antMatchers("/department/**").hasAuthority("DEPARTMENT")
                .anyRequest()
                .authenticated().and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successForwardUrl("/default")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and().logout(logout -> logout
                .logoutSuccessUrl("/login")
                .addLogoutHandler(new SecurityContextLogoutHandler())
        );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
