package com.soundcloud.exceptions;

public class TrackAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 3565779106116333837L;

	public TrackAlreadyExistsException() {
		super();
	}

	public TrackAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TrackAlreadyExistsException(String message) {
		super(message);
	}

	public TrackAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	
	
}
