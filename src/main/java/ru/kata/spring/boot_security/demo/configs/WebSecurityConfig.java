package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailsService;


    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl")UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }


    /*@Autowired
    public  void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //защищает от CSRF атак
                .authorizeRequests() //авторизация запроса
                    .antMatchers("/", "/index").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN") // доступ для url admin
                    .antMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER')") //доступ для url user (помним,что две роли у пользователя)
                    .anyRequest().authenticated()  //все запросы должны быть аутентифицированы и авторизованы
                .and()
                    .formLogin() //форма для ввода логина-пароля, по дефолту это /login
                    .successHandler(successUserHandler)
                    .permitAll() //доступно всем
                .and()
                    .logout() // настройка выхода
                    .permitAll();
    }

    @Bean
    public  PasswordEncoder passwordEncoder() { //энкодер паролей
        return new BCryptPasswordEncoder(12);
    }

}