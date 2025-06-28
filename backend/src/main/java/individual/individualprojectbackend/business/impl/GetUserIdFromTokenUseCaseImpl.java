package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetUserIdFromTokenUseCase;
import individual.individualprojectbackend.configuration.security.token.AccessTokenDecoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserIdFromTokenUseCaseImpl implements GetUserIdFromTokenUseCase {
    private final AccessTokenDecoder decoder;
    @Override
    public Long getIdFromToken(String accessToken) {
        String tokenToDecode = accessToken.replace("\"", "").substring(7);

        return decoder.decode(tokenToDecode).getUserId();
    }
}
