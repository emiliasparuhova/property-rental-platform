package individual.individualprojectbackend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIdMismatchException extends ResponseStatusException {
    public UserIdMismatchException() {super(HttpStatus.FORBIDDEN, "USER_ID_MISMATCH");}
}
