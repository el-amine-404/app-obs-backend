package org.obs.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.obs.app.model.User;
import org.obs.app.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId){
        Optional<User> user = userRepository.getUser(userId);

        if(user.isPresent()){
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<User> getUsers(){
        return userRepository.getUsers();
    }

}
