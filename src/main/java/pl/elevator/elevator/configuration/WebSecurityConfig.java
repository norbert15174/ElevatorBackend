package pl.elevator.elevator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.elevator.elevator.models.Role;
import pl.elevator.elevator.services.UserService;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${Algorithm-key}")
    private String key;
    private UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/elevator/**").antMatchers("/auth/register").antMatchers("/auth/login");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

                //http.authorizeRequests().antMatchers("/**").permitAll();
        http.cors().and().authorizeRequests()
                .antMatchers("/test").hasAnyRole("ADMIN")
                .antMatchers("/building/**").hasAnyRole("ADMIN")
                .and()
                .addFilterBefore(new JwtFilter(userService,key), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();

    }


}
