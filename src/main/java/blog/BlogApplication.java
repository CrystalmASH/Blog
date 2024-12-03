package blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BlogApplication {

    private static final String LOGIN_ROUTE = "/login";

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Configuration
    static class VideoShopWebConfiguration implements WebMvcConfigurer {

        /**
         * We configure {@code /login} to be directly routed to the {@code login} template without any controller
         * interaction.
         *
         * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
         */
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController(LOGIN_ROUTE).setViewName("login");
            registry.addViewController("/").setViewName("index");
        }
    }

    @Configuration
    static class WebSecurityConfiguration {

        @Bean
        SecurityFilterChain videoShopSecurity(HttpSecurity http) throws Exception {

            return http
                    .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                    .csrf(csrf -> csrf.disable())
                    .formLogin(login -> login.loginProcessingUrl("/login"))
                    .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
                    .build();
        }
    }

}
