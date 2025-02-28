package com.lidiagaldino.tickets.infraestructure.exceptions.enums;

import jakarta.ws.rs.core.Response;

import java.util.Arrays;

public enum StatusTypeEnum implements Response.StatusType {

    UNPROCESSABLE_ENTITY(422, Response.Status.Family.CLIENT_ERROR, "Unprocessable Entity");

    private final int statusCode;
    private final Response.Status.Family family;
    private final String reason;

    StatusTypeEnum(int statsCode, Response.Status.Family family, String reason) {
        this.statusCode = statsCode;
        this.family = family;
        this.reason = reason;
    }

    public static Response.StatusType valueOf(final int statusCode) {
        var statusType = Arrays.stream(StatusTypeEnum.values())
                .filter(e -> e.statusCode == statusCode)
                .findFirst()
                .orElse(null);
        if (statusType != null) return statusType;
        return Response.Status.fromStatusCode(statusCode);
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public Response.Status.Family getFamily() {
        return this.family;
    }

    @Override
    public String getReasonPhrase() {
        return this.reason;
    }

    @Override
    public Response.Status toEnum() {
        return Response.StatusType.super.toEnum();
    }
}
