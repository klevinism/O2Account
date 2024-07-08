package com.o2dent.lib.accounts.helpers.exceptions;

/**
 * @author delimeta
 *
 */
public class PhoneNumberExistsException extends Exception{
	public PhoneNumberExistsException() {}
	public PhoneNumberExistsException(String message) {
		super(message);
	}
}
