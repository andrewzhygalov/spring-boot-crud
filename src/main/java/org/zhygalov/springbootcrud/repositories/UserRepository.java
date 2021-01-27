package org.zhygalov.springbootcrud.repositories;

import org.zhygalov.springbootcrud.model.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
		User findByEmail(String email);
}
