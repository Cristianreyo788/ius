package com.bbva.libranza.exception;

public class FileCaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *  
     */
    public FileCaseException() {
        super();
    }

    /**
     * @param message
     */
    public FileCaseException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public FileCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public FileCaseException(Throwable cause) {
        super(cause);
    }
}
