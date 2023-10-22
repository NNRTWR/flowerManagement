package ku.cs.flowerManagement.config;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComparatorConfig {
    @Bean
    public DateTimeComparator dateTimeComparator(){
        return new DateTimeComparator();
    }
}
