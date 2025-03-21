package com.zeroblog.zerolog.exception;

/**
 *  status -> 404
 */


public class PostNodFound extends zerologException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";


    public PostNodFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
