package com.lidiagaldino.tickets.infraestructure.exceptions;

import com.lidiagaldino.tickets.infraestructure.exceptions.dto.ErrorDTO;
import com.lidiagaldino.tickets.infraestructure.exceptions.enums.StatusTypeEnum;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMapper {

    @ServerExceptionMapper(BaseCustomException.class)
    public Uni<RestResponse<ErrorDTO>> baseCustomException(final BaseCustomException e) {
        return Uni.createFrom().item(e)
                .map(ex -> new ErrorDTO(ex))
                .map(data -> RestResponse.ResponseBuilder.create(StatusTypeEnum.valueOf(e.getHttpStatusCode()), data).build());
    }
}
