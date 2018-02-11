/*
 * InvalidRequestException.java
 */

package com.tx.simplescheduling.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Diego Rojas
 */
public class InvalidRequestException extends ClientErrorException {

    /**
     * Creates a new instance of InvalidRequestException.
     * @param message String
     */
    public InvalidRequestException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }

}
