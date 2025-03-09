package com.lidiagaldino.tickets.infraestructure.cryptography.user;

import com.lidiagaldino.tickets.domain.contexts.user.entity.UserEntity;
import com.lidiagaldino.tickets.domain.services.AuthToken;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class AuthTokenService implements AuthToken {
    @Override
    @WithSpan("jwtGenerateToken")
    public Uni<String> generateToken(UserEntity user) {
        return Uni.createFrom()
                .item(user)
                .onItem()
                .ifNotNull()
                .transform(it -> Jwt.issuer("tickets-backend")
                        .upn(it.email())
                        .subject(it.id().get())
                        .groups("user")
                        .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                        .sign());
    }
}
