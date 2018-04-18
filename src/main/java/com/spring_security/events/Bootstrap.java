package com.spring_security.events;

import com.spring_security.entity.User;
import com.spring_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {

    @Autowired
    UserRepository userRepository;

    @EventListener(ContextRefreshedEvent.class)
    void init(){
        Long userCount = userRepository.count();
        if(userCount<1){
            User user = new User();
            user.setUsername("User");
            user.setPassword("Pass");
            userRepository.save(user);
        }
    }
}
