package individual.individualprojectbackend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExistingChatException extends ResponseStatusException {
    public ExistingChatException() {super(HttpStatus.BAD_REQUEST, "EXISTING_CHAT"); }
}
