package ku.cs.flowerManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javassist.compiler.ast.Member;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.SignupRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.SignupService;


@SpringBootApplication
public class FlowerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerManagementApplication.class, args);
	}
	@Bean
    public CommandLineRunner loadData(JdbcTemplate jdbcTemplate) {
        return args -> {
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member", Integer.class);
            if(count == 0){
                jdbcTemplate.execute("INSERT INTO `member` (`id`, `name`, `password`, `role`, `username`) " +
                        "VALUES " +
                        "(UNHEX(REPLACE(UUID(), '-', '')), 'สมชาย ชาวสวน', '$2a$12$fXgzsQbCww4gefIfJmDqRuQmwKibAtTFregXfS5KhcyoIs9NDPo6i', 'GARDENER', 'gardener'), " +
                        "(UNHEX(REPLACE(UUID(), '-', '')), 'คนขาย นะจ้ะ', '$2a$12$OjvSYMO6xfissB7UKEnWE.Dp8g67/b0XEkK9hzIkopDrfzkDXnXn6', 'SELLER', 'seller'), " +
                        "(UNHEX(REPLACE(UUID(), '-', '')), 'คุณแหม่ม เจ้าของสวน', '$2a$12$h/dg4dWPeFRG8GA9Bbrin.qwpvG9Xxog0pvvd/oBK3wpCPiMLuCSm', 'OWNER', 'owner')");
            } 
        };
	}
	

}
