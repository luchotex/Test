/*
 * NotFoundException.java
 */

package com.tx.simplescheduling.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Diego Rojas
 */
public class NotFoundException extends ClientErrorException {

    /**
     * Creates a new instance of NotFoundException.
     * @param message String
     */
    public NotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

}
