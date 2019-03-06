/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.novatronic.pgvdbas.conector.ws.exception;

/**
 *
 * @author evaldivia
 */
public class StartContextException extends RuntimeException{

    public StartContextException() {
    }

    public StartContextException(String message) {
        super(message);
    }

    public StartContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartContextException(Throwable cause) {
        super(cause);
    }
    
}
