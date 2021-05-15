package com.stevenpj.infra.csv;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to load resource")
public class CsvLoadingException extends RuntimeException {

    public CsvLoadingException(Throwable throwable) {
        super(throwable);
    }
}