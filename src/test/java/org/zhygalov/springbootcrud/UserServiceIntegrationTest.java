package org.zhygalov.springbootcrud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import org.zhygalov.springbootcrud.model.User;
import org.zhygalov.springbootcrud.model.Role;
import org.zhygalov.springbootcrud.repositories.UserRepository;
import org.zhygalov.springbootcrud.services.UserService;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceIntegrationTest {
    @Autowired
	private  UserRepository userRepository;
	
	private UserService userService;
	
	@BeforeEach
	public void initService() {
		userService = new UserService(userRepository);
	}
    
	
	@Test
	public void whenSaveNewUser_thenCorrect() {
		Set<Role> roles = new HashSet<>(); 
		Set.of(new Role("USER"), new Role("ADMIN"))
			.forEach(roles::add);
		var julie = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		var ron = new User(-1L, "Ron","Peters", 28,"ron@domain.com", "pass", roles);
		var managed = userService.save(julie);
		assertThat(managed.getId()).isNotNull();
		managed = userService.save(ron);
		assertThat(managed.getId()).isNotEqualTo(-1L);
	}
	
	@Test
	public void whenUpdateUser_thenCorrect() {
		Set<Role> roles = new HashSet<>(); 
		Set.of(new Role("USER"), new Role("ADMIN"))
			.forEach(roles::add);
		var julie = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		userService.save(julie);
		var managed = (User) userService.loadUserByUsername("julie@domain.com");
		managed.setLastName("Peters");
		userService.save(managed);
		var updated = (User) userService.loadUserByUsername("julie@domain.com");
		assertThat(updated.getLastName()).isEqualTo("Peters");
	}
	
	@Test
	public void whenDeleteUser_thenCorrect() {
		Set<Role> roles = new HashSet<>(); 
		Set.of(new Role("USER"), new Role("ADMIN"))
			.forEach(roles::add);
		var julie = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		userService.save(julie);
		var managed = (User) userService.loadUserByUsername("julie@domain.com");
		userService.delete(managed);
		var deleted = (User) userService.loadUserByUsername("julie@domain.com");
		assertThat(deleted).isNull();
	}
	@Test
    public void givenSetOfUsers_whenGetUsers_thenReturnAllUsers() {
        var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		var julie = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		var ron = new User(null, "Ron","Peters", 28,"ron@domain.com", "pass", roles);
		var tim = new User(null, "Tim","Stevens", 31,"tim@domain.com", "pass", roles);
		Set.of(julie, ron, tim).forEach(userService::save);
        var users = userService.getUsers();
        assertThat(users).hasSize(3).extracting(User::getFirstName).containsOnly(julie.getFirstName(), ron.getFirstName(), tim.getFirstName());
    }  
}
