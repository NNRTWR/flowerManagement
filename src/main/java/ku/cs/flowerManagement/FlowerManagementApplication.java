package ku.cs.flowerManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javassist.compiler.ast.Member;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.SignupRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.service.SignupService;


@SpringBootApplication
public class FlowerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerManagementApplication.class, args);
	}

	@Bean
    public CommandLineRunner createDefaultOwner(SignupService signupService) {
        return args -> {
            
                SignupRequest owner = new SignupRequest();
				if(signupService.isUsernameAvailable(owner.getUsername())){
                	owner.setUsername("owner");
                	owner.setPassword("owner123");
                	owner.setName("คุณแหม่ม เจ้าของสวน");
                	owner.setRole("OWNER");
                	signupService.createIntUser(owner);
				}
            
			
				SignupRequest seller = new SignupRequest();
				if(signupService.isUsernameAvailable(seller.getUsername())){
					seller.setUsername("seller");
					seller.setPassword("seller123");
					seller.setName("คนขาย นะจ้ะ");
					seller.setRole("SELLER");
					signupService.createIntUser(seller);
				}
			
				SignupRequest gardener = new SignupRequest();
				if(signupService.isUsernameAvailable(gardener.getUsername())){
					gardener.setUsername("gardener");
					gardener.setPassword("gardener123");
					gardener.setName("สมชาย ชาวสวน");
					gardener.setRole("GARDENER");
					signupService.createIntUser(gardener);
				}

				
			
        };
    }

}
