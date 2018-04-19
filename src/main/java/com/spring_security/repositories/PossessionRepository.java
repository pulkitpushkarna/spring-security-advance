package com.spring_security.repositories;

import com.spring_security.entity.Possession;
import org.springframework.data.repository.CrudRepository;

public interface PossessionRepository extends CrudRepository<Possession,Long> {
}
