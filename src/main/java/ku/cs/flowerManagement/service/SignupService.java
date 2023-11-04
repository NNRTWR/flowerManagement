//Patcharin Khangwicha 6410406797
package ku.cs.flowerManagement.service;


import ku.cs.flowerManagement.entity.Member;
import ku.cs.flowerManagement.model.SignupRequest;
import ku.cs.flowerManagement.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService {


    @Autowired
    private MemberRepository repository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;


    public boolean isUsernameAvailable(String username) {
        return repository.findByUsername(username) == null;
    }


    public void createUser(SignupRequest user) {
        Member record = modelMapper.map(user, Member.class); // Member = target class >>> map SignupRequest เป็น Member
        // record.setRole("SELLER");
        // record.setRole("GARDENER");
        record.setRole("OWNER");


        String hashedPassword = passwordEncoder.encode(user.getPassword()); //springframework security ทำให้
        record.setPassword(hashedPassword);


        repository.save(record);
    }


    public Member getUser(String username) {
        return repository.findByUsername(username);
    }
}
