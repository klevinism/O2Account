package com.o2dent.lib.accounts.helpers.exceptions;

public class SubdomainExistsException extends Exception{
    public SubdomainExistsException(String message) {
        super(message);
    }

    public SubdomainExistsException(String message, Throwable e) {
        super(message, e);
    }
}