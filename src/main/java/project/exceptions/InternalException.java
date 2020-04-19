package project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "La requète n'a pas pu aboutir suite à une erreur interne")
public class InternalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
