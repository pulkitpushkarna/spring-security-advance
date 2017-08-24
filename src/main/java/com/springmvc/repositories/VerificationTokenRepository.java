package com.springmvc.repositories;

import com.springmvc.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<User,Integer> {
}
