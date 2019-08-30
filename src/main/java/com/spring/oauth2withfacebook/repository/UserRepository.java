package com.spring.oauth2withfacebook.repository;

import com.spring.oauth2withfacebook.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
