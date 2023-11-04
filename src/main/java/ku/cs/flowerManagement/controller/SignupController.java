//Patcharin Khangwicha 6410406797
package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.SignupRequest;
import ku.cs.flowerManagement.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {


    @Autowired
    private SignupService signupService;


    @GetMapping("/signup")
    public String getSignupPage(@ModelAttribute SignupRequest user, Model model) {
        model.addAttribute("members",signupService.getAllUser()); 
        return "signup"; 
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute SignupRequest user, Model model,@RequestParam("role") String role) { 

        if (signupService.isUsernameAvailable(user.getUsername())) { //username นี้ใช้ได้มั้ย
            signupService.createUser(user,role);
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", "Username not available");
        }
        return "redirect:/signup";
    }
}

