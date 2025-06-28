package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Message;

public interface CreateMessageUseCase {

    Long createMessage(Message message);
}
