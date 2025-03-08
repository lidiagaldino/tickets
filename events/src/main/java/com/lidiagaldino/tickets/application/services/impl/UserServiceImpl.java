package com.lidiagaldino.tickets.application.services.impl;

import com.lidiagaldino.tickets.application.data.input.UserInputData;
import com.lidiagaldino.tickets.application.data.output.UserOutputData;
import com.lidiagaldino.tickets.application.services.UserService;
import com.lidiagaldino.tickets.domain.contexts.user.repository.UserRepository;
import com.lidiagaldino.tickets.domain.services.PasswordCryptography;
import com.lidiagaldino.tickets.infraestructure.exceptions.customs.UserAlreadyExistException;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordCryptography passwordCryptography;

    public UserServiceImpl(UserRepository userRepository, PasswordCryptography passwordCryptography) {
        this.userRepository = userRepository;
        this.passwordCryptography = passwordCryptography;
    }

    @Override
    public Uni<UserOutputData> save(UserInputData user) {
        return userRepository.findByEmail(user.email())
                .onItem().ifNotNull()
                .failWith(() -> new UserAlreadyExistException())
                .onItem().ifNull().switchTo(() -> this.passwordCryptography.hash(user.password())
                        .onItem().ifNotNull()
                        .transform(password -> user.toEntity(password))
                        .onItem().ifNotNull()
                        .transformToUni(userEntity -> this.userRepository.save(userEntity))).onItem().ifNotNull()
                .transform(savedUser -> UserOutputData.from(savedUser));
    }

    @Override
    public void delete(String email) {

    }

    @Override
    @Authenticated
    public Uni<UserOutputData> findById(String id) {
        return Uni.createFrom()
                .item(id)
                .onItem()
                .ifNotNull()
                .transformToUni(it -> this.userRepository.findById(id))
                .onItem()
                .ifNotNull()
                .transform(user -> UserOutputData.from(user));
    }
}
