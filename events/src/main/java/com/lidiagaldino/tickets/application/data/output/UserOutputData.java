package com.lidiagaldino.tickets.application.data.output;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;

public record UserOutputData(
        String id,
        String username,
        String email
) {
    public static UserOutputData from(UserEntity userEntity) {
        return new UserOutputData(
                userEntity.id().get(),
                userEntity.username(),
                userEntity.email()
        );
    }
}
