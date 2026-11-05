package com.tecsup.petclinic.exceptions;

/**
 * Se lanza cuando no existe una visita con el id solicitado.
 */
public class VisitNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VisitNotFoundException(String message) {
		super(message);
	}

}
