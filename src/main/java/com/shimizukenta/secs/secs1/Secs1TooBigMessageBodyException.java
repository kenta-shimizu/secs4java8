package com.shimizukenta.secs.secs1;

import java.util.Optional;

/**
 * Secs1 Message is Too Big body Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1TooBigMessageBodyException extends RuntimeException {
	
	private static final long serialVersionUID = 5113152634808257502L;
	
	/**
	 * Reference SECS-I Message.
	 */
	private final Secs1Message referenceSecs1Message;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs1TooBigMessageBodyException() {
		super();
		this.referenceSecs1Message = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param secs1Message the SECS-I Message
	 * 
	 */
	public Secs1TooBigMessageBodyException(Secs1Message secs1Message) {
		super();
		this.referenceSecs1Message = secs1Message;
	}
	
	/**
	 * Returns Optional of reference Secs1Message.
	 * 
	 * @return Optional of reference Secs1Message
	 */
	public Optional<Secs1Message> referenceSecs1Message() {
		return this.referenceSecs1Message == null ? Optional.empty() : Optional.of(this.referenceSecs1Message);
	}
	
}
