package com.lidiagaldino.tickets.infraestructure.exceptions;

import java.util.logging.Logger;

public interface CustomException {

    Integer getHttpStatusCode();
    String getErrorCode();
}
