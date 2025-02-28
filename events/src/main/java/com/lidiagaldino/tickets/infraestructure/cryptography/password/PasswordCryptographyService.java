package com.lidiagaldino.tickets.infraestructure.cryptography.password;

import com.lidiagaldino.tickets.domain.services.PasswordCryptography;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@ApplicationScoped
public class PasswordCryptographyService implements PasswordCryptography {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int ITERATIONS = 10;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    public Uni<String> hash(String password) {
        return Uni.createFrom().item(() -> {
            byte[] salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH).getBytes(StandardCharsets.UTF_8);

            Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                    .withSalt(salt)
                    .withIterations(ITERATIONS)
                    .withMemoryAsKB(MEMORY)
                    .withParallelism(PARALLELISM);

            Argon2BytesGenerator generator = new Argon2BytesGenerator();
            generator.init(builder.build());
            byte[] hash = new byte[HASH_LENGTH];
            generator.generateBytes(password.getBytes(StandardCharsets.UTF_8), hash);

            byte[] combined = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hash, 0, combined, salt.length, hash.length);
            return Base64.getEncoder().encodeToString(combined);
        });
    }

    public Uni<Boolean> verify(String password, String hashedPassword) {
        return Uni.createFrom().item(() -> {
            byte[] combined = Base64.getDecoder().decode(hashedPassword);
            byte[] salt = new byte[SALT_LENGTH];
            byte[] hash = new byte[HASH_LENGTH];
            System.arraycopy(combined, 0, salt, 0, salt.length);
            System.arraycopy(combined, salt.length, hash, 0, hash.length);

            Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                    .withSalt(salt)
                    .withIterations(ITERATIONS)
                    .withMemoryAsKB(MEMORY)
                    .withParallelism(PARALLELISM);

            Argon2BytesGenerator generator = new Argon2BytesGenerator();
            generator.init(builder.build());
            byte[] testHash = new byte[HASH_LENGTH];
            generator.generateBytes(password.getBytes(StandardCharsets.UTF_8), testHash);

            return java.util.Arrays.equals(hash, testHash);
        }).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }
}
