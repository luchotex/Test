/*
 * InternalServerErrorException.java
 */

package com.tx.simplescheduling.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Diego Rojas
 */
public class InternalServerErrorException extends ClientErrorException {

    /**
     * Creates a new instance of InternalServerErrorException.
     */
    public InternalServerErrorException() {
        super(Response.Status.INTERNAL_SERVER_ERROR);
    }

}
