package com.lidiagaldino.tickets.application.services;

import com.lidiagaldino.tickets.application.data.output.AuthOutputData;
import io.smallrye.mutiny.Uni;

public interface SessionService {
    Uni<AuthOutputData> login(String email, String password);
}
