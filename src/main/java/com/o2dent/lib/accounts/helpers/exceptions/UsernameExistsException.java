package com.o2dent.lib.accounts.helpers.exceptions;

/**
 * @author delimeta
 *
 */
public class UsernameExistsException extends Exception{
	public UsernameExistsException() {}
	public UsernameExistsException(String message) {
		super(message);
	}
}
