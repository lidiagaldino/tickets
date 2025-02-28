package com.lidiagaldino.tickets.infraestructure.database.pg.repositories;

import com.lidiagaldino.tickets.infraestructure.database.pg.entities.UserEntityModel;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserPgService implements PanacheRepository<UserEntityModel> {
        public Uni<UserEntityModel> findByEmail(String email) {
            return find("email", email).firstResult();
        }

    public Uni<UserEntityModel> save(UserEntityModel user) {
        return persist(user);
    }
}
