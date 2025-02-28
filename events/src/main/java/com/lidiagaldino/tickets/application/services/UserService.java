package com.lidiagaldino.tickets.application.services;

import com.lidiagaldino.tickets.application.data.input.UserInputData;
import com.lidiagaldino.tickets.application.data.output.UserOutputData;
import io.smallrye.mutiny.Uni;

public interface UserService {
    Uni<UserOutputData> save(UserInputData user);
    void delete(String email);
}
