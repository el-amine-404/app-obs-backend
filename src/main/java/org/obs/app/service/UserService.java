package org.obs.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.obs.app.model.User;
import org.obs.app.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;


}
