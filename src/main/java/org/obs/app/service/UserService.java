package org.obs.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.obs.app.model.User;
import org.obs.app.repository.UserRepository;

import javax.naming.directory.InvalidAttributesException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public Optional<User> findUserById(long id) {
        return userRepository.findByIdOptional(id);
    }

    public List<User> getUsers(){
        return userRepository.listAll();
    }

    @Transactional
    public void create(User user) throws InvalidAttributesException {
        if (user.getId() != null) {
            throw new InvalidAttributesException("Id must not be filled");
        }
        Validate.notNull(user, "User can not be null");

        userRepository.persist(user);
    }

    @Transactional
    public User update(long userId, User user) {
        user.setId(userId);
        return userRepository.update(user).orElseThrow(() -> new InvalidParameterException("User not found"));
    }

    @Transactional
    public boolean delete(long userId) {
        return userRepository.deleteById(userId);
    }

}
