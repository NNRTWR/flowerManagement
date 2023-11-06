package ku.cs.flowerManagement.service;


import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.Member;
import ku.cs.flowerManagement.model.SignupRequest;
import ku.cs.flowerManagement.repository.MemberRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<Member> getAllUser(){ 
        List<Member> members = repository.findAll();
        return members;
    }

     public Page<Member> getMemberPage(int page, int size) {   //สำหรับ pagination
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public int getTotalMemberCount() {
        List<Member> members = repository.findAll();
        return members.size();
    }

    public void createUser(SignupRequest user, String role) {
        Member record = modelMapper.map(user, Member.class); 
        // record.setRole("SELLER");
        // record.setRole("GARDENER");
        // record.setRole("OWNER");
        record.setRole(role);

        String hashedPassword = passwordEncoder.encode(user.getPassword()); //springframework security ทำให้
        record.setPassword(hashedPassword);


        repository.save(record);
    }

    public void createIntUser(SignupRequest user) {
        Member record = modelMapper.map(user, Member.class); 
        // record.setRole("SELLER");
        // record.setRole("GARDENER");
        // record.setRole("OWNER");
        //record.setRole("OWNER");

        String hashedPassword = passwordEncoder.encode(user.getPassword()); //springframework security ทำให้
        record.setPassword(hashedPassword);


        repository.save(record);
    }

    public void updateUser(UUID member_ID, SignupRequest user, String role){
        Member member = repository.findById(member_ID).get();
        member.setName(user.getName());
        member.setUsername(user.getUsername());
        member.setPassword(user.getPassword());

        member.setRole(role);

        repository.save(member);
    }

    public Member getUser(String username) {
        return repository.findByUsername(username);
    }
}
