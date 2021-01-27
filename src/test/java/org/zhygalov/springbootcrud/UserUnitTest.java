package org.zhygalov.springbootcrud;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import  org.zhygalov.springbootcrud.model.User;
import  org.zhygalov.springbootcrud.model.Role;

public class UserUnitTest {
    @Test
    public void whenCalledGetUserId_thenCorrect() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		assertThat(user.getId()).isEqualTo(1L);
    }
	@Test
    public void whenCalledSetUserId_thenCorrect() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		user.setId(2L);
		assertThat(user.getId()).isEqualTo(2L);
    }
    @Test
    public void whenCalledGetUserInfo_thenCorrect() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
        User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
        assertThat(user.getFirstName()).isEqualTo("Julie");
		assertThat(user.getLastName()).isEqualTo("Andrews");
		assertThat(user.getAge()).isEqualTo(25);
    }
    
	@Test
    public void whenCalledSetUserInfo_thenCorrect() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
        User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
        user.setFirstName("Tawny");
		assertThat(user.getFirstName()).isEqualTo("Tawny");
		user.setLastName("Peters");
		assertThat(user.getLastName()).isEqualTo("Peters");
		user.setAge(27);
		assertThat(user.getAge()).isEqualTo(27);
    }
	@Test
    public void whenCalledGetUserDetails_thenCorrect() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		assertThat(user.getEmail()).isEqualTo("julie@domain.com");
		assertThat(user.getPassword()).isEqualTo("pass");
		assertThat(user.getRoles()).isEqualTo(roles);
    }
	@Test
	public void whenCalledSetUserDetails_thenCorrect() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		user.setEmail("julie@mail.ru");
		assertThat(user.getEmail()).isEqualTo("julie@mail.ru");
		user.setPassword("other");
		assertThat(user.getPassword()).isEqualTo("other");
		roles = Set.of(new Role("UNKNOWN"));
		user.setRoles(roles);
		assertThat(user.getRoles()).isEqualTo(roles);
    }
	@Test
	public void UserDetailsImplementation_correct() {
		var roles = Set.of(new Role("USER"), new Role("ADMIN"));
		User user = new User(1L, "Julie","Andrews", 25,"julie@domain.com", "pass", roles);
		assertThat(user.getAuthorities()).isEqualTo(roles);
		assertThat(user.getUsername()).isEqualTo("julie@domain.com");
    }
}
