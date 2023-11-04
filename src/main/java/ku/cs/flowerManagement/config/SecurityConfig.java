package ku.cs.flowerManagement.config;

import ku.cs.flowerManagement.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import java.util.Collection;


@Configuration
@EnableWebSecurity
public class SecurityConfig { //เปิดหน้าไม่ขึ้นมานี่เลยจ้าาาาาาาาาาาาาาาา


    @Autowired
    private UserDetailsServiceImp userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
        
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/static/assets/**")).permitAll()


//                        .requestMatchers(new AntPathRequestMatcher("/flower/**")).permitAll()
                       .requestMatchers(new AntPathRequestMatcher("/flower/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                       .requestMatchers(new AntPathRequestMatcher("/allocate/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                       .requestMatchers(new AntPathRequestMatcher("/beds/**")).hasAnyAuthority("GARDENER","SELLER")
                       .requestMatchers(new AntPathRequestMatcher("/orders/**")).hasAnyAuthority("GARDENER")


//                        .requestMatchers(new AntPathRequestMatcher("/flower/create")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/flower/detail")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/order/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/beds/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/test/**")).permitAll()


                       .requestMatchers(
                               new AntPathRequestMatcher("/seller/**")).hasAuthority("SELLER")
                        .requestMatchers(
                               new AntPathRequestMatcher("/gardener/**")).hasAnyAuthority("GARDENER","SELLER")
                        .requestMatchers(
                               new AntPathRequestMatcher("/owner/**")).hasAuthority("OWNER")

                        .requestMatchers(new AntPathRequestMatcher("/order/**")).permitAll()
                      
                        .requestMatchers(new AntPathRequestMatcher("/invoice/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                        .requestMatchers(new AntPathRequestMatcher("/invoiceConfirm/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                        .requestMatchers(new AntPathRequestMatcher("/invoiceCompleteButton/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                        .requestMatchers(new AntPathRequestMatcher("/stock/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                        .requestMatchers(new AntPathRequestMatcher("/allocate/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") //login ที่ path นี้นะ
                        // .defaultSuccessUrl("/", true) //ถ้า login สำเร็จจะไปที่ path นี้นะ
                        .successHandler((request, response, authentication) -> {
            // ดึงข้อมูล Role ของผู้ใช้
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            for (GrantedAuthority authority : authorities) {
                        
                                if (authority.getAuthority().equals("GARDENER")) {
                                    response.sendRedirect("/gardener/beds");
                                } else if (authority.getAuthority().equals("SELLER")) {
                                    response.sendRedirect("/seller/orders");
                                } else{
                                    response.sendRedirect("/allocate");
                                }
                            }
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );
        return http.build();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
}

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
//}
