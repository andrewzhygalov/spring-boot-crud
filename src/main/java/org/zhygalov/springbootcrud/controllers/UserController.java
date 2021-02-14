package org.zhygalov.springbootcrud.controllers;


import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;

import org.zhygalov.springbootcrud.model.*;
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
        model.addAttribute("users", userService.getUsers());
		model.addAttribute("emptyUser", new User());
		var userId = (long) request.getSession().getAttribute("userId");
		model.addAttribute("authenticated", userService.findById(userId));
		return "admin";
    }
    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request) {
		var userId = (long) request.getSession().getAttribute("userId");
		var user =  userService.findById(userId);
		model.addAttribute("authenticated", userService.findById(userId));
		model.addAttribute("users", List.of(user));
		return "user";
    }
    
   
    @PostMapping("/adduser")
    public String addUser(User user, Model model) {
		if(userService.loadUserByUsername(user.getEmail()) != null) {
			return "redirect:/admin";
		}
        userService.save(prefixRoles(user));
		model.addAttribute("users", userService.getUsers());
        return "redirect:/admin";
    }
	
	@GetMapping("/editform/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "editForm";
    }
	
    @GetMapping("/deleteform/{id}")
    public String showDeleteForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "deleteForm";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, User user, Model model) {
        userService.save(prefixRoles(user));
        return "redirect:/admin";
    }
	private static User prefixRoles(User user) {
		var roles = new HashSet<Role>();
		user.getRoles().stream()
			.map(role -> {
				var r = role.getRole();
				if(!r.startsWith("ROLE_")) {
					r = "ROLE_" + role;
				}
				role.setRole(r);
				return  role;
			})
			.forEach(roles::add);
		user.setRoles(roles);
		return user;
	}
      
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        userService.delete(user);
        model.addAttribute("users", userService.getUsers());
        return "redirect:/admin";
    }
	
	@GetMapping("/error")
    public String error() {
        return "redirect:/logout";
    }
}
