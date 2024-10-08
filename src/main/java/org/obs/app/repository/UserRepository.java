package org.obs.app.repository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.obs.app.model.User;

import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class UserRepository implements PanacheRepository<User> {

    @Override
    public void persist(User user) {
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        PanacheRepository.super.persist(user);
    }

    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
}
