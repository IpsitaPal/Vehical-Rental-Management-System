package com.cg.ovms.exception;

public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
    
	public UserNotFoundException(String m){
        super(m);
    }

}
