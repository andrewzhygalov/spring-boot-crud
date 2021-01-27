package org.zhygalov.springbootcrud.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Embeddable;

@Embeddable
public class Role implements GrantedAuthority {
    private String role;

    public Role(String role) {
        this.role = role;
    }
	public Role() {}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
	
	@Override
	public String toString() {
		return role.startsWith("ROLE_") ? role.substring(5) : role;
	}
}
