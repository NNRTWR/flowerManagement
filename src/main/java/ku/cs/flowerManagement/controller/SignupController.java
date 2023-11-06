package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Member;
import ku.cs.flowerManagement.model.SignupRequest;
import ku.cs.flowerManagement.service.SignupService;
import ku.cs.flowerManagement.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/owner/member")
@Controller
public class SignupController {


    @Autowired
    private SignupService signupService;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;


    @GetMapping
    public String getSignupPage(@ModelAttribute SignupRequest user, Model model) {
        model.addAttribute("members",signupService.getAllUser()); 
        return "signup"; 
    }

    @PostMapping
    public String signupUser(@ModelAttribute SignupRequest user, Model model,@RequestParam("role") String role) { 

        if (signupService.isUsernameAvailable(user.getUsername())) { //username นี้ใช้ได้มั้ย
            signupService.createUser(user,role);
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", "Username not available");
        }
        return "redirect:/owner/member";
    }

    @GetMapping("/update/{id}")
    public String getForm(@PathVariable("id") UUID member_ID, Model model) {
        Member member = userDetailsServiceImp.hasThisMember(member_ID);
        if(member == null){
            System.out.println("null");
            return "redirect:/owner/member";
        }
        else{
            System.out.println("not null");
            model.addAttribute("member", member);
            return "member-update";
        }
    }

    @PostMapping("/update/{id}")
    public String updateDetail(@PathVariable("id") UUID member_ID, @ModelAttribute SignupRequest user, Model model,@RequestParam("role") String role) {
        signupService.updateUser(member_ID, user, role);

        return "redirect:/owner/member";
    }


}

