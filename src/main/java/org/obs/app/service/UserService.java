package org.obs.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.obs.app.dto.UserDto;
import org.obs.app.exception.UserNotFoundException;
import org.obs.app.mapper.UserMapper;
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

    private final UserMapper userMapper;

    public Optional<UserDto> findUserById(long id) {
        return Optional.of(userMapper.toDto(userRepository.findByIdOptional(id).get()));
    }

    public List<UserDto> getUsers(){
        return userMapper.toDtoList(userRepository.listAll());
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
        if ( userRepository.findByIdOptional(userId).isEmpty() ){
            throw new UserNotFoundException("User with id " + userId + " does not exist" );
        }
        return userRepository.deleteById(userId);
    }

}
