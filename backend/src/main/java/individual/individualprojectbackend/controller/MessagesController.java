package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.CreateMessageUseCase;
import individual.individualprojectbackend.business.GetChatUseCase;
import individual.individualprojectbackend.business.GetUserUseCase;
import individual.individualprojectbackend.controller.converter.MessageRequestsConverter;
import individual.individualprojectbackend.controller.dto.CreateMessageRequest;
import individual.individualprojectbackend.controller.dto.SendMessageRequest;
import individual.individualprojectbackend.controller.dto.SendMessageResponse;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.Message;
import individual.individualprojectbackend.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessagesController {

    private final SimpMessagingTemplate messagingTemplate;

    private final CreateMessageUseCase createMessageUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetChatUseCase getChatUseCase;


    @PostMapping("/create")
    public ResponseEntity<Void> createMessage(@RequestBody CreateMessageRequest request){
        final Message message = MessageRequestsConverter.convertCreateRequest(request);

        createMessageUseCase.createMessage(message);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @MessageMapping("/send/{chatId}")
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request){
        Long chatId = request.getChat().getId();
        String privateDestination = "/user/" + chatId + "/queue/inboxmessages";

        User sender = getUserUseCase.getUser(request.getSender().getId());
        Chat chat = getChatUseCase.getChat(request.getChat().getId());

        final SendMessageResponse response = SendMessageResponse.builder()
                .sender(sender)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .chat(chat)
                .build();


        messagingTemplate.convertAndSend(privateDestination, response);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
