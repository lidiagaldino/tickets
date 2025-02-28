package com.lidiagaldino.tickets.domain.contexts.user.entity;

import java.util.Optional;

public record UserEntity(
        Optional<String> id,
        String username,
        String email,
        String password
) {
}
