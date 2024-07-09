package org.obs.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.obs.app.model.User;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class UserRepository implements PanacheRepository<User> {

    private final JPAStreamer jpaStreamer;

//    public Optional<User> getUser(Long userId){
//        return jpaStreamer.stream(User.class)
//                .filter(User$.id.equal(userId))
//                .findFirst();
//    }
//
//    public List<User> getUsers(){
//        return jpaStreamer.stream(User.class)
//                .toList();
//    }

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
