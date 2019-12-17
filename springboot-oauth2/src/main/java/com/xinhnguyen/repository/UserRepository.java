package com.xinhnguyen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.xinhnguyen.domain.User;

@RestResource(path = "users", rel = "users")
public interface UserRepository extends CrudRepository<User, Long> {

}
