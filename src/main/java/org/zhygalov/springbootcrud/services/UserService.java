package org.zhygalov.springbootcrud.services;
import org.zhygalov.springbootcrud.model.*;
import org.zhygalov.springbootcrud.repositories.UserRepository;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.core.env.Environment;
@Service
@Transactional
public class UserService implements UserDetailsService {
	
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private Environment env;
	
	public UserService() {}
	
	@Autowired
	public UserService(UserRepository repo) {
		this.repo = repo;
	}
	
	public User save(User user) {
		if(user.getId() == null || !repo.existsById(user.getId())) {
			user.setPassword(encoder.encode(user.getPassword()));
			return repo.save(user);
		}
		return repo.findById(user.getId())
					.map(managed -> mergeAndSave(managed, user))
					.get();
	}
	private User mergeAndSave(User managed, User updated) {
		/* managed.setFirstName(updated.getFirstName());
		managed.setLastName(updated.getLastName());
		managed.setAge(updated.getAge());
		managed.setEmail(updated.getEmail());
		managed.setRoles(updated.getRoles()); */
		
		var pass = updated.getPassword();
		if(!managed.getPassword().equals(pass))
			updated.setPassword(encoder.encode(pass));
			
		
		return repo.save(updated);
	}
	public User findById(long id) {
		return repo.findById(id).get();
	}
	public void delete(User user) {
		repo.delete(user);
	}
	
	public Iterable <User> getUsers(){
		return repo.findAll();
	}
	
	@Override
    public UserDetails loadUserByUsername(String s) {
        return repo.findByEmail(s);
    }
	
	@PostConstruct
	private void init() {
		var roles = new HashSet<Role>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		var pass = encoder.encode(env.getProperty("admin.pass"));
		repo.save(new User(null,"Andrew", "Z", 35, "drew@domain.com", pass, roles));
	}
}