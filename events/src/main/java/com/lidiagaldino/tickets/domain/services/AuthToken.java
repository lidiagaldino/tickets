package com.lidiagaldino.tickets.domain.services;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;
import io.smallrye.mutiny.Uni;

public interface AuthToken {
    Uni<String> generateToken(UserEntity user);
}
