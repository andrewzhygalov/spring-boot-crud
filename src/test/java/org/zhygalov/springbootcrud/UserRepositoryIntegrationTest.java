package org.zhygalov.springbootcrud;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import org.zhygalov.springbootcrud.model.User;
import org.zhygalov.springbootcrud.model.Role;
import org.zhygalov.springbootcrud.repositories.UserRepository;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
	
	
	@Test
    public void whenCalledSaveUser_thenCorrect() {
        var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		var managed = userRepository.save(user);
        assertThat(user.getId()).isNotEqualTo(null);
		assertThat(user.getFirstName()).isEqualTo(managed.getFirstName());
		assertThat(user.getLastName()).isEqualTo(managed.getLastName());
		assertThat(user.getAge()).isEqualTo(managed.getAge());
		assertThat(user.getEmail()).isEqualTo(managed.getEmail());
		assertThat(user.getPassword()).isEqualTo(managed.getPassword());
		assertThat(user.getRoles()).isEqualTo(managed.getRoles());
    }
	
	@Test
    public void whenCalledUpdateUser_thenCorrect() {
        Set<Role> roles = new HashSet<>();
		roles.add(new Role("USER"));
		roles.add(new Role("ADMIN"));
		User user = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		var managed = userRepository.save(user);
		var fromDb = userRepository.findById(managed.getId()).get();
		fromDb.setLastName("Peters"); 
		userRepository.save(fromDb);
		assertThat(userRepository.findById(fromDb.getId()).get().getLastName())
			.isEqualTo("Peters"); 
    }
	
	@Test
    public void whenCalledDeleteUser_thenCorrect() {
        var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		userRepository.save(user);
		userRepository.delete(user);
        var fromDb = userRepository.findById(user.getId()).orElse(null);
        assertThat(fromDb).isNull();
    }
	
	@Test
    public void whenFindByEmail_thenReturnUser() {
        var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		var managed = userRepository.save(user);
        assertThat(managed).isEqualTo(userRepository.findByEmail("julie@domain.com"));
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        var fromDb = userRepository.findByEmail("doesNotExist");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindById_thenReturnUser() {
        var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		userRepository.save(user);
        var fromDb = userRepository.findById(user.getId()).orElse(null);
        assertThat(fromDb).isEqualTo(user);
    }

  

    @Test
    public void givenSetOfUsers_whenFindAll_thenReturnAllUsers() {
        var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		var julie = new User(null, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		var ron = new User(null, "Ron","Peters", 28,"ron@domain.com", "pass", roles);
		var tim = new User(null, "Tim","Stevens", 31,"tim@domain.com", "pass", roles);
		
		userRepository.save(julie);
		userRepository.save(ron);
		userRepository.save(tim);
        var users = userRepository.findAll();
        assertThat(users).hasSize(3).extracting(User::getFirstName).containsOnly(julie.getFirstName(), ron.getFirstName(), tim.getFirstName());
    }  
}
