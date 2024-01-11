package ru.rail.gmarketonline.exception;

import lombok.extern.log4j.Log4j;


@Log4j
public class DaoException extends RuntimeException {

    public DaoException(String message, Throwable cause) {
        super(message, cause);
        log.info("DAO Exception has occurred");
    }

    public DaoException(Throwable cause) {
        super(cause);
        log.info("DAO Exception has occurred");
    }
}