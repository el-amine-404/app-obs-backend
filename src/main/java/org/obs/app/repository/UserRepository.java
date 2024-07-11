package org.obs.app.repository;

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
        saved.setLogin(user.getLogin());
        saved.setPassword(user.getPassword());
        
        return Optional.of(saved);
    }

    

}
