package com.lidiagaldino.tickets.application.services.impl;

import com.lidiagaldino.tickets.application.data.output.AuthOutputData;
import com.lidiagaldino.tickets.application.services.SessionService;
import com.lidiagaldino.tickets.domain.contexts.user.repository.UserRepository;
import com.lidiagaldino.tickets.domain.services.AuthToken;
import com.lidiagaldino.tickets.domain.services.PasswordCryptography;
import com.lidiagaldino.tickets.infraestructure.exceptions.customs.UserAlreadyExistException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;
    private final PasswordCryptography passwordCryptography;
    private final AuthToken authToken;

    public SessionServiceImpl(UserRepository userRepository, PasswordCryptography passwordCryptography, AuthToken authToken) {
        this.userRepository = userRepository;
        this.passwordCryptography = passwordCryptography;
        this.authToken = authToken;
    }

    @Override
    public Uni<AuthOutputData> login(String email, String password) {
        return Uni.createFrom()
                .item(email)
                .onItem()
                .ifNotNull()
                .transformToUni(it -> userRepository.findByEmail(it))
                .onItem()
                .ifNotNull()
                .transformToUni(it -> this.passwordCryptography.verify(password, it.password())
                        .onItem()
                        .transformToUni(result -> {
                            if(result) return Uni.createFrom().item(it);
                            return Uni.createFrom().failure(() -> new UserAlreadyExistException());
                        })
                )
                .onItem()
                .ifNotNull()
                .transformToUni(it -> authToken.generateToken(it))
                .map(it -> new AuthOutputData(it));
    }
}
