package org.obs.app.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.obs.app.dto.UserDto;
import org.obs.app.dto.UserUpdateCreateDto;
import org.obs.app.exception.UserNotFoundException;
import org.obs.app.mapper.UserMapper;
import org.obs.app.model.User;
import org.obs.app.repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Getter
@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "jwt.expiration.time")
    long jwtExpirationTime;

    private final UserMapper userMapper;


    public UserService(final UserRepository userRepository,
                       final UserMapper userMapper)  {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    private String userNotFoundMessage(long userId) {
        return String.format("User with id %d not found", userId);
    }

    private String userEmailNotValidMessage(String email) {
        return String.format("Invalid email address: %s (please provide something like: username@domain", email);
    }

    public UserDto getUser(long userId) {
        return userMapper.toDto(userRepository
                                    .findByIdOptional(userId)
                                    .orElseThrow(() -> new UserNotFoundException(userNotFoundMessage(userId))));
    }

    public List<UserDto> getUsers(){
        return userMapper.toDtoList(userRepository.listAll());
    }

    @Transactional
    public UserDto create(UserUpdateCreateDto userDetails){

        if (!EmailValidator.getInstance().isValid(userDetails.getEmail())) {
            throw new InvalidParameterException(userEmailNotValidMessage(userDetails.getEmail()));
        }

        User user = new User();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setAge(userDetails.getAge());
        user.setEmail(userDetails.getEmail());
        user.setGender(userDetails.getGender());
        user.setRole(userDetails.getRole());

        userRepository.persist(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto update(long userId, UserUpdateCreateDto userDetails) {
        User user = userRepository
                .findByIdOptional(userId)
                .orElseThrow(() -> new UserNotFoundException(userNotFoundMessage(userId)));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setAge(userDetails.getAge());
        user.setEmail(userDetails.getEmail());
        user.setGender(userDetails.getGender());
        user.setRole(userDetails.getRole());

        return userMapper.toDto(user);
    }

    @Transactional
    public void delete(long userId) {
        User user = userRepository
                .findByIdOptional(userId)
                .orElseThrow(() -> new UserNotFoundException(userNotFoundMessage(userId)));

        userRepository.deleteById(user.getId());
    }

    public String generateJwtToken(final User user) {

        return Jwt.issuer(issuer)
                .upn(user.getUsername())
                .groups(user.getRole().toString())
                .expiresIn(jwtExpirationTime)
                .claim(Claims.email_verified.name(), user.getEmail())
                .claim(Claims.family_name, user.getLastName())
                .sign();
    }

    public boolean checkUserCredentials(String username, String password) {
        final User user = findByUsername(username);
        return BcryptUtil.matches(password, user.getPassword());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User '%s' not found", username)));
    }

}
