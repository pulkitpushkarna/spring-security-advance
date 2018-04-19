package com.spring_security.services;


import com.spring_security.entity.Possession;
import com.spring_security.repositories.PossessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PossessionService {

    @Autowired
    private PossessionRepository possessionRepository;


    @PostAuthorize("hasPermission(returnObject, 'READ') or hasPermission(returnObject, 'ADMINISTRATION')")
     public Possession findById(Long id){
        Possession possession = possessionRepository.findOne(id);
        System.out.println(possession);
        return possession;
    }


}
