package com.chenhong.android.carsdoor.exception;

public class BaseException extends RuntimeException{
	//�������е������쳣
	
	 public BaseException()
	    {
	        super();
	    }

	    public BaseException(String detailMessage, Throwable throwable)
	    {
	        super(detailMessage, throwable);
	    }

	    public BaseException(String detailMessage)
	    {
	        super(detailMessage);
	    }

	    public BaseException(Throwable throwable)
	    {
	        super(throwable);
	    }
	
	
	
	
	

}
