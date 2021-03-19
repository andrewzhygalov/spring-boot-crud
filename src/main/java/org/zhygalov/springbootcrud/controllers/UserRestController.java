package org.zhygalov.springbootcrud.controllers;

import org.zhygalov.springbootcrud.model.User;
import org.zhygalov.springbootcrud.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRestController {
    
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping("{id}")
    public User getOne(@PathVariable("id") long id){
		return userService.findById(id);
	}
	
	@GetMapping
    public Iterable <User> users() {
		return userService.getUsers();
    }
	@PostMapping
    public User create(@RequestBody User user) {
		return userService.save(user);
    }
	@PutMapping("{id}")
    public User update(@PathVariable("id") long id, @RequestBody User user){
		return userService.save(user);
	}
	@DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id){
		userService.deleteById(id);
	} 
	
}
