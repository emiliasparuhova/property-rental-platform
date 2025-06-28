package individual.individualprojectbackend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExistingLinkedAccountException extends ResponseStatusException {
    public ExistingLinkedAccountException() {super(HttpStatus.BAD_REQUEST, "EXISTING_LINKED_ACCOUNT"); }
}
