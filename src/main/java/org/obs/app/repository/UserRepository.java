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

    public Optional<User> update(User user) {
        final var id = user.getId();
        var savedOpt = this.findByIdOptional(id);
        if (savedOpt.isEmpty()) {
            return Optional.empty();
        }

        var saved = savedOpt.get();
        saved.setFirstName(user.getFirstName());
        saved.setLastName(user.getLastName());
        saved.setUsername(user.getUsername());
        saved.setPassword(user.getPassword());
        saved.setAge(user.getAge());
        saved.setEmail(user.getEmail());
        saved.setGender(user.getGender());

        return Optional.of(saved);
    }

    @Override
    public void persist(User user) {
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        PanacheRepository.super.persist(user);
    }
}
