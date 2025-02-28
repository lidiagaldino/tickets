package com.lidiagaldino.tickets.infraestructure.database.pg.entities;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntityModel extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String username;
    private String email;
    private String password;

    public UserEntityModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserEntityModel() {}

    public static UserEntityModel from(UserEntity userEntity) {
        return new UserEntityModel(
                userEntity.username(),
                userEntity.email(),
                userEntity.password()
        );
    }

    public UserEntity toEntity() {
        return new UserEntity(
                Optional.of(this.id.toString()),
                this.username,
                this.email,
                this.password
        );
    }
}
