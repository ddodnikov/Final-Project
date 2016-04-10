package com.soundcloud.exceptions;

public class TrackAlreadyLikedException extends Exception {

	private static final long serialVersionUID = -5012811893895307949L;

	public TrackAlreadyLikedException() {
		super();
	}

	public TrackAlreadyLikedException(String message, Throwable cause) {
		super(message, cause);
	}

	public TrackAlreadyLikedException(String message) {
		super(message);
	}

	public TrackAlreadyLikedException(Throwable cause) {
		super(cause);
	}
	
	

}
