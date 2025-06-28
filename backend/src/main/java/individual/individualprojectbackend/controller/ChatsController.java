package individual.individualprojectbackend.controller;


import individual.individualprojectbackend.business.CreateChatUseCase;
import individual.individualprojectbackend.business.GetChatUseCase;
import individual.individualprojectbackend.business.GetChatsByUserId;
import individual.individualprojectbackend.configuration.security.token.AccessTokenDecoder;
import individual.individualprojectbackend.controller.converter.ChatRequestsConverter;
import individual.individualprojectbackend.controller.dto.CreateChatRequest;
import individual.individualprojectbackend.domain.Chat;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
public class ChatsController {

    private final CreateChatUseCase createChatUseCase;
    private final GetChatUseCase getChatUseCase;
    private GetChatsByUserId getChatsByUserId;

    private AccessTokenDecoder decoder;

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable(value = "id") final Long id,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) final String accessToken){
        final Chat chat = getChatUseCase.getChat(id);

        String tokenToDecode = accessToken.replace("\"", "").substring(7);
        final Long userId = decoder.decode(tokenToDecode).getUserId();

        if (Objects.isNull(chat)){
            return ResponseEntity.notFound().build();
        }

        if (!Objects.equals(userId, chat.getTenant().getId()) &&
                !Objects.equals(userId, chat.getLandlord().getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().body(chat);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Chat>> getChatsByUser(@PathVariable(value = "id") final Long id){
        final List<Chat> chats = getChatsByUserId.getChatsByUser(id);

        return  ResponseEntity.ok().body(chats);
    }


    @PostMapping
    public ResponseEntity<Long> createChat(@RequestBody CreateChatRequest request){
        final Chat chat = ChatRequestsConverter.convertCreateRequest(request);

        final Long response = createChatUseCase.createChat(chat);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
