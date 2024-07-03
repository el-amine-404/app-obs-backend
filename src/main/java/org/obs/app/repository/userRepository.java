package org.obs.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.inject.Inject;
import org.obs.app.model.User;
import org.obs.app.model.User$;

import java.util.Optional;

public class userRepository {

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<User> getUser(Integer userId){
        return jpaStreamer.stream(User.class)
                .filter(User$.id.equal(userId))
                .findFirst();
    }

}
