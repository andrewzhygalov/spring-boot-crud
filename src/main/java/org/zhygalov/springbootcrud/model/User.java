package org.zhygalov.springbootcrud.model;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @NotBlank(message = "First name is mandatory")
    private String firstName;
	
	@NotBlank(message = "Last name is mandatory")
    private String lastName;
	
	private Integer age;
    
    @NotBlank(message = "Email is mandatory")
    private String email;
	
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	/* @Version
	private Integer version;
	
	public User(Long id, String firstName, String lastName, Integer age, String email, String password, Set<Role> roles) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.password = password;
		this.roles = roles;
	} */
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
