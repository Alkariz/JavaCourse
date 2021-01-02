package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css", "/img", "/login").permitAll() // Нужно ли дублирование "/login"? Эта же форма указана выше, как доступная всем
                .antMatchers("/routes", "/payments").hasRole("CLIENT")
                .antMatchers("/registerClient").hasAnyRole("MANAGER")
                .antMatchers("/registerManager").hasRole("ROOT")
                .antMatchers("/home").authenticated()
                .and()
            .formLogin()
                .loginPage("/login") // Нужно ли дублирование? Эта же форма указана выше, как доступная всем
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("client").password("123").roles("CLIENT")
                .and()
                .withUser("manager").password("1234").roles("CLIENT", "MANAGER ")
                .and()
                .withUser("admin").password("123456").roles("CLIENT", "MANAGER", "ROOT");
    }
}
