package com.cg.ovms.exception;

public class DuplicateRecordException  extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public DuplicateRecordException(String msg) {
		super(msg);
	}
}
