package com.lidiagaldino.tickets.application.data.output;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AuthOutputData(
        @JsonProperty("token")
        String token
) {
}
