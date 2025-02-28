package com.lidiagaldino.tickets.domain.services;

import io.smallrye.mutiny.Uni;

public interface PasswordCryptography {
    Uni<String> hash(String password);
    Uni<Boolean> verify(String password, String hashedPassword);
}
