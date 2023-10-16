package ku.cs.flowerManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("greeting", "Hello");
        // ต้องคืนค่าเป็นชื่อไฟล์ html template โดยในเมธอดนี้ คืนค่าเป็น home.html
        return "home";
    }
    @RequestMapping("/test/sidebar")
    public String getSidebar(Model model) {
        return "/fragments/sidebar";
    }
    @RequestMapping("/test/header")
    public String getHeader(Model model) {
        return "/fragments/header";
    }
    @RequestMapping("/test/main")
    public String getMain(Model model) {
        return "/layouts/main";
    }
}


