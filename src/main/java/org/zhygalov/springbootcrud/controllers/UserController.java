package org.zhygalov.springbootcrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

import org.zhygalov.springbootcrud.model.User;
import org.zhygalov.springbootcrud.services.UserService;

@Controller
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
		var userId = (long) request.getSession().getAttribute("userId");
		model.addAttribute("authenticated", userService.findById(userId));
		return "admin";
    }
	
    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request) {
		var userId = (long) request.getSession().getAttribute("userId");
		var user =  userService.findById(userId);
		model.addAttribute("authenticated", userService.findById(userId));
		return "user";
    }
}
