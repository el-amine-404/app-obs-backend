package org.obs.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import org.obs.app.model.User;
import org.obs.app.model.User$;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {


    private final JPAStreamer jpaStreamer;

    public UserRepository(JPAStreamer jpaStreamer){
        this.jpaStreamer = jpaStreamer;
    }

    public Optional<User> getUser(Long userId){
        return jpaStreamer.stream(User.class)
                .filter(User$.id.equal(userId))
                .findFirst();
    }

    public List<User> getUsers(){
        return jpaStreamer.stream(User.class)
                .toList();
    }

    

}
