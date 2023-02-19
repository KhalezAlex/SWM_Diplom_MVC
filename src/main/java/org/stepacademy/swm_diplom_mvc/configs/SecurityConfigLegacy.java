package org.stepacademy.swm_diplom_mvc.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigLegacy /*extends WebSecurityConfigurerAdapter*/ {
// адреса, по которым можно делать запросы неавторизованным пользователям
    private static final String[] endpointsForAll = {"/" ,"/login", "/home_page", "/register",
            "/onLoad", "/error_page", "/checkLoginForRegistration"};

// адреса для админа
    private static final String[] endpointsForAdmin = {"/admin_page"};

// адреса для авторизованных пользователей
    private static final String[] endpointsForAuthenticated = {"/profile", "/loadCitiesCountries", "/updateProfile"};


    // открываем папку static для того, чтобы обращаться к css файлу
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/*");
//    }


//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests((requests) -> requests
//                        .requestMatchers("/", "/webjars/**").permitAll()
//                        .requestMatchers("/client/*").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                        .failureUrl("/login?error=true")
//                )
//                .logout().logoutSuccessUrl("/login");
//
//        return http.build();
//    }

//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {return super.authenticationManagerBean();}

//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}




//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .httpBasic().disable()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(endpointsForAuthenticated).hasAnyRole("ADMIN", "USER", "STRIKED")
//                .antMatchers(endpointsForAdmin).hasRole("ADMIN")
//                .antMatchers(endpointsForAll).permitAll()
//                .anyRequest().authenticated()
////                .and()
////                .formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/").permitAll()
//                .and()
//                .logout().permitAll().logoutSuccessUrl("/");
//    }