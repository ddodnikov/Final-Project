package com.soundcloud.exceptions;

public class SoundCloudInvalidArgumentException extends Exception {
	
	private static final long serialVersionUID = -7428384860666913706L;

	public SoundCloudInvalidArgumentException() {
		super();
	}

	public SoundCloudInvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public SoundCloudInvalidArgumentException(String message) {
		super(message);
	}

	public SoundCloudInvalidArgumentException(Throwable cause) {
		super(cause);
	}
}
