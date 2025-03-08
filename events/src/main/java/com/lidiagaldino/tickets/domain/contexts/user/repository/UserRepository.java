package com.lidiagaldino.tickets.domain.contexts.user.repository;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;
import io.smallrye.mutiny.Uni;

public interface UserRepository {
    Uni<UserEntity> save(UserEntity user);
    void delete(UserEntity user);
    Uni<UserEntity> findByEmail(String email);
    Uni<UserEntity> findById(String id);
}
