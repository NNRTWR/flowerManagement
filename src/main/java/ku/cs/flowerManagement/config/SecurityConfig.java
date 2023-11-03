package ku.cs.flowerManagement.config;

import ku.cs.flowerManagement.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig { //เปิดหน้าไม่ขึ้นมานี่เลยจ้าาาาาาาาาาาาาาาา


    @Autowired
    private UserDetailsServiceImp userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                       .requestMatchers(new AntPathRequestMatcher("/flower/**")).hasAnyAuthority("SELLER", "OWNER", "GARDENER")
                       .requestMatchers(new AntPathRequestMatcher("/beds/**")).hasAnyAuthority("GARDENER")
                       .requestMatchers(new AntPathRequestMatcher("/orders/**")).hasAnyAuthority("GARDENER")
//                        .requestMatchers(new AntPathRequestMatcher("/flower/create")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/flower/detail")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/order/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/beds/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/test/**")).permitAll()


                       .requestMatchers(
                               new AntPathRequestMatcher("/seller/**")).hasAuthority("SELLER")
                        .requestMatchers(
                               new AntPathRequestMatcher("/gardener/**")).hasAuthority("GARDENER")
                       

                        .requestMatchers(new AntPathRequestMatcher("/order/**")).permitAll()
                      

                        .requestMatchers(new AntPathRequestMatcher("/invoice/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/invoiceConfirm/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/invoiceCompleteButton/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/stock/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/allocate/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") //login ที่ path นี้นะ
                        .defaultSuccessUrl("/", true) //ถ้า login สำเร็จจะไปที่ path นี้นะ
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
