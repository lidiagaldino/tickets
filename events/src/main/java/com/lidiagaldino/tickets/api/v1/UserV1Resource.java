package com.lidiagaldino.tickets.api.v1;

import com.lidiagaldino.tickets.application.data.input.UserInputData;
import com.lidiagaldino.tickets.application.services.UserService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserV1Resource {

    @Inject
    UserService userService;

    @POST
    @WithTransaction
    public Uni<Response> store(UserInputData user) {
        return Uni.createFrom()
                .item(user)
                .onItem()
                .ifNotNull()
                .transformToUni(it -> userService.save(it))
                .onItem()
                .ifNotNull()
                .transform(it -> Response.status(Response.Status.CREATED).entity(it).build());
    }

    @GET
    @Path("/{id}")
    @Authenticated
    @WithSession
    public Uni<Response> find(@PathParam("id") String id) {
        return Uni.createFrom()
                .item(id)
                .onItem()
                .ifNotNull()
                .transformToUni(it -> userService.findById(it))
                .onItem()
                .ifNotNull()
                .transform(it -> Response.status(Response.Status.OK).entity(it).build());
    }
}
