package com.lidiagaldino.tickets.infraestructure.database.pg.repositories.adapter;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;
import com.lidiagaldino.tickets.domain.contexts.user.repository.UserRepository;
import com.lidiagaldino.tickets.infraestructure.database.pg.entities.UserEntityModel;
import com.lidiagaldino.tickets.infraestructure.database.pg.repositories.UserPgService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class UserRepositoryPgAdapter implements UserRepository {

    private final UserPgService userPgService;

    public UserRepositoryPgAdapter(UserPgService userRepository) {
        this.userPgService = userRepository;
    }

    @Override
    public Uni<UserEntity> save(UserEntity user) {
        return Uni.createFrom()
                .item(user)
                .onItem()
                .ifNotNull()
                .transform(it -> UserEntityModel.from(it))
                .onItem()
                .transformToUni(it -> userPgService.save(it))
                .map(it -> it.toEntity());
    }

    @Override
    public void delete(UserEntity user) {

    }

    @Override
    public Uni<UserEntity> findByEmail(String email) {
        return Uni.createFrom()
                .item(email)
                .onItem()
                .ifNotNull()
                .transformToUni(it -> userPgService.findByEmail(it))
                .onItem()
                .ifNotNull()
                .transform(it -> it.toEntity());
    }
}
