package com.lidiagaldino.tickets.application.data.input;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;

import java.util.Optional;


public record UserInputData(
        String username,
        String email,
        String password
) {

    public UserEntity toEntity(String hashedPassword) {
        return new UserEntity(
                Optional.empty(),
                this.username,
                this.email,
                hashedPassword
        );
    }
}
