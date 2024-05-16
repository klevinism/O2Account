package com.o2dent.lib.accounts.helpers.exceptions;
public class EmailExistsException extends Exception{
	public EmailExistsException() {}
	public EmailExistsException(String message) {
		super(message);
	}
}