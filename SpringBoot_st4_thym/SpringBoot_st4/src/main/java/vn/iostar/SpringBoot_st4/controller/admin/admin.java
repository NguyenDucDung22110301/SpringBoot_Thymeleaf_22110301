package vn.iostar.SpringBoot_st4.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class admin {
    @GetMapping("/home")
    public String index(){
//        return "admin/category-list";
        return"admin/category-list";
    }
}
