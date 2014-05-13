package io.monocycle.exception;

public class AgentException extends RuntimeException {

	private static final long serialVersionUID = -3559049787084588498L;

	public AgentException(String message, Throwable cause) {
		super(message, cause);
	}

	public AgentException(String message) {
		super(message);
	}

}
