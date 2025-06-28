package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.CreateLinkedAccountUseCase;
import individual.individualprojectbackend.business.DeleteLinkedAccountUseCase;
import individual.individualprojectbackend.business.GetLinkedAccountsUseCase;
import individual.individualprojectbackend.configuration.security.token.AccessTokenDecoder;
import individual.individualprojectbackend.controller.converter.LinkedAccountRequestsConverter;
import individual.individualprojectbackend.controller.dto.CreateLinkedAccountRequest;
import individual.individualprojectbackend.domain.LinkedAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/linked-account")
@AllArgsConstructor
public class LinkedAccountsController {
    private final CreateLinkedAccountUseCase createLinkedAccountUseCase;
    private final GetLinkedAccountsUseCase getLinkedAccountsUseCase;
    private final DeleteLinkedAccountUseCase deleteLinkedAccountUseCase;

    private AccessTokenDecoder decoder;

    @GetMapping("{userId}")
    public ResponseEntity<List<LinkedAccount>> getLinkedAccounts(@PathVariable(value = "userId") final Long userId){
        final List<LinkedAccount> linkedAccounts = getLinkedAccountsUseCase.getLinkedAccounts(userId);

        return ResponseEntity.ok().body(linkedAccounts);
    }
    @PostMapping
    public ResponseEntity<Long> createLinkedAccount(@RequestBody CreateLinkedAccountRequest request,
                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) final String accessToken){
        String tokenToDecode = accessToken.replace("\"", "").substring(7);
        final Long userId = decoder.decode(tokenToDecode).getUserId();

        final LinkedAccount linkedAccount = LinkedAccountRequestsConverter.convertCreateRequest(request, userId);

        final Long result = createLinkedAccountUseCase.createLinkedAccount(linkedAccount);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<LinkedAccount>> deleteLinkedAccount(@PathVariable(value = "id") final Long id){
        deleteLinkedAccountUseCase.deleteLinkedAccount(id);

        return ResponseEntity.noContent().build();
    }


}
