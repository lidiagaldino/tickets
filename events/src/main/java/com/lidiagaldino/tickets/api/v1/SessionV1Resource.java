package com.lidiagaldino.tickets.api.v1;

import com.lidiagaldino.tickets.application.data.input.AuthInputData;
import com.lidiagaldino.tickets.application.services.SessionService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionV1Resource {
    @Inject
    SessionService sessionService;

    @POST
    @WithTransaction
    public Uni<Response> login(AuthInputData user) {
        return Uni.createFrom()
                .item(user)
                .onItem()
                .ifNotNull()
                .transformToUni(it -> sessionService.login(it.email(), it.password()))
                .onItem()
                .ifNotNull()
                .transform(it -> Response.status(Response.Status.OK).entity(it).build());
    }
}
